package ru.jafti.braintalk.message.storage.api.model;

import java.util.UUID;

public record StorableMessage(
        String messageId,
        From from,
        To to,
        Content content
) {

    public record From(String nickname, UUID talkerGuid) {
    }

    public record To(String nickname, UUID talkerGuid) {
    }

    public record Content(String rawContent, Content.ContentType contentType) {

        public enum ContentType {
            TEXT
        }
    }

    public static StorableMessage buildFrom(
            String messageId,
            String fromTalkerNickName,
            UUID fromTalkerGuid,
            String toTalkerNickname,
            UUID toTalkerGuid,
            String message) {
        var from = new From(fromTalkerNickName, fromTalkerGuid);
        var to = new StorableMessage.To(toTalkerNickname, toTalkerGuid);
        var content = new Content(message, Content.ContentType.TEXT);

        return new StorableMessage(messageId, from, to, content);
    }
}
