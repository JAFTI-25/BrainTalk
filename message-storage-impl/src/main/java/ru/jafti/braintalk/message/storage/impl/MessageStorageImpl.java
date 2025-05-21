package ru.jafti.braintalk.message.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageStorage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.mapper.ChatMessageEntityMapper;
import ru.jafti.braintalk.message.storage.impl.repository.ChatMessageRepository;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;


@Component
public class MessageStorageImpl implements MessageStorage {

    private static final Logger log = LoggerFactory.getLogger(MessageStorageImpl.class);
    private final ChatMessageEntityMapper mapper;
    private final ChatMessageRepository chatMessageRepository;


    public MessageStorageImpl(ChatMessageEntityMapper entityMapper, ChatMessageRepository chatMessageRepository) {
        this.mapper = entityMapper;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void store(StorableMessage storableMessage) {
        log.trace("Store message {}", storableMessage);
        ChatMessageEntity entity = mapper.map(storableMessage);
        chatMessageRepository.save(entity);
        log.trace("Store message success {}", entity.getMessageId());
    }

}
