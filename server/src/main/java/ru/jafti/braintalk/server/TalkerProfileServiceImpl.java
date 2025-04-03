package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.persist.DbConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;

public class TalkerProfileServiceImpl implements TalkerProfileService {
    private final Statement dbStatement;

    public TalkerProfileServiceImpl(DbConnection dbConnection) {
        try {
            dbStatement = dbConnection.getConnection().createStatement();

            var createTableSql = "CREATE TABLE IF NOT EXISTS talker_profile (talker_id UUID PRIMARY KEY, nickname TEXT)";
            dbStatement.execute(createTableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID findByNickname(String nickname) {
        try {
            var selectSql = "SELECT * FROM talker_profile";
            var resultSet = dbStatement.executeQuery(selectSql);

            while (resultSet.next()) {
                var uuid = UUID.fromString(resultSet.getString("talker_id"));
                var storedNickname = resultSet.getString("nickname");

                if (Objects.equals(nickname, storedNickname)) {
                    return uuid;
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID createWithNickname(String nickname) {
        var uuid = UUID.randomUUID();
        var insertSql = String.format("INSERT INTO talker_profile VALUES ('%s', '%s')", uuid, nickname);

        try {
            dbStatement.executeUpdate(insertSql);
            return uuid;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
