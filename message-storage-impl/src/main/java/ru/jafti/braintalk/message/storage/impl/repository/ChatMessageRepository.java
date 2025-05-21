package ru.jafti.braintalk.message.storage.impl.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jafti.braintalk.message.storage.impl.repository.model.ChatMessageEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessageEntity, Long> {

    @Query("SELECT * FROM chat_history WHERE from_talker = :fromTalker ORDER BY message_id")
    List<ChatMessageEntity> findByFromTalker(@Param("fromTalker") UUID fromTalker);

    @Query("SELECT * FROM chat_history WHERE to_talker = :toTalker ORDER BY message_id")
    List<ChatMessageEntity> findByToTalker(@Param("toTalker") UUID toTalker);

    @Query("""
            SELECT * FROM chat_history 
            WHERE LEAST(from_talker, to_talker) = LEAST(:talker1, :talker2) 
              AND GREATEST(from_talker, to_talker) = GREATEST(:talker1, :talker2) 
            ORDER BY message_id
            """)
    List<ChatMessageEntity> findConversationBetween(@Param("talker1") UUID talker1, @Param("talker2") UUID talker2);

    @Modifying
    @Query("INSERT INTO chat_history VALUES (:messageId, :fromTalkerId, :toTalkerId, :content)")
    void insertChatMessageEntity(
            @Param("messageId") long messageId,
            @Param("fromTalkerId") UUID fromTalkerId,
            @Param("toTalkerId") UUID toTalkerId,
            @Param("content") String content
            );
}