package ru.jafti.braintalk.message.processor.api.model;

import java.util.UUID;

public record TalkersMessage(
        From from,
        To to,
        Content content
) {

    public record From(String nickname, UUID talkerGuid) {
    }

    public record To(String nickname) {
    }

    public record Content(String rawContent, ContentType contentType) {

        public enum ContentType {
            TEXT
        }
    }

    public static TalkersMessage buildFrom(String fromTalkerNickName,
                                           UUID fromTalkerGuid,
                                           String toTalkerNickname,
                                           String message) {
        var from = new From(fromTalkerNickName, fromTalkerGuid);
        var to = new To(toTalkerNickname);
        var content = new Content(message, Content.ContentType.TEXT);

        return new TalkersMessage(from, to, content);
    }
}