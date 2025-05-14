package ru.jafti.braintalk.message.storage.persist;

import java.sql.SQLException;
import java.sql.Statement;

import jakarta.annotation.PostConstruct;

public final class DbInitializer {
    public static final String TABLE_NAME = "chat_history";
    public static final String ID_COLUMN_NAME = "message_id";

    public static final String FROM_TALKER_ID_COLUMN_NAME = "from_talker";
    public static final String TO_TALKER_ID_COLUMN_NAME = "to_talker";

    public static final String CONTENT_COLUMN_NAME = "content";
    public static final String TIME_COLUMN_NAME = "created_at";

    public final DbConnection dbConnection;

    public DbInitializer(DbConnection connection) {
        dbConnection = connection;
    }

    @PostConstruct
    public static void initialize(DbConnection dbConnection) {
        dbConnection.connect();

        try (Statement statement = dbConnection.getConnection().createStatement()) {
            var createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (%s LONG PRIMARY KEY, %s UUID, %S UUID," +
                            " %s TEXT, %s TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
                    TABLE_NAME, ID_COLUMN_NAME, FROM_TALKER_ID_COLUMN_NAME, TO_TALKER_ID_COLUMN_NAME,
                    CONTENT_COLUMN_NAME, TIME_COLUMN_NAME);

            statement.execute(createTableSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}