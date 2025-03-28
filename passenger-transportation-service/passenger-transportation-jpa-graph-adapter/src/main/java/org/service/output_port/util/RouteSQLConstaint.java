package org.service.output_port.util;

public class RouteSQLConstaint {

    public static final String SELECT_ALL_ROUTE_BY_PARAM_WITH_DFS = """
             WITH RECURSIVE bfs AS (
                   SELECT t.from_location_id,
                   t.to_location_id,
                    ARRAY [t.from_location_id, t.to_location_id]            AS path_array,
                    ARRAY [t.edge_id]                                       AS edge_path,
                    t.price                                                 AS total_price,
                    t.departure_time                                        AS dep_time,
                    t.departure_time + (t.time_cost || ' minute')::interval AS arr_time,
                    t.time_cost                                             AS total_time_cost,
                    tt.type_name                                            AS transport_type,
                    1                                                       AS level -- Уровень поиска (глубина)
                    FROM t_location_graph t
                     INNER JOIN t_transport_types tt ON t.type_id = tt.id
                     INNER JOIN t_location l ON t.from_location_id = l.id
                     WHERE l.c_name = :fromLocation
                     AND t.departure_time BETWEEN (:depTime)::timestamp - interval '120 hour'
                     AND (:depTime)::timestamp + interval '120 hour'
            
                      UNION ALL
            
            
                      SELECT rp.from_location_id,
                      t.to_location_id,
                      rp.path_array || t.to_location_id,
                      rp.edge_path || t.edge_id,
                      rp.total_price + t.price,
                      rp.dep_time,
                      rp.dep_time + ((rp.total_time_cost + t.time_cost) || ' minute')::interval AS arr_time,
                      rp.total_time_cost + t.time_cost,
                      CASE
           WHEN rp.transport_type = 'микс' THEN 'микс'
           WHEN rp.transport_type = tt.type_name THEN rp.transport_type
             ELSE 'микс'
                     END                                                                   AS transport_type,
            rp.level + 1                                                              AS level
           FROM bfs rp
            INNER JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
            INNER JOIN t_transport_types tt ON t.type_id = tt.id
           WHERE NOT t.to_location_id = ANY (rp.path_array) -- Избегаем циклов
           )
            
            
           SELECT rp.from_location_id,
           rp.to_location_id,
           rp.edge_path,
           rp.total_price,
           rp.dep_time,
           rp.arr_time,
           rp.total_time_cost,
            rp.transport_type
            FROM bfs rp
            JOIN t_location l ON rp.to_location_id = l.id
             WHERE rp.transport_type = :type
            AND l.c_name = :toLocation
            ORDER BY rp.dep_time, rp.total_time_cost
            LIMIT :limit OFFSET :offset;  
            """;

    public static final String SELECT_ALL_ROUTE_WITH_DFS = """
             WITH  RECURSIVE route_path AS (
                SELECT
                    t.from_location_id,
                    t.to_location_id,
                    ARRAY[from_location_id, to_location_id] AS path_array,  -- массив вместо строки
                    ARRAY[edge_id] AS edge_path,
                    t.price AS total_price,
                    t.departure_time AS dep_time,
                    t.departure_time + (t.time_cost || ' minute')::interval AS arr_time,
                    t.time_cost AS total_time_cost,
                    tt.type_name AS transport_type
            
                FROM t_location_graph t
                         INNER JOIN t_transport_types tt ON t.type_id = tt.id
                WHERE t.from_location_id = :fromLocation
            
                UNION ALL
            
                SELECT
                    rp.from_location_id,
                    t.to_location_id,
                    rp.path_array || t.to_location_id,  -- Добавляем в массив следующий город
                    rp.edge_path || t.edge_id,  -- Добавляем в массив следующее ребро
                    rp.total_price + t.price,
                    rp.dep_time,
                    rp.dep_time + ((rp.total_time_cost + t.time_cost) || ' minute')::interval AS arr_time,
                    rp.total_time_cost + t.time_cost,
                    CASE
                        WHEN rp.transport_type = 'микс' THEN 'микс'
                        WHEN rp.transport_type = tt.type_name THEN rp.transport_type
                        ELSE 'mix'
                        END AS transport_type
                FROM route_path rp
                         INNER JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
                         INNER JOIN t_transport_types tt ON t.type_id = tt.id
                WHERE NOT t.to_location_id  = ANY (rp.path_array)
            )
            
            SELECT  rp.from_location_id,
                    rp.to_location_id,
                    rp.edge_path,
                    rp.total_price,
                    rp.dep_time,
                    rp.arr_time,
                    rp.total_time_cost,
                    rp.transport_type FROM(SELECT *
                                           FROM route_path
                                           LIMIT :limit OFFSET :offset) as "rp" ORDER BY total_time_cost, total_price, dep_time;
            
            
            """;
}
