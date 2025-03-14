package org.service.output_port.util;

public class RouteSQLConstaint {

    public static final String SELECT_ALL_ROUTE_BY_PARAM_WITH_DFS = """
             WITH RECURSIVE route_path AS (
     SELECT
         t.from_location_id,
         t.to_location_id,
         ',' || t.from_location_id || ',' || t.to_location_id || ',' AS path,
         t.price AS total_price,
         t.departure_time AS dep_time,
         t.departure_time + (t.time_cost || ' minute')::interval AS arr_time,
         t.time_cost AS total_time_cost,
         tt.type_name AS transport_type,
         ',' || t.edge_id || ',' AS edge_path
     FROM t_location_graph t
         INNER JOIN t_transport_types tt ON t.type_id = tt.id
         INNER JOIN t_location l ON t.from_location_id = l.id
     WHERE l.c_name = :fromLocation  AND t.departure_time BETWEEN (:depTime)::timestamp - interval '120 hour'
         AND (:depTime)::timestamp + interval '120 hour'
 
     UNION ALL
 
     SELECT
         rp.from_location_id,
         t.to_location_id,
         rp.path || t.to_location_id || ',',
         rp.total_price + t.price,
         rp.dep_time,
         rp.dep_time + ((rp.total_time_cost + t.time_cost) || ' minute')::interval AS arr_time,
         rp.total_time_cost + t.time_cost,
         CASE
             WHEN rp.transport_type = 'микс' THEN 'микс'
             WHEN rp.transport_type = tt.type_name THEN rp.transport_type
             ELSE 'микс'
             END AS transport_type,
         rp.edge_path || t.edge_id || ','
     FROM route_path rp
              INNER JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
              INNER JOIN t_transport_types tt ON t.type_id = tt.id
     WHERE rp.path NOT LIKE '%,' || t.to_location_id || ',%'
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
 
                                         JOIN t_location l ON route_path.to_location_id = l.id
                                  AND route_path.transport_type = :type
                                AND l.c_name = :toLocation
                                LIMIT :limit OFFSET :offset  ) as "rp" ORDER BY rp.dep_time, rp.total_time_cost;
 
            
            """;

    public static final String SELECT_ALL_ROUTE_WITH_DFS = """
            WITH  RECURSIVE route_path AS (
                SELECT
                    t.from_location_id,
                    t.to_location_id,
                    ',' || t.from_location_id || ',' || t.to_location_id || ',' AS path,
                    t.price AS total_price,
                    t.departure_time AS dep_time,
                    t.departure_time + (t.time_cost || ' minute')::interval AS arr_time,
                    t.time_cost AS total_time_cost,
                    tt.type_name AS transport_type,
                    ',' || t.edge_id || ',' AS edge_path
                FROM t_location_graph t
                INNER JOIN t_transport_types tt ON t.type_id = tt.id
                WHERE t.from_location_id = :fromLocation
            
                UNION ALL
            
                SELECT
                    rp.from_location_id,
                    t.to_location_id,
                    rp.path || t.to_location_id || ',',
                    rp.total_price + t.price,
                    rp.dep_time,
                    rp.dep_time + ((rp.total_time_cost + t.time_cost) || ' minute')::interval AS arr_time,
                    rp.total_time_cost + t.time_cost,
                    CASE
                        WHEN rp.transport_type = 'микс' THEN 'микс'
                        WHEN rp.transport_type = tt.type_name THEN rp.transport_type
                        ELSE 'mix'
                    END AS transport_type,
                    rp.edge_path || t.edge_id || ','
                FROM route_path rp
                INNER JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
                INNER JOIN t_transport_types tt ON t.type_id = tt.id
                WHERE rp.path NOT LIKE '%,' || t.to_location_id || ',%'
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
