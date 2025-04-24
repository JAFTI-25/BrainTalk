package ru.jafti.persist;

import java.sql.SQLException;
import java.sql.Statement;

public final class DbInitializer {
    public final static String TABLE_NAME = "chat_history";
    public final static String ID_COLUMN_NAME = "message_id";

    public final static String FROM_TALKER_ID_COLUMN_NAME = "from_talker";
    public final static String TO_TALKER_ID_COLUMN_NAME = "to_talker";

    public final static String CONTENT_COLUMN_NAME = "content";
    public final static String TIME_COLUMN_NAME = "created_at";

    public static void initialize(DbConnection dbConnection) {
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