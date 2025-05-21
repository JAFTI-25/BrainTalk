package ru.jafti.braintalk.message.storage.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

@Mapper(componentModel = "spring")
public interface StorableMessageEntityMapper {

    @Mapping(target = "messageId", expression = "java(Long.parseLong(message.messageId()))")
    @Mapping(target = "fromTalker", expression = "java(message.from().talkerGuid())")
    @Mapping(target = "toTalker", expression = "java(message.to().talkerGuid())")
    @Mapping(target = "content", expression = "java(message.content().rawContent())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    ChatMessageEntity map(StorableMessage message);
}