CREATE TABLE IF NOT EXISTS t_status(
                                             id INTEGER PRIMARY KEY,
                                             status TEXT NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS t_transport_types (
                                 id INTEGER PRIMARY KEY,
                                 type_name TEXT NOT NULL UNIQUE,
                                 description TEXT
);
CREATE TABLE IF NOT EXISTS t_routes (
                        id TEXT PRIMARY KEY,
                        departure_city TEXT NOT NULL,
                        arrival_city   TEXT NOT NULL,
                        departure_time DATETIME NOT NULL,
                        arrival_time   DATETIME NOT NULL,
                        transport_type_id INTEGER NOT NULL,
                        price REAL,
                        FOREIGN KEY (transport_type_id) REFERENCES t_transport_types(id)
);
CREATE TABLE IF NOT EXISTS t_bookings (
                          id TEXT PRIMARY KEY,
                          route_id TEXT NOT NULL,
                          booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                          status_id INTEGER NOT NULL, -- значения: 'забронировано', 'отменено'
                          FOREIGN KEY (route_id) REFERENCES t_routes(id),
                          FOREIGN KEY (status_id) REFERENCES t_status(id)
);

CREATE TABLE IF NOT EXISTS t_user(
    user_phone TEXT primary key
);

CREATE TABLE IF NOT EXISTS t_user_bookings(
                                     user_phone TEXT NOT NULL REFERENCES t_user(user_phone),
                                     booking_id TEXT NOT NULL REFERENCES t_bookings(id),
                                     PRIMARY KEY (user_phone, booking_id)
);

CREATE INDEX IF NOT EXISTS transport_type_id_idx on t_routes(transport_type_id);
CREATE INDEX IF NOT EXISTS t_bookings_idx on t_bookings(route_id);
CREATE INDEX IF NOT EXISTS t_booking_user_idx on t_user_bookings(booking_id);
CREATE INDEX IF NOT EXISTS t_user_booking_idx on t_user_bookings(user_phone);