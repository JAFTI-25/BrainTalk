package ru.jafti.braintalk.talker.profile.impl.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@Repository
public interface TalkerProfileRepository extends CrudRepository<TalkerProfileEntity, UUID> {

    TalkerProfileEntity findByTalkerId(UUID talkerId);

    TalkerProfileEntity findByNickname(String nickname);

    @Modifying
    @Query("INSERT INTO talker_profile VALUES (:talkerId, :nickname)")
    void insertTalkerProfileEntity(@Param("talkerId") UUID talkerId, @Param("nickname") String nickname);

}
