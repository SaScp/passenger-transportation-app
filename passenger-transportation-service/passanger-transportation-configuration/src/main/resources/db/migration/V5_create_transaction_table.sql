CREATE TABLE IF NOT EXISTS t_booking
(
    id           VARCHAR PRIMARY KEY,
    route_id     VARCHAR NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_id    INTEGER NOT NULL,
    user_id      bigint  NOT NULL,
    FOREIGN KEY (status_id) REFERENCES t_status (id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES t_route (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES t_user (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_transaction(
                                            id bigint PRIMARY KEY,
                                            user_id bigint REFERENCES t_user(id) NOT NULL,
                                            booking_id varchar REFERENCES t_booking(id),
                                            amount decimal NOT NULL default 0,
                                            date timestamp default CURRENT_TIMESTAMP
);