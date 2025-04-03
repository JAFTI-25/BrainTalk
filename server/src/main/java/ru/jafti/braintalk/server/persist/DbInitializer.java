package ru.jafti.braintalk.server.persist;

import java.sql.SQLException;
import java.sql.Statement;

public final class DbInitializer {
    public final static String TABLE_NAME = "talker_profile";
    public final static String ID_COLUMN_NAME = "talker_id";
    public final static String NICKNAME_COLUMN_NAME = "nickname";

    public static void initialize(DbConnection dbConnection) {
        try (Statement statement = dbConnection.getConnection().createStatement()) {
            var createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (%s UUID PRIMARY KEY, %s TEXT)",
                    TABLE_NAME, ID_COLUMN_NAME, NICKNAME_COLUMN_NAME);

            statement.execute(createTableSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
