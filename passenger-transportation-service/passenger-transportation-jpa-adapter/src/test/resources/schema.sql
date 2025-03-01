CREATE TABLE IF NOT EXISTS t_status (
                                        id INT PRIMARY KEY,
                                        status VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS t_transport_types (
                                                 id INT PRIMARY KEY,
                                                 type_name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(512)
    );

CREATE TABLE IF NOT EXISTS t_user (
                                      user_phone VARCHAR(20) PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS t_routes (
                                        id VARCHAR(50) PRIMARY KEY,
    departure_city VARCHAR(255) NOT NULL,
    arrival_city VARCHAR(255) NOT NULL,
    departure_time VARCHAR(255) NOT NULL,
    arrival_time VARCHAR(255) NOT NULL,
    transport_type_id INT NOT NULL,
    price DECIMAL(10,2),
    FOREIGN KEY (transport_type_id) REFERENCES t_transport_types(id)
    );

CREATE TABLE IF NOT EXISTS t_bookings (
                                          id VARCHAR(50) PRIMARY KEY,
    route_id VARCHAR(50) NOT NULL,
    booking_time VARCHAR(255),
    status_id INT NOT NULL,
    user_phone VARCHAR(20) NOT NULL,
    FOREIGN KEY (route_id) REFERENCES t_routes(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES t_status(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_phone) REFERENCES t_user(user_phone) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS transport_type_id_idx ON t_routes(transport_type_id);
CREATE INDEX IF NOT EXISTS t_bookings_idx ON t_bookings(route_id);
