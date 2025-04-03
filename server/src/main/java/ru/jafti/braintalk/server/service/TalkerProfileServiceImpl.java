package ru.jafti.braintalk.server.service;

import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.persist.DbInitializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;

public class TalkerProfileServiceImpl implements TalkerProfileService {
    private final static String SELECT_REQUEST = "SELECT * FROM " + DbInitializer.TABLE_NAME;
    private final static String INSERT_REQUEST_FORMAT = "INSERT INTO " + DbInitializer.TABLE_NAME + " VALUES ('%s', '%s')";

    private final DbConnection dbConnection;

    public TalkerProfileServiceImpl(DbConnection dbConnection) {
        DbInitializer.initialize(dbConnection);
        this.dbConnection = dbConnection;
    }

    @Override
    public String findById(UUID id) {
        Connection connection = dbConnection.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_REQUEST);

            while (resultSet.next()) {
                var storedUuid = UUID.fromString(resultSet.getString(DbInitializer.ID_COLUMN_NAME));
                var nickname = resultSet.getString(DbInitializer.NICKNAME_COLUMN_NAME);

                if (id == storedUuid) {
                    return nickname;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID findByNickname(String nickname) {
        Connection connection = dbConnection.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_REQUEST);

            while (resultSet.next()) {
                var uuid = UUID.fromString(resultSet.getString(DbInitializer.ID_COLUMN_NAME));
                var storedNickname = resultSet.getString(DbInitializer.NICKNAME_COLUMN_NAME);

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
        var insertSql = String.format(INSERT_REQUEST_FORMAT, uuid, nickname);
        Connection connection = dbConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSql);
            return uuid;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
