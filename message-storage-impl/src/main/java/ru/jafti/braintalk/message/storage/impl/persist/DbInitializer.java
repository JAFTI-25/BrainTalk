package ru.jafti.braintalk.message.storage.impl.persist;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

@Component("MessageStorageDbInitializer")
public final class DbInitializer {
    public final static String TABLE_NAME = "chat_history";
    public final static String ID_COLUMN_NAME = "message_id";

    public final static String FROM_TALKER_ID_COLUMN_NAME = "from_talker";
    public final static String TO_TALKER_ID_COLUMN_NAME = "to_talker";

    public final static String CONTENT_COLUMN_NAME = "content";
    public final static String TIME_COLUMN_NAME = "created_at";

    private static final String INDEX_BY_PAIR_SQL = String.format("""
        CREATE INDEX IF NOT EXISTS %s 
        ON %s (
            LEAST(%s, %s),
            GREATEST(%s, %s),
            %s
        )
        """,
            "idx_chat_history_pair", TABLE_NAME,
            FROM_TALKER_ID_COLUMN_NAME, TO_TALKER_ID_COLUMN_NAME,
            FROM_TALKER_ID_COLUMN_NAME, TO_TALKER_ID_COLUMN_NAME,
            ID_COLUMN_NAME
    );

    private static final String INDEX_BY_FROM_SQL = String.format("""
        CREATE INDEX IF NOT EXISTS %s 
        ON %s (%s, %s)
        """,
            "idx_chat_history_from", TABLE_NAME,
            FROM_TALKER_ID_COLUMN_NAME, ID_COLUMN_NAME
    );

    private static final String INDEX_BY_TO_SQL = String.format("""
        CREATE INDEX IF NOT EXISTS %s 
        ON %s (%s, %s)
        """,
            "idx_chat_history_to", TABLE_NAME,
            TO_TALKER_ID_COLUMN_NAME, ID_COLUMN_NAME
    );

    @Qualifier("MessageStoreJdbcConnection")
    private final DbConnection dbConnection;

    public DbInitializer(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @PostConstruct
    public void initialize() {
        dbConnection.connect();

        try (Statement statement = dbConnection.getConnection().createStatement()) {
            var createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (%s BIGINT PRIMARY KEY, %s UUID, %S UUID," +
                            " %s TEXT, %s TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
                    TABLE_NAME, ID_COLUMN_NAME, FROM_TALKER_ID_COLUMN_NAME, TO_TALKER_ID_COLUMN_NAME,
                    CONTENT_COLUMN_NAME, TIME_COLUMN_NAME);

            statement.execute(createTableSql);
            statement.execute(INDEX_BY_PAIR_SQL);
            statement.execute(INDEX_BY_FROM_SQL);
            statement.execute(INDEX_BY_TO_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}