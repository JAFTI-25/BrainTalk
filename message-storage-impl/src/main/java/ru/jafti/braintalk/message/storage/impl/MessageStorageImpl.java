package ru.jafti.braintalk.message.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.repository.ChatMessageRepository;

@Component
public class MessageStorageImpl implements MessageStorage {

    private static final Logger log = LoggerFactory.getLogger(MessageStorageImpl.class);
    private final ChatMessageRepository chatMessageRepository;

    public MessageStorageImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void store(StorableMessage storableMessage) {
        log.trace("Store message {}", storableMessage);

        long messageId = convertStringToLong(storableMessage.messageId());
        chatMessageRepository.insertChatMessageEntity(
                messageId,
                storableMessage.from().talkerGuid(),
                storableMessage.to().talkerGuid(),
                storableMessage.content().rawContent()
        );

        log.trace("Store message success {}", messageId);
    }

    private long convertStringToLong(String string) {
        return Long.parseLong(string);
    }
}
