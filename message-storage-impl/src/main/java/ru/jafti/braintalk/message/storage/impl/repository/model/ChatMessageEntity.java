package ru.jafti.braintalk.message.storage.impl.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.jafti.braintalk.message.storage.impl.persist.DbInitializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(DbInitializer.TABLE_NAME)
public class ChatMessageEntity {

    @Id
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