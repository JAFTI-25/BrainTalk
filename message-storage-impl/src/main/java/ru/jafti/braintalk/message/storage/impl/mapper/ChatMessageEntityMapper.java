package ru.jafti.braintalk.message.storage.impl.mapper;

import org.mapstruct.Mapper;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageEntityMapper {
    ChatMessageEntity map(StorableMessage entity);
    default String mapContent(StorableMessage.Content content) {
        return content.rawContent();
    }
}