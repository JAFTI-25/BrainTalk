package ru.jafti.braintalk.talker.profile.impl.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.Optional;
import java.util.UUID;

public interface TalkerProfileRepository extends CrudRepository<TalkerProfileEntity, UUID> {

    @Query("INSERT INTO talker_profile (talker_id, nickname) " +
            "VALUES (:#{#entity.talkerId}, :#{#entity.nickname}) " +
            "RETURNING talker_id")
    UUID insert(@Param("entity") TalkerProfileEntity entity);

    Optional<TalkerProfileEntity> findByNickname(String nickname);

    Optional<TalkerProfileEntity> findByTalkerId(UUID talkerId);
}
