package ru.jafti.braintalk.message.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.persist.DbConnection;
import ru.jafti.braintalk.message.storage.impl.persist.DbInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static ru.jafti.braintalk.message.storage.impl.persist.DbInitializer.ID_COLUMN_NAME;

@Component
public class MessageStorageImpl implements MessageStorage {

    private static final Logger log = LoggerFactory.getLogger(MessageStorageImpl.class);
    private final static String INSERT_QUERY = "INSERT INTO " + DbInitializer.TABLE_NAME + " VALUES (?, ?, ?, ?)";

    private final PreparedStatement insertStatement;


    public MessageStorageImpl( @Qualifier("MessageStoreJdbcConnection") DbConnection dbConnection) {
        try {
            this.insertStatement = dbConnection.getConnection().prepareStatement(INSERT_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void store(StorableMessage storableMessage) {
        log.trace("Store message {}", storableMessage);
        long messageId = convertStringToLong(storableMessage.messageId());
        try {
            insertStatement.setLong(1, messageId);
            insertStatement.setObject(2, storableMessage.from().talkerGuid());
            insertStatement.setObject(3, storableMessage.to().talkerGuid());
            insertStatement.setString(4, storableMessage.content().rawContent());
            insertStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.trace("Store message success {}", messageId);
    }

    private long convertStringToLong(String string) {
        return Long.parseLong(string);
    }
}
