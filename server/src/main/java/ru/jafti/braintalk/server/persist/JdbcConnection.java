package ru.jafti.braintalk.server.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection implements DbConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        String btPostgresUser = System.getenv("BT_POSTGRES_USER");
        String btPostgresPass = System.getenv("BT_POSTGRES_PASS");
        String jdbcUrl = System.getenv("JDBC_URL");

        // Создание соединения
        try (Connection connection = DriverManager.getConnection(jdbcUrl, btPostgresUser, btPostgresPass)) {
            if (connection != null) {
                System.out.println("Соединение с PostgreSQL установлено!");
                this.connection = connection;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }
}
