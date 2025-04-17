package ru.jafti.braintalk.talker.profile.impl.persist;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class JdbcConnection implements DbConnection {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    @PostConstruct
    public void connect() {
        if (connection != null) {
            return;
        }

        String btPostgresUser = System.getenv("BT_POSTGRES_USER");
        if (btPostgresUser == null) {
            throw new RuntimeException("Переменная окружения BT_POSTGRES_USER не задана.");
        }

        String btPostgresPass = System.getenv("BT_POSTGRES_PASSWORD");
        if (btPostgresPass == null) {
            throw new RuntimeException("Переменная окружения BT_POSTGRES_PASSWORD не задана.");
        }

        String jdbcUrl = System.getenv("JDBC_URL");
        if (jdbcUrl == null) {
            throw new RuntimeException("Переменная окружения JDBC_URL не задана.");
        }

        try {
            connection = DriverManager.getConnection(jdbcUrl, btPostgresUser, btPostgresPass);
            if (connection != null) {
                System.out.println("Соединение с PostgreSQL установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }
}

