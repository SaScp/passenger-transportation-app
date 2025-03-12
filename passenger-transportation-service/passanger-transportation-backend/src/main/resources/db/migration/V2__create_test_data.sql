
INSERT INTO t_location (id, c_name) VALUES
                                        ('MOW', 'Москва'),
                                        ('SPB', 'Санкт-Петербург'),
                                        ('NOV', 'Новосибирск'),
                                        ('EKB', 'Екатеринбург'),
                                        ('KZN', 'Казань'),
                                        ('NIZ', 'Нижний Новгород'),
                                        ('SAM', 'Самара'),
                                        ('OMS', 'Омск'),
                                        ('UFA', 'Уфа'),
                                        ('CHL', 'Челябинск'),
                                        ('VOR', 'Воронеж'),
                                        ('KRD', 'Краснодар'),
                                        ('PER', 'Пермь'),
                                        ('ROS', 'Ростов-на-Дону'),
                                        ('VOL', 'Волгоград');


INSERT INTO t_location (id, c_name) VALUES
                                        ('SOCHI', 'Сочи'),
                                        ('PSK', 'Псков'),
                                        ('IRK', 'Иркутск'),
                                        ('KEM', 'Кемерово'),
                                        ('SEV', 'Севастополь');


INSERT INTO t_location (id, c_name) VALUES
                                        ('VLA', 'Владивосток'),
                                        ('KHB', 'Хабаровск'),
                                        ('AST', 'Астрахань');


INSERT INTO t_transport_types (id, type_name, description) VALUES
                                                               (1, 'Автобус', 'Междугородный автобус'),
                                                               (2, 'Поезд', 'Железнодорожный транспорт'),
                                                               (3, 'Самолет', 'Авиаперелет'),
                                                               (4, 'Маршрутка', 'Местный перевозчик');


INSERT INTO t_transport_types (id, type_name, description) VALUES
    (5, 'Такси', 'Персональный автомобильный сервис');


INSERT INTO t_transport_types (id, type_name, description) VALUES
    (6, 'Вертолет', 'Авиаперевозки с использованием вертолёта');


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES
    ('MOW', '2023-06-01 07:00:00', 'SPB', 480, 2500.00, 2),  -- edge_id 1
    ('SPB', '2023-06-02 09:00:00', 'KZN', 720, 1500.00, 1),  -- edge_id 2
    ('KZN', '2023-06-03 11:00:00', 'EKB', 300, 2000.00, 2),  -- edge_id 3
    ('EKB', '2023-06-04 14:00:00', 'NOV', 120, 3500.00, 3),  -- edge_id 4
    ('NOV', '2023-06-05 16:00:00', 'NIZ', 900, 3000.00, 2);  -- edge_id 5


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-06-10 08:00:00', 'SAM', 300, 2200.00, 2);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SAM', '2023-06-10 15:00:00', 'UFA', 360, 1800.00, 2);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('UFA', '2023-06-11 10:00:00', 'CHL', 240, 2100.00, 2);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('CHL', '2023-06-11 16:00:00', 'PER', 180, 1500.00, 2);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SPB', '2023-06-12 07:00:00', 'ROS', 600, 2800.00, 1);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('ROS', '2023-06-12 18:00:00', 'VOL', 240, 1900.00, 1);


INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('VOL', '2023-06-13 08:00:00', 'KRD', 300, 2100.00, 1);

