package ru.jafti.persist;

import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.persist.DbConnection;
import ru.jafti.braintalk.message.storage.impl.persist.DbInitializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

// Небезопасный код
public class MessageStorageImpl implements MessageStorage {
    private final static String INSERT_REQUEST_FORMAT = "INSERT INTO " + DbInitializer.TABLE_NAME + " VALUES ('%s', " +
            "'%s', '%s', '%s')";

    private final DbConnection dbConnection;

    public MessageStorageImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    @Override
    public void store(StorableMessage storableMessage) {
        long messageId = convertStringToLong(storableMessage.messageId());
        var insertSql = String.format(INSERT_REQUEST_FORMAT, messageId,
                storableMessage.from().talkerGuid(),
                storableMessage.to().talkerGuid(),
                storableMessage.content());
        Connection connection = dbConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private long convertStringToLong(String string){
        try{
            var uuid = UUID.fromString(string);
            return uuid.getMostSignificantBits();
        }
        catch(Exception e){
            return Long.parseLong(string);
        }
    }
}
