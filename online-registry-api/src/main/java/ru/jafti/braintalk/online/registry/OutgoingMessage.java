package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public record OutgoingMessage(
        String messageId,
        From from,
        To to,
        Content content
) {

    public record From(String nickname) {
    }

    public record To(UUID talkerGuid) {
    }

    public record Content(String rawContent, Content.ContentType contentType) {

        public enum ContentType {
            TEXT
        }
    }

    public static OutgoingMessage buildFrom(
            String messageId,
            String fromTalkerNickName,
            UUID toTalkerGuid,
            String message) {
        var from = new From(fromTalkerNickName);
        var to = new OutgoingMessage.To(toTalkerGuid);
        var content = new Content(message, Content.ContentType.TEXT);

        return new OutgoingMessage(messageId, from, to, content);
    }
}
