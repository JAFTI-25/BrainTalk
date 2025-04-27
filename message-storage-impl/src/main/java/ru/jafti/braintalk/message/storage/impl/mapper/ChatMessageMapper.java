package ru.jafti.braintalk.message.storage.impl.mapper;

import org.mapstruct.Mapper;
import ru.jafti.braintalk.message.storage.api.model.ChatMessage;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessage map(ChatMessageEntity entity);
    List<ChatMessage> map(List<ChatMessageEntity> entity);
}
