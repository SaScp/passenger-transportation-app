CREATE TABLE IF NOT EXISTS t_location(
                                         id TEXT PRIMARY KEY,
                                         c_name TEXT NOT NULL,
                                         departure_time DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS t_location_graph(
                                               from_location_id TEXT NOT NULL,
                                               to_location_id TEXT NOT NULL,
                                               time_cost BIGINT NOT NULL,
                                               price DOUBLE,
                                               с_type INT,
                                               FOREIGN KEY (from_location_id) REFERENCES t_location(id),
    FOREIGN KEY (to_location_id) REFERENCES t_location(id),
    PRIMARY KEY(from_location_id, to_location_id)
    );


CREATE TABLE IF NOT EXISTS t_user(
                                     user_phone TEXT primary key
);
CREATE TABLE IF NOT EXISTS t_status(
                                       id INTEGER PRIMARY KEY,
                                       status TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS t_route (
                                       id TEXT PRIMARY KEY,
                                       departure_city TEXT NOT NULL,
                                       arrival_city   TEXT NOT NULL,
                                       departure_time DATETIME NOT NULL,
                                       arrival_time   DATETIME NOT NULL,
                                       FOREIGN KEY (departure_city) REFERENCES t_location(id),
                                       FOREIGN KEY (arrival_city) REFERENCES t_location(id)
);

CREATE TABLE IF NOT EXISTS t_booking (
                                         id TEXT PRIMARY KEY,
                                         route_id TEXT NOT NULL,
                                         booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         status_id INTEGER NOT NULL,
                                         user_phone TEXT NOT NULL,

                                         FOREIGN KEY (status_id) REFERENCES t_status(id),
    FOREIGN KEY (route_id) REFERENCES t_route(id),
    FOREIGN KEY (user_phone) REFERENCES t_user(user_phone) ON DELETE CASCADE
    );

CREATE TABLE t_route_step (
                              route_id TEXT NOT NULL,
                              route_step INTEGER NOT NULL,
                              location_id TEXT NOT NULL,
                              FOREIGN KEY (location_id) REFERENCES t_location(id),
                              FOREIGN KEY (route_id) REFERENCES t_route(id),
                              PRIMARY KEY (route_id, route_step)
);
