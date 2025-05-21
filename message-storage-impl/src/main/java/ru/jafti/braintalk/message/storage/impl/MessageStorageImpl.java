package ru.jafti.braintalk.message.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.repository.ChatMessageRepository;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

import java.time.LocalDateTime;

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
        long messageId = convertStringToLong(storableMessage.messageId());

        ChatMessageEntity newEntity = new ChatMessageEntity();
        newEntity.setMessageId(messageId);
        newEntity.setFromTalker(storableMessage.from().talkerGuid());
        newEntity.setToTalker(storableMessage.to().talkerGuid());
        newEntity.setContent(storableMessage.content().rawContent());
        newEntity.setCreatedAt(LocalDateTime.now());

        repository.insert(newEntity);

        log.trace("Store message success {}", messageId);
    }

    private long convertStringToLong(String string) {
        return Long.parseLong(string);
    }
}