-- edge_id 13: Краснодар -> Москва (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KRD', '2023-06-13 14:00:00', 'MOW', 480, 3200.00, 2);

-- edge_id 14: Москва -> Екатеринбург (Самолет)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-06-14 07:00:00', 'EKB', 150, 4500.00, 3);

-- edge_id 15: Екатеринбург -> Нижний Новгород (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('EKB', '2023-06-14 11:00:00', 'NIZ', 200, 2300.00, 2);

-- edge_id 16: Санкт-Петербург -> Уфа (Маршрутка)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SPB', '2023-06-15 08:00:00', 'UFA', 480, 1200.00, 4);

-- edge_id 17: Санкт-Петербург -> Воронеж (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SPB', '2023-06-15 07:30:00', 'VOR', 360, 2000.00, 2);

-- edge_id 18: Воронеж -> Волгоград (Автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('VOR', '2023-06-15 14:00:00', 'VOL', 180, 1500.00, 1);

-- Новые ребра с дополнительными городами
-- edge_id 19: Новосибирск -> Иркутск (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('NOV', '2023-06-16 09:00:00', 'IRK', 720, 3500.00, 2);

-- edge_id 20: Иркутск -> Новосибирск (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('IRK', '2023-06-17 10:00:00', 'NOV', 700, 3400.00, 2);

-- edge_id 21: Екатеринбург -> Кемерово (Самолет)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('EKB', '2023-06-18 06:00:00', 'KEM', 180, 5000.00, 3);

-- edge_id 22: Кемерово -> Новосибирск (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KEM', '2023-06-18 20:00:00', 'NOV', 600, 2800.00, 2);

-- edge_id 23: Москва -> Псков (Автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-06-20 07:30:00', 'PSK', 300, 1900.00, 1);

-- edge_id 31: Псков -> Санкт-Петербург (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('PSK', '2023-06-20 13:00:00', 'SPB', 180, 1500.00, 2);

-- edge_id 25: Санкт-Петербург -> Севастополь (Автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SPB', '2023-06-21 09:00:00', 'SEV', 240, 2000.00, 1);

-- edge_id 26: Севастополь -> Москва (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SEV', '2023-06-21 14:00:00', 'MOW', 480, 2700.00, 2);

-- edge_id 27: Сочи -> Москва (Самолет)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SOCHI', '2023-06-22 10:00:00', 'MOW', 120, 6000.00, 3);

-- edge_id 28: Москва -> Сочи (Самолет)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-06-22 15:00:00', 'SOCHI', 130, 6200.00, 3);

-- edge_id 29: Уфа -> Ростов-на-Дону (Такси)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('UFA', '2023-06-23 08:00:00', 'ROS', 100, 1500.00, 5);

-- edge_id 30: Ростов-на-Дону -> Самара (Маршрутка)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('ROS', '2023-06-23 10:00:00', 'SAM', 150, 800.00, 4);

---------------------------------------------------
-- Дополнительные рёбра (из расширенных данных)
---------------------------------------------------
-- edge_id 24: Псков -> Санкт-Петербург (альтернативный вариант)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('PSK', '2023-06-20 10:00:00', 'SPB', 180, 1500.00, 2);

-- edge_id 32: Омск -> Челябинск (альтернативный маршрут)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('OMS', '2023-06-24 09:00:00', 'CHL', 300, 2600.00, 2);

-- edge_id 33: Челябинск -> Астрахань
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('CHL', '2023-06-24 14:00:00', 'AST', 200, 1800.00, 1);

-- edge_id 34: Астрахань -> Казань
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('AST', '2023-06-25 10:00:00', 'KZN', 220, 2100.00, 2);

-- edge_id 35: Хабаровск -> Владивосток (самолётом)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KHB', '2023-06-25 16:00:00', 'VLA', 240, 7000.00, 3);

-- edge_id 36: Москва -> Хабаровск (самолётом)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-06-26 06:00:00', 'KHB', 300, 8000.00, 3);

-- edge_id 37: Хабаровск -> Москва (самолётом)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KHB', '2023-06-26 12:00:00', 'MOW', 310, 8200.00, 3);

-- edge_id 38: Астрахань -> Уфа (автобусом)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('AST', '2023-06-27 08:00:00', 'UFA', 350, 2300.00, 1);

-- edge_id 39: Казань -> Уфа (альтернативный вариант, автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KZN', '2023-06-25 14:00:00', 'UFA', 360, 2500.00, 1);

---------------------------------------------------
-- Дополнительные рёбра для маршрутов R19-R22
---------------------------------------------------
-- edge_id 40: Санкт-Петербург -> Екатеринбург (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SPB', '2023-06-27 10:00:00', 'EKB', 360, 3100.00, 2);

-- edge_id 41: Екатеринбург -> Нижний Новгород (Поезд) – новый вариант
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('EKB', '2023-06-27 15:00:00', 'NIZ', 180, 2400.00, 2);

-- edge_id 42: Казань -> Самара (Автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KZN', '2023-06-28 08:00:00', 'SAM', 240, 1700.00, 1);

-- edge_id 43: Самара -> Омск (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SAM', '2023-06-28 12:00:00', 'OMS', 480, 3000.00, 2);

-- edge_id 44: Владивосток -> Хабаровск (Самолёт)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('VLA', '2023-06-29 09:00:00', 'KHB', 240, 7500.00, 3);

-- edge_id 45: Хабаровск -> Москва (Самолёт) – новый рейс с изменённым временем
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KHB', '2023-06-29 14:00:00', 'MOW', 320, 8300.00, 3);

-- edge_id 46: Новосибирск -> Екатеринбург (Поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('NOV', '2023-06-30 07:00:00', 'EKB', 600, 3500.00, 2);

-- edge_id 47: Екатеринбург -> Самара (Самолёт)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('EKB', '2023-06-30 17:30:00', 'SAM', 120, 4500.00, 3);

-- edge_id 48: Самара -> Уфа (Поезд) – скорректировано время и длительность
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SAM', '2023-06-30 20:00:00', 'UFA', 240, 1900.00, 2);

---------------------------------------------------
-- Дополнительные рёбра для маршрутов R23-R40
---------------------------------------------------
-- edge_id 49: Москва -> Новосибирск (прямой, новый вариант)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-07-01 07:00:00', 'NOV', 600, 4000.00, 2);

-- edge_id 50: Волгоград -> Ростов-на-Дону (прямой)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('VOL', '2023-07-01 08:00:00', 'ROS', 300, 2000.00, 1);

-- edge_id 51: Самара -> Ростов-на-Дону (такси)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SAM', '2023-07-01 09:00:00', 'ROS', 180, 1600.00, 5);

-- edge_id 52: Уфа -> Челябинск (новый вариант поезом)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('UFA', '2023-07-01 10:00:00', 'CHL', 260, 2200.00, 2);

-- edge_id 53: Челябинск -> Пермь (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('CHL', '2023-07-01 11:00:00', 'PER', 190, 1600.00, 2);

-- edge_id 54: Пермь -> Нижний Новгород (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('PER', '2023-07-01 12:00:00', 'NIZ', 210, 2300.00, 2);

-- edge_id 55: Краснодар -> Сочи (автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KRD', '2023-07-01 13:00:00', 'SOCHI', 120, 1900.00, 1);

-- edge_id 56: Сочи -> Ростов-на-Дону (автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SOCHI', '2023-07-01 14:00:00', 'ROS', 150, 2000.00, 1);

-- edge_id 57: Севастополь -> Сочи (автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('SEV', '2023-07-01 15:00:00', 'SOCHI', 300, 2500.00, 1);

-- edge_id 58: Нижний Новгород -> Казань (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('NIZ', '2023-07-01 16:00:00', 'KZN', 100, 1200.00, 2);

-- edge_id 59: Ростов-на-Дону -> Волгоград (автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('ROS', '2023-07-01 17:00:00', 'VOL', 220, 2100.00, 1);

-- edge_id 60: Омск -> Новосибирск (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('OMS', '2023-07-01 18:00:00', 'NOV', 400, 2900.00, 2);

-- edge_id 61: Иркутск -> Кемерово (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('IRK', '2023-07-01 19:00:00', 'KEM', 360, 3000.00, 2);

-- edge_id 62: Кемерово -> Новосибирск (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('KEM', '2023-07-01 20:00:00', 'NOV', 550, 2700.00, 2);

-- edge_id 63: Челябинск -> Волгоград (автобус)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('CHL', '2023-07-01 21:00:00', 'VOL', 260, 2400.00, 1);

-- edge_id 64: Пермь -> Самара (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('PER', '2023-07-01 22:00:00', 'SAM', 180, 2000.00, 2);

-- edge_id 65: Уфа -> Казань (поезд)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('UFA', '2023-07-01 23:00:00', 'KZN', 300, 2200.00, 2);

-- edge_id 66: Москва -> Севастополь (самолёт)
INSERT INTO t_location_graph (from_location_id, departure_time, to_location_id, time_cost, price, type_id)
VALUES ('MOW', '2023-07-02 07:00:00', 'SEV', 480, 2600.00, 3);

---------------------------------------------------
-- Таблица пользователей (t_user)
---------------------------------------------------
-- Первоначальные пользователи
INSERT INTO t_user (user_phone) VALUES
                                    ('+79001234567'),
                                    ('+79007654321'),
                                    ('+79005553322'),
                                    ('+79009997766'),
                                    ('+79003331122');

-- Дополнительные пользователи
INSERT INTO t_user (user_phone) VALUES
                                    ('+79001112233'),
                                    ('+79004445566'),
                                    ('+79007778899');

-- Дополнительные пользователи (из дополнительных данных)
INSERT INTO t_user (user_phone) VALUES
                                    ('+79008887766'),
                                    ('+79002223344');

---------------------------------------------------
-- Таблица статусов бронирования (t_status)
---------------------------------------------------
INSERT INTO t_status (id, status) VALUES
                                      (1, 'Подтверждено'),
                                      (2, 'Бронировано'),
                                      (3, 'Отменено'),
                                      (4, 'Ожидает подтверждения');

---------------------------------------------------
-- Таблица маршрутов (t_route)
---------------------------------------------------
-- Ранее добавленные маршруты
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES
    ('R1', 'MOW', 'SPB', '2023-06-01 07:00:00', '2023-06-01 15:00:00'),
    ('R2', 'SPB', 'KZN', '2023-06-02 09:00:00', '2023-06-02 21:00:00'),
    ('R3', 'MOW', 'EKB', '2023-06-01 07:00:00', '2023-06-03 16:00:00'),
    ('R4', 'MOW', 'UFA', '2023-06-10 08:00:00', '2023-06-10 21:00:00'),
    ('R5', 'SPB', 'VOL', '2023-06-15 07:30:00', '2023-06-15 17:00:00'),
    ('R6', 'SPB', 'UFA', '2023-06-15 08:00:00', '2023-06-15 16:00:00'),
    ('R7', 'MOW', 'EKB', '2023-06-14 07:00:00', '2023-06-14 09:30:00');

-- Новые маршруты (из первоначальных дополнительных данных)
-- R8: Новосибирск -> Иркутск (прямой, edge_id 19)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R8', 'NOV', 'IRK', '2023-06-16 09:00:00', '2023-06-16 21:00:00');

-- R9: Екатеринбург -> Кемерово -> Новосибирск (edge_id 21 и 22)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R9', 'EKB', 'NOV', '2023-06-18 06:00:00', '2023-06-19 06:00:00');

-- R10: Москва -> Псков -> Санкт-Петербург (edge_id 23 и 31)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R10', 'MOW', 'SPB', '2023-06-20 07:30:00', '2023-06-20 16:00:00');

-- R11: Санкт-Петербург -> Севастополь -> Москва (edge_id 25 и 26)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R11', 'SPB', 'MOW', '2023-06-21 09:00:00', '2023-06-21 22:00:00');

-- R12: Сочи -> Москва (прямой, edge_id 27)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R12', 'SOCHI', 'MOW', '2023-06-22 10:00:00', '2023-06-22 12:00:00');

-- R13: Москва -> Сочи (прямой, edge_id 28)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R13', 'MOW', 'SOCHI', '2023-06-22 15:00:00', '2023-06-22 17:10:00');

-- R14: Уфа -> Ростов-на-Дону -> Самара (edge_id 29 и 30)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R14', 'UFA', 'SAM', '2023-06-23 08:00:00', '2023-06-23 12:30:00');

---------------------------------------------------
-- Дополнительные маршруты (из расширенных данных)
---------------------------------------------------
-- R15: Омск -> Челябинск -> Астрахань (ребра edge_id 32 и 33)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R15', 'OMS', 'AST', '2023-06-24 09:00:00', '2023-06-24 16:00:00');

-- R16: Астрахань -> Казань -> Уфа (ребра edge_id 34 и 39)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R16', 'AST', 'UFA', '2023-06-25 10:00:00', '2023-06-25 20:00:00');

-- R17: Москва -> Хабаровск -> Москва (ребра edge_id 36 и 37)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R17', 'MOW', 'MOW', '2023-06-26 06:00:00', '2023-06-26 16:00:00');

-- R18: Хабаровск -> Владивосток (прямой маршрут, edge_id 35)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R18', 'KHB', 'VLA', '2023-06-25 16:00:00', '2023-06-25 20:00:00');

-- R19: Санкт-Петербург -> Нижний Новгород (через Екатеринбург)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R19', 'SPB', 'NIZ', '2023-06-27 10:00:00', '2023-06-27 18:00:00');

-- R20: Казань -> Омск (через Самару)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R20', 'KZN', 'OMS', '2023-06-28 08:00:00', '2023-06-28 20:00:00');

-- R21: Владивосток -> Москва (через Хабаровск)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R21', 'VLA', 'MOW', '2023-06-29 09:00:00', '2023-06-29 19:20:00');

-- R22: Новосибирск -> Уфа (через Екатеринбург и Самару)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R22', 'NOV', 'UFA', '2023-06-30 07:00:00', '2023-07-01 00:00:00');

-- R23: Москва -> Новосибирск (прямой, новый вариант)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R23', 'MOW', 'NOV', '2023-07-01 07:00:00', '2023-07-01 17:00:00');

-- R24: Волгоград -> Ростов-на-Дону (прямой)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R24', 'VOL', 'ROS', '2023-07-01 08:00:00', '2023-07-01 13:00:00');

-- R25: Самара -> Ростов-на-Дону (такси)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R25', 'SAM', 'ROS', '2023-07-01 09:00:00', '2023-07-01 12:00:00');

-- R26: Уфа -> Челябинск (новый вариант поезом)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R26', 'UFA', 'CHL', '2023-07-01 10:00:00', '2023-07-01 14:20:00');

-- R27: Челябинск -> Пермь (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R27', 'CHL', 'PER', '2023-07-01 11:00:00', '2023-07-01 14:10:00');

-- R28: Пермь -> Нижний Новгород (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R28', 'PER', 'NIZ', '2023-07-01 12:00:00', '2023-07-01 15:30:00');

-- R29: Краснодар -> Сочи (автобус)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R29', 'KRD', 'SOCHI', '2023-07-01 13:00:00', '2023-07-01 15:00:00');

-- R30: Сочи -> Ростов-на-Дону (автобус)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R30', 'SOCHI', 'ROS', '2023-07-01 14:00:00', '2023-07-01 16:30:00');

-- R31: Севастополь -> Сочи (автобус)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R31', 'SEV', 'SOCHI', '2023-07-01 15:00:00', '2023-07-01 20:00:00');

-- R32: Нижний Новгород -> Казань (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R32', 'NIZ', 'KZN', '2023-07-01 16:00:00', '2023-07-01 17:40:00');

-- R33: Ростов-на-Дону -> Волгоград (автобус)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R33', 'ROS', 'VOL', '2023-07-01 17:00:00', '2023-07-01 20:40:00');

-- R34: Омск -> Новосибирск (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R34', 'OMS', 'NOV', '2023-07-01 18:00:00', '2023-07-02 00:00:00');

-- R35: Иркутск -> Кемерово (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R35', 'IRK', 'KEM', '2023-07-01 19:00:00', '2023-07-02 01:00:00');

-- R36: Кемерово -> Новосибирск (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R36', 'KEM', 'NOV', '2023-07-01 20:00:00', '2023-07-02 04:30:00');

-- R37: Челябинск -> Волгоград (автобус)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R37', 'CHL', 'VOL', '2023-07-01 21:00:00', '2023-07-02 00:20:00');

-- R38: Пермь -> Самара (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R38', 'PER', 'SAM', '2023-07-01 22:00:00', '2023-07-02 01:00:00');

-- R39: Уфа -> Казань (поезд)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R39', 'UFA', 'KZN', '2023-07-01 23:00:00', '2023-07-02 04:00:00');

-- R40: Москва -> Севастополь (самолёт)
INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time)
VALUES ('R40', 'MOW', 'SEV', '2023-07-02 07:00:00', '2023-07-02 15:00:00');

---------------------------------------------------
-- Таблица бронирований (t_booking)
---------------------------------------------------
-- Ранее добавленные бронирования
INSERT INTO t_booking (id, route_id, status_id, user_phone)
VALUES
    ('B1', 'R1', 1, '+79001234567'),
    ('B2', 'R2', 2, '+79007654321'),
    ('B3', 'R4', 4, '+79005553322'),
    ('B4', 'R5', 2, '+79009997766'),
    ('B5', 'R6', 1, '+79003331122'),
    ('B6', 'R7', 3, '+79001234567');

-- Новые бронирования
INSERT INTO t_booking (id, route_id, status_id, user_phone)
VALUES
    ('B7', 'R8', 1, '+79001112233'),
    ('B8', 'R9', 2, '+79004445566'),
    ('B9', 'R10', 1, '+79007778899'),
    ('B10', 'R11', 4, '+79001234567'),
    ('B11', 'R12', 2, '+79007654321'),
    ('B12', 'R13', 1, '+79005553322'),
    ('B13', 'R14', 3, '+79003331122');

-- Дополнительные бронирования (из расширенных данных)
INSERT INTO t_booking (id, route_id, status_id, user_phone)
VALUES
    ('B14', 'R15', 2, '+79008887766'),
    ('B15', 'R16', 1, '+79002223344'),
    ('B16', 'R17', 4, '+79001112233'),
    ('B17', 'R18', 1, '+79004445566');

---------------------------------------------------
-- Таблица шагов маршрутов (t_route_step)
---------------------------------------------------
-- Для ранее добавленных маршрутов
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES
    ('R1', 1, '1'),
    ('R2', 1, '2'),
    ('R3', 1, '1'),
    ('R3', 2, '2'),
    ('R3', 3, '3'),
    ('R4', 1, '6'),
    ('R4', 2, '7'),
    ('R5', 1, '17'),
    ('R5', 2, '18'),
    ('R6', 1, '16'),
    ('R7', 1, '14');

-- Для новых маршрутов (из первоначальных дополнительных данных)
-- R8: Новосибирск -> Иркутск
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R8', 1, '19');

-- R9: Екатеринбург -> Кемерово -> Новосибирск
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R9', 1, '21'),
       ('R9', 2, '22');

-- R10: Москва -> Псков -> Санкт-Петербург
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R10', 1, '23'),
       ('R10', 2, '31');

-- R11: Санкт-Петербург -> Севастополь -> Москва
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R11', 1, '25'),
       ('R11', 2, '26');

-- R12: Сочи -> Москва
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R12', 1, '27');

-- R13: Москва -> Сочи
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R13', 1, '28');

-- R14: Уфа -> Ростов-на-Дону -> Самара
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R14', 1, '29'),
       ('R14', 2, '30');

---------------------------------------------------
-- Дополнительные шаги маршрутов (из расширенных данных)
---------------------------------------------------
-- R15: OMS -> CHL -> AST
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R15', 1, '32'),
       ('R15', 2, '33');

-- R16: AST -> KZN -> UFA
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R16', 1, '34'),
       ('R16', 2, '39');

-- R17: MOW -> KHB -> MOW
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R17', 1, '36'),
       ('R17', 2, '37');

-- R18: Хабаровск -> VLA
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R18', 1, '35');

-- R19: SPB -> NIZ (через EKB)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R19', 1, '40'),
       ('R19', 2, '41');

-- R20: KZN -> OMS (через SAM)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R20', 1, '42'),
       ('R20', 2, '43');

-- R21: VLA -> MOW (через KHB)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R21', 1, '44'),
       ('R21', 2, '45');

-- R22: NOV -> UFA (через EKB и SAM)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R22', 1, '46'),
       ('R22', 2, '47'),
       ('R22', 3, '48');

-- R23: MOW -> NOV (прямой)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R23', 1, '49');

-- R24: VOL -> ROS (прямой)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R24', 1, '50');

-- R25: SAM -> ROS (такси)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R25', 1, '51');

-- R26: UFA -> CHL (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R26', 1, '52');

-- R27: CHL -> PER (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R27', 1, '53');

-- R28: PER -> NIZ (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R28', 1, '54');

-- R29: KRD -> SOCHI (автобус)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R29', 1, '55');

-- R30: SOCHI -> ROS (автобус)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R30', 1, '56');

-- R31: SEV -> SOCHI (автобус)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R31', 1, '57');

-- R32: NIZ -> KZN (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R32', 1, '58');

-- R33: ROS -> VOL (автобус)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R33', 1, '59');

-- R34: OMS -> NOV (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R34', 1, '60');

-- R35: IRK -> KEM (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R35', 1, '61');

-- R36: KEM -> NOV (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R36', 1, '62');

-- R37: CHL -> VOL (автобус)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R37', 1, '63');

-- R38: PER -> SAM (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R38', 1, '64');

-- R39: UFA -> KZN (поезд)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R39', 1, '65');

-- R40: MOW -> SEV (самолёт)
INSERT INTO t_route_step (route_id, route_step, edge_id)
VALUES ('R40', 1, '66');
