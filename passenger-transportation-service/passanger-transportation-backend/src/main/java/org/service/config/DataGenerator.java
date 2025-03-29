package org.service.config;

import net.datafaker.Faker;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class DataGenerator {

    // Параметры подключения к БД
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/passanger_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Operator332@@";

    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("ru"));
        Random random = new Random();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Отключаем автокоммит для транзакций
            conn.setAutoCommit(false);

            // 1. Генерация локаций (например, 10 записей)
            List<String> locationIds = new ArrayList<>();
            String insertLocationSQL = "INSERT INTO t_location (id, c_name) VALUES (?, ?) ON CONFLICT DO NOTHING;";
            try (PreparedStatement ps = conn.prepareStatement(insertLocationSQL)) {
                for (int i = 1; i <= 100; i++) {
                    String locId = String.format("L%03d", i);
                    locationIds.add(locId);
                    ps.setString(1, locId);
                    ps.setString(2, faker.address().city());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 2. Генерация типов транспорта
            String[][] transportTypes = {
                    {"Автобус", "Городской и междугородный автобус"},
                    {"Поезд", "Региональный и междугородний поезд"},
                    {"Самолёт", "Авиаперелёты"}
            };
            String insertTransportSQL = "INSERT INTO t_transport_types (type_name, description) VALUES (?, ?) ON CONFLICT DO NOTHING;";
            try (PreparedStatement ps = conn.prepareStatement(insertTransportSQL)) {
                for (String[] type : transportTypes) {
                    ps.setString(1, type[0]);
                    ps.setString(2, type[1]);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Получаем id типов транспорта
            Map<String, Integer> transportTypeMap = new HashMap<>();
            String selectTransportSQL = "SELECT id, type_name FROM t_transport_types;";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectTransportSQL)) {
                while (rs.next()) {
                    transportTypeMap.put(rs.getString("type_name"), rs.getInt("id"));
                }
            }

            // 3. Генерация рёбер графа (например, 20 записей)
            List<Integer> edgeIds = new ArrayList<>();
            String insertEdgeSQL = "INSERT INTO t_location_graph " +
                    "(from_location_id, departure_time, to_location_id, time_cost, price, type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING edge_id;";
            try (PreparedStatement ps = conn.prepareStatement(insertEdgeSQL)) {
                for (int i = 0; i < 1000; i++) {
                    // Выбираем случайную локацию для отправления и прибытия
                    String fromLoc = locationIds.get(random.nextInt(locationIds.size()));
                    String toLoc = locationIds.get(random.nextInt(locationIds.size()));
                    while (toLoc.equals(fromLoc)) {
                        toLoc = locationIds.get(random.nextInt(locationIds.size()));
                    }
                    // Случайное время отправления: текущее время + от 1 до 100 часов
                    LocalDateTime departureTime = LocalDateTime.now().plusHours(random.nextInt(100) + 1);
                    int timeCost = random.nextInt(271) + 30; // от 30 до 300 минут
                    double price = Math.round((5.0 + random.nextDouble() * 95.0) * 100.0) / 100.0;
                    // Выбираем случайный тип транспорта
                    List<String> types = new ArrayList<>(transportTypeMap.keySet());
                    String typeName = types.get(random.nextInt(types.size()));
                    int typeId = transportTypeMap.get(typeName);

                    ps.setString(1, fromLoc);
                    ps.setTimestamp(2, Timestamp.valueOf(departureTime));
                    ps.setString(3, toLoc);
                    ps.setInt(4, timeCost);
                    ps.setDouble(5, price);
                    ps.setInt(6, typeId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        edgeIds.add(rs.getInt("edge_id"));
                    }
                    rs.close();
                }
            }

            // 4. Генерация пользователей (например, 5 записей)
            List<String> userPhones = new ArrayList<>();
            String insertUserSQL = "INSERT INTO t_user (user_phone) VALUES (?) ON CONFLICT DO NOTHING;";
            try (PreparedStatement ps = conn.prepareStatement(insertUserSQL)) {
                for (int i = 0; i < 5; i++) {
                    String phone = faker.phoneNumber().phoneNumber();
                    userPhones.add(phone);
                    ps.setString(1, phone);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 5. Генерация статусов бронирования
            String[] statuses = {"Ожидается", "Подтверждено", "Отменено"};
            String insertStatusSQL = "INSERT INTO t_status (status) VALUES (?) ON CONFLICT DO NOTHING;";
            try (PreparedStatement ps = conn.prepareStatement(insertStatusSQL)) {
                for (String status : statuses) {
                    ps.setString(1, status);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Получаем id статусов
            Map<String, Integer> statusMap = new HashMap<>();
            String selectStatusSQL = "SELECT id, status FROM t_status;";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectStatusSQL)) {
                while (rs.next()) {
                    statusMap.put(rs.getString("status"), rs.getInt("id"));
                }
            }

            // 6. Генерация маршрутов (например, 10 записей)
            List<String> routeIds = new ArrayList<>();
            String insertRouteSQL = "INSERT INTO t_route (id, departure_city, arrival_city, departure_time, arrival_time) " +
                    "VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertRouteSQL)) {
                for (int i = 1; i <= 10; i++) {
                    String routeId = String.format("R%03d", i);
                    routeIds.add(routeId);
                    String depCity = locationIds.get(random.nextInt(locationIds.size()));
                    String arrCity = locationIds.get(random.nextInt(locationIds.size()));
                    while (arrCity.equals(depCity)) {
                        arrCity = locationIds.get(random.nextInt(locationIds.size()));
                    }
                    LocalDateTime depTime = LocalDateTime.now().plusHours(random.nextInt(50) + 1);
                    LocalDateTime arrTime = depTime.plusHours(random.nextInt(5) + 1);
                    ps.setString(1, routeId);
                    ps.setString(2, depCity);
                    ps.setString(3, arrCity);
                    ps.setTimestamp(4, Timestamp.valueOf(depTime));
                    ps.setTimestamp(5, Timestamp.valueOf(arrTime));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 7. Генерация бронирований (например, 15 записей)
            String insertBookingSQL = "INSERT INTO t_booking (id, route_id, booking_time, status_id, user_phone) " +
                    "VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertBookingSQL)) {
                for (int i = 1; i <= 15; i++) {
                    String bookingId = String.format("B%03d", i);
                    String routeId = routeIds.get(random.nextInt(routeIds.size()));
                    LocalDateTime bookingTime = LocalDateTime.now().minusHours(random.nextInt(10) + 1);
                    // Выбираем случайный статус
                    String status = statuses[random.nextInt(statuses.length)];
                    int statusId = statusMap.get(status);
                    String userPhone = userPhones.get(random.nextInt(userPhones.size()));

                    ps.setString(1, bookingId);
                    ps.setString(2, routeId);
                    ps.setTimestamp(3, Timestamp.valueOf(bookingTime));
                    ps.setInt(4, statusId);
                    ps.setString(5, userPhone);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 8. Генерация шагов маршрута для каждого маршрута (2-4 шага)
            String insertRouteStepSQL = "INSERT INTO t_route_step (route_id, route_step, edge_id) " +
                    "VALUES (?, ?, ?) ON CONFLICT DO NOTHING;";
            try (PreparedStatement ps = conn.prepareStatement(insertRouteStepSQL)) {
                for (String routeId : routeIds) {
                    int numSteps = random.nextInt(3) + 2; // 2-4 шага
                    // Выбираем случайные уникальные edge_id
                    Collections.shuffle(edgeIds);
                    List<Integer> selectedEdges = edgeIds.subList(0, numSteps);
                    for (int step = 1; step <= numSteps; step++) {
                        ps.setString(1, routeId);
                        ps.setInt(2, step);
                        ps.setInt(3, selectedEdges.get(step - 1));
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            // Фиксируем транзакцию
            conn.commit();
            System.out.println("Генерация тестовых данных завершена.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
