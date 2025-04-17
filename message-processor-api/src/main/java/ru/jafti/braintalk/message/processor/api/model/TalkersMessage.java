package ru.jafti.braintalk.message.processor.api.model;

import java.util.UUID;

public class TalkersMessage {

    private From from;
    private To to;
    private Content content;

    public TalkersMessage(From from, To to, Content content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public String toString() {
        return "TalkersMessage{" +
                "from=" + from +
                ", to=" + to +
                ", content=" + content +
                '}';
    }

    public static class From {
        private String nickname;
        private UUID talkerGuid;

        public From(String nickname, UUID talkerGuid) {
            this.nickname = nickname;
            this.talkerGuid = talkerGuid;
        }

        @Override
        public String toString() {
            return "From{" +
                    "nickname='" + nickname + '\'' +
                    ", talkerGuid=" + talkerGuid +
                    '}';
        }
    }

    public static class To {
        private String nickname;

        public To(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "To{" +
                    "nickname='" + nickname + '\'' +
                    '}';
        }
    }


    public static class Content {
        private String rawContent;
        private ContentType contentType;

        public Content(String rawContent, ContentType contentType) {
            this.rawContent = rawContent;
            this.contentType = contentType;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "rawContent='" + rawContent + '\'' +
                    ", contentType=" + contentType +
                    '}';
        }

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
