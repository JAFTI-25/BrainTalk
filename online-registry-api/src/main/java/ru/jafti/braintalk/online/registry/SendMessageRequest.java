package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public record SendMessageRequest(
        String toTalker,
        String fromTalker,
        UUID toTalkerGuid,
        String messageId,
        Content content
) {
    public record Content(String rawContent, ContentType contentType) {
        public enum ContentType {
            TEXT
        }
    }
}
