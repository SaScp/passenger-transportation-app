package org.service.output_port.repository;


import org.antlr.v4.runtime.misc.NotNull;
import org.service.output_port.model.Route;
import org.service.output_port.model.RouteEntityTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @Query("select route.id from Route route where route.departureCity.id = :id")
    List<String> findRoutesIdByDepId(@Param("id") String id, Pageable pageable);

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId", "routeSteps.edgeId.fromLocationId", "routeSteps.edgeId.type"})
    List<Route> findRoutesByIdIn(List<String> ids, Sort sort);

    @EntityGraph(attributePaths = {"departureCity", "arrivalCity", "routeSteps", "routeSteps.edgeId.toLocationId","routeSteps.edgeId.fromLocationId", "routeSteps.edgeId.type"})
    List<Route> findRoutesByIdIn(Collection<String> ids);
    @Override
    @EntityGraph(attributePaths = {"departureCity", "arrivalCity",  "routeSteps"})
    Page<Route> findAll(Pageable pageable);

    @Query("select route.id from Route route")
    Page<String> findIds(Pageable pageable);


    @Query(value = """
            
            WITH RECURSIVE route_path (
                                                   from_location_id,
                                                   to_location_id,
                                                   path,
                                                   total_price,
                                                   dep_time,
                                                   arr_time,
                                                   total_time_cost,
                                                   transport_type,
                                                   edge_path
                            ) AS (
                         
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
                                     JOIN t_transport_types tt ON t.type_id = tt.id
                                     JOIN t_location l ON t.from_location_id = l.id
                            WHERE l.c_name = :fromLocation
            
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
                                     JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
                                     JOIN t_transport_types tt ON t.type_id = tt.id
                            WHERE rp.path NOT LIKE '%,' || t.to_location_id || ',%'
                        )
                        SELECT
                            rp.from_location_id,
                            rp.to_location_id,
                            rp.edge_path,
                            rp.total_price,
                            rp.dep_time,
                            rp.arr_time,
                            rp.total_time_cost,
                            rp.transport_type
                        FROM route_path rp
                                 JOIN t_location l ON rp.to_location_id = l.id
                        WHERE l.c_name = :toLocation
                          AND rp.transport_type = :type
                        ORDER BY rp.total_time_cost, rp.total_price, rp.dep_time
                        LIMIT :limit
            
            OFFSET :offset;
            
            """, nativeQuery = true)
    List<RouteEntityTemp> findRecursiveRoutes(
            @Param("fromLocation") String fromLocation,
            @Param("toLocation") String toLocation,
            @NotNull @Param("type") String type,
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = """

            WITH RECURSIVE route_path (
                                       from_location_id,
                                       to_location_id,
                                       path,
                                       total_price,
                                       dep_time,
                                       arr_time,
                                       total_time_cost,
                                       transport_type,
                                       edge_path
                ) AS (SELECT t.from_location_id,
                             t.to_location_id,
                             ',' || t.from_location_id || ',' || t.to_location_id || ',' AS path,
                             t.price                                                     AS total_price,
                             t.departure_time                                            AS dep_time,
                             t.departure_time + (t.time_cost || ' minute')::interval     AS arr_time,
                             t.time_cost                                                 AS total_time_cost,
                             tt.type_name                                                AS transport_type,
                             ',' || t.edge_id || ','                                     AS edge_path
                      FROM t_location_graph t
                               JOIN t_transport_types tt ON t.type_id = tt.id
                               JOIN t_location l ON t.from_location_id = l.id
                      WHERE l.c_name = :fromLocation
            
                      UNION ALL
            
            
                      SELECT rp.from_location_id,
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
                                 END                                                                   AS transport_type,
                             rp.edge_path || t.edge_id || ','
                      FROM route_path rp
                               JOIN t_location_graph t ON rp.to_location_id = t.from_location_id
                               JOIN t_transport_types tt ON t.type_id = tt.id
                      WHERE rp.path NOT LIKE '%,' || t.to_location_id || ',%')
            SELECT rp.from_location_id,
                   rp.to_location_id,
                   rp.edge_path,
                   rp.total_price,
                   rp.dep_time,
                   rp.arr_time,
                   rp.total_time_cost,
                   rp.transport_type
            FROM route_path rp
                     JOIN t_location l ON rp.to_location_id = l.id
            ORDER BY rp.total_time_cost, rp.total_price, rp.dep_time
            LIMIT :limit OFFSET :offset;
            
            """, nativeQuery = true)
    List<RouteEntityTemp> findAllRecursiveRoutes(
            @Param("fromLocation") String fromLocation,
            @Param("limit") int limit,
            @Param("offset") int offset);
}
