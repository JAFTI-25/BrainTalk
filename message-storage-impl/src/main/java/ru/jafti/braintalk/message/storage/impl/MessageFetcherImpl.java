package ru.jafti.braintalk.message.storage.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.storage.api.MessageFetcher;
import ru.jafti.braintalk.message.storage.api.model.ChatMessage;
import ru.jafti.braintalk.message.storage.impl.mapper.ChatMessageMapper;
import ru.jafti.braintalk.message.storage.impl.repository.ChatMessageRepository;

import java.util.List;
import java.util.UUID;

@Component
public class MessageFetcherImpl implements MessageFetcher {

    private final ChatMessageRepository repository;
    private final ChatMessageMapper mapper;

    public MessageFetcherImpl(ChatMessageRepository repository, ChatMessageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ChatMessage> findByFromTalker(UUID fromTalker) {
        return mapper.map(repository.findByFromTalker(fromTalker));
    }

    @Override
    public List<ChatMessage> findByToTalker(UUID toTalker) {
        return mapper.map(repository.findByToTalker(toTalker));
    }

    @Override
    public List<ChatMessage> findConversationBetween(UUID talker1, UUID talker2) {
        return mapper.map(repository.findConversationBetween(talker1, talker2));
    }
}

