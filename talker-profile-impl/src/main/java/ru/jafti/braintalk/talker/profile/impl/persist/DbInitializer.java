package ru.jafti.braintalk.talker.profile.impl.persist;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

@Component("TalkerProfileDbInitializer")
public final class DbInitializer {
    public final static String TABLE_NAME = "talker_profile";
    public final static String ID_COLUMN_NAME = "talker_id";
    public final static String NICKNAME_COLUMN_NAME = "nickname";

    @Qualifier("TalkerProfileJdbcConnection")
    public final DbConnection dbConnection;

    public DbInitializer(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @PostConstruct
    public void initialize() {
        dbConnection.connect();

        try (Statement statement = dbConnection.getConnection().createStatement()) {
            var createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (%s UUID PRIMARY KEY, %s TEXT)",
                    TABLE_NAME, ID_COLUMN_NAME, NICKNAME_COLUMN_NAME);

            statement.execute(createTableSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
