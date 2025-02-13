CREATE TABLE IF NOT EXISTS transport_types (
                                 id INTEGER PRIMARY KEY,
                                 type_name TEXT NOT NULL UNIQUE,
                                 description TEXT
);
CREATE TABLE IF NOT EXISTS routes (
                        id TEXT PRIMARY KEY,
                        departure_city TEXT NOT NULL,
                        arrival_city   TEXT NOT NULL,
                        departure_time DATETIME NOT NULL,
                        arrival_time   DATETIME NOT NULL,
                        transport_type_id INTEGER NOT NULL,
                        price REAL,
                        FOREIGN KEY (transport_type_id) REFERENCES transport_types(id)
);
CREATE TABLE IF NOT EXISTS bookings (
                          id TEXT PRIMARY KEY,
                          route_id TEXT NOT NULL,
                          user_name TEXT NOT NULL,
                          booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                          status TEXT NOT NULL, -- значения: 'забронировано', 'отменено'
                          FOREIGN KEY (route_id) REFERENCES routes(id)
);