package ru.jafti.braintalk.message.storage.api.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {

    private Long messageId;
    private UUID fromTalker;
    private UUID toTalker;
    private String content;
    private LocalDateTime createdAt;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public UUID getFromTalker() {
        return fromTalker;
    }

    public void setFromTalker(UUID fromTalker) {
        this.fromTalker = fromTalker;
    }

    public UUID getToTalker() {
        return toTalker;
    }

    public void setToTalker(UUID toTalker) {
        this.toTalker = toTalker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}