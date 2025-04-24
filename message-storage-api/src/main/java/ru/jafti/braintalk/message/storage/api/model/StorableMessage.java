package ru.jafti.braintalk.message.storage.api.model;

import java.util.UUID;

public record StorableMessage(
        From from,
        To to,
        Content content
) {

    public record From(String nickname, UUID talkerGuid) {
    }

    public record To(String nickname) {
    }

    public record Content(String rawContent, Content.ContentType contentType) {

        public enum ContentType {
            TEXT
        }
    }

    public static StorableMessage buildFrom(String fromTalkerNickName,
            UUID fromTalkerGuid,
            String toTalkerNickname,
            String message) {
        var from = new From(fromTalkerNickName, fromTalkerGuid);
        var to = new StorableMessage.To(toTalkerNickname);
        var content = new Content(message, Content.ContentType.TEXT);

        return new StorableMessage(from, to, content);
    }
}
