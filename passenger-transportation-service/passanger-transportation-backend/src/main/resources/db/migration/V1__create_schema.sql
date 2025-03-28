CREATE TABLE IF NOT EXISTS t_location (
                                          id VARCHAR PRIMARY KEY,
                                          c_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS t_transport_types (
                                                 id SERIAL PRIMARY KEY,
                                                 type_name TEXT NOT NULL UNIQUE,
                                                 description TEXT
);

CREATE TABLE IF NOT EXISTS t_location_graph (
                                                edge_id SERIAL PRIMARY KEY,
                                                from_location_id VARCHAR NOT NULL,
                                                departure_time TIMESTAMP NOT NULL,
                                                to_location_id VARCHAR NOT NULL,
                                                time_cost BIGINT NOT NULL,
                                                price DOUBLE PRECISION,
                                                type_id INTEGER NOT NULL,
                                                FOREIGN KEY (from_location_id) REFERENCES t_location(id) ON DELETE CASCADE,
                                                FOREIGN KEY (to_location_id) REFERENCES t_location(id) ON DELETE CASCADE,
                                                FOREIGN KEY (type_id) REFERENCES t_transport_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_user (
                                      user_phone TEXT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS t_status (
                                        id SERIAL PRIMARY KEY,
                                        status TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS t_route (
                                       id VARCHAR PRIMARY KEY ,
                                       departure_city VARCHAR NOT NULL,
                                       arrival_city VARCHAR NOT NULL,
                                       departure_time TIMESTAMP NOT NULL,
                                       arrival_time TIMESTAMP NOT NULL,
                                       FOREIGN KEY (departure_city) REFERENCES t_location(id) ON DELETE CASCADE,
                                       FOREIGN KEY (arrival_city) REFERENCES t_location(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_booking (
                                         id VARCHAR PRIMARY KEY ,
                                         route_id VARCHAR NOT NULL,
                                         booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         status_id INTEGER NOT NULL,
                                         user_phone TEXT NOT NULL,
                                         FOREIGN KEY (status_id) REFERENCES t_status(id) ON DELETE CASCADE,
                                         FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE,
                                         FOREIGN KEY (user_phone) REFERENCES t_user(user_phone) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_route_step (
                                            id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
                                            route_id VARCHAR NOT NULL,
                                            route_step INTEGER NOT NULL,
                                            edge_id INTEGER NOT NULL,
                                            FOREIGN KEY (edge_id) REFERENCES t_location_graph(edge_id) ON DELETE CASCADE,
                                            FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE,
                                            UNIQUE (route_id, route_step)
);


CREATE INDEX IF NOT EXISTS idx_departure_time ON t_location_graph (departure_time);
CREATE INDEX IF NOT EXISTS idx_from_location_id ON t_location_graph (from_location_id);
CREATE INDEX IF NOT EXISTS idx_to_location_id  ON t_location_graph (to_location_id) ;
CREATE INDEX IF NOT EXISTS idx_transport_type ON t_transport_types (id);
CREATE INDEX IF NOT EXISTS idx_transport_type_name ON t_transport_types (type_name);
CREATE INDEX IF NOT EXISTS idx_status ON t_status (id);
CREATE INDEX IF NOT EXISTS idx_route_id ON t_route (id);
CREATE INDEX IF NOT EXISTS idx_route_departure_city ON t_route (departure_city);
CREATE INDEX IF NOT EXISTS idx_route_arrival_city ON t_route (arrival_city);
CREATE INDEX IF NOT EXISTS idx_from_to_type ON t_location_graph (from_location_id, to_location_id, type_id);
CREATE INDEX IF NOT EXISTS idx_edge_id ON t_location_graph (edge_id);
CREATE INDEX IF NOT EXISTS idx_route_step_route_id ON t_route_step (route_id);
CREATE INDEX IF NOT EXISTS idx_route_step_edge_id ON t_route_step (edge_id);
CREATE INDEX IF NOT EXISTS  idx_location_name ON t_location(c_name) ;
CREATE INDEX IF NOT EXISTS idx_initial_edges
    ON t_location_graph(from_location_id, departure_time);
