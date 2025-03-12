CREATE TABLE IF NOT EXISTS t_location (
                                          id VARCHAR PRIMARY KEY,
                                          c_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS t_transport_types (
                                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                                 type_name VARCHAR NOT NULL UNIQUE,
                                                 description VARCHAR
);

CREATE TABLE IF NOT EXISTS t_location_graph (
                                                edge_id INT PRIMARY KEY AUTO_INCREMENT,
                                                from_location_id VARCHAR NOT NULL,
                                                departure_time TIMESTAMP NOT NULL,
                                                to_location_id VARCHAR NOT NULL,
                                                time_cost BIGINT NOT NULL,
                                                price DOUBLE,
                                                type_id INT NOT NULL,
                                                FOREIGN KEY (from_location_id) REFERENCES t_location(id),
    FOREIGN KEY (to_location_id) REFERENCES t_location(id),
    FOREIGN KEY (type_id) REFERENCES t_transport_types(id)
    );

CREATE TABLE IF NOT EXISTS t_user (
                                      user_phone VARCHAR PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS t_status (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        status VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS t_route (
                                       id VARCHAR PRIMARY KEY,
                                       departure_city VARCHAR,
                                       arrival_city VARCHAR,
                                       departure_time TIMESTAMP NOT NULL,
                                       arrival_time TIMESTAMP NOT NULL,
                                       FOREIGN KEY (departure_city) REFERENCES t_location(id),
    FOREIGN KEY (arrival_city) REFERENCES t_location(id)
    );

CREATE TABLE IF NOT EXISTS t_booking (
                                         id VARCHAR PRIMARY KEY,
                                         route_id VARCHAR NOT NULL,
                                         booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         status_id INT NOT NULL,
                                         user_phone VARCHAR NOT NULL,
                                         FOREIGN KEY (status_id) REFERENCES t_status(id),
    FOREIGN KEY (route_id) REFERENCES t_route(id),
    FOREIGN KEY (user_phone) REFERENCES t_user(user_phone) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS t_route_step (
                                            route_id VARCHAR NOT NULL,
                                            route_step INT NOT NULL,
                                            edge_id INT NOT NULL,
                                            FOREIGN KEY (edge_id) REFERENCES t_location_graph(edge_id),
    FOREIGN KEY (route_id) REFERENCES t_route(id),
    PRIMARY KEY (route_id, route_step)
    );




