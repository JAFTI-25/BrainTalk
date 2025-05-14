package ru.jafti.braintalk.message.storage.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.persist.DbConnection;
import ru.jafti.braintalk.message.storage.persist.DbInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MessageStorageImpl implements MessageStorage {
    private static final String INSERT_REQUEST_FORMAT = "INSERT INTO " + DbInitializer.TABLE_NAME + " VALUES ('%s', " +
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
                storableMessage.content().rawContent());
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.executeUpdate();
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
