package ru.jafti.braintalk.message.storage.api;

import ru.jafti.braintalk.message.storage.api.model.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface MessageFetcher {

    List<ChatMessage> findByFromTalker(UUID fromTalker);
    List<ChatMessage> findByToTalker(UUID toTalker);
    List<ChatMessage> findConversationBetween(UUID talker1, UUID talker2);
}
