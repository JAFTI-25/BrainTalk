package ru.jafti.braintalk.message.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.persist.DbConnection;
import ru.jafti.braintalk.message.storage.impl.persist.DbInitializer;
import ru.jafti.braintalk.message.storage.impl.repository.ChatMessageRepository;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static ru.jafti.braintalk.message.storage.impl.persist.DbInitializer.ID_COLUMN_NAME;

@Component
public class MessageStorageImpl implements MessageStorage {
    private static final Logger log = LoggerFactory.getLogger(MessageStorageImpl.class);
    private final ChatMessageRepository repository;

    public MessageStorageImpl(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void store(StorableMessage storableMessage) {
        log.trace("Store message {}", storableMessage);

        ChatMessageEntity entity = new ChatMessageEntity();

        entity.setMessageId(convertStringToLong(storableMessage.messageId()));
        entity.setFromTalker(storableMessage.from().talkerGuid());
        entity.setToTalker(storableMessage.to().talkerGuid());
        entity.setContent(storableMessage.content().rawContent());

        repository.save(entity);
        log.trace("Store message success {}", entity.getMessageId());
    }
    private long convertStringToLong(String string) {
        return Long.parseLong(string);
    }
}
