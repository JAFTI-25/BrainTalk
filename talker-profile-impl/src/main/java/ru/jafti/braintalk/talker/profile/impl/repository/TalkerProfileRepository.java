package ru.jafti.braintalk.talker.profile.impl.repository;

import org.springframework.stereotype.Repository;
import ru.jafti.braintalk.talker.profile.impl.persist.DbInitializer;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


@Repository
public interface TalkerProfileRepository extends CrudRepository<TalkerProfileEntity, UUID> {

    @Query("SELECT * FROM " + DbInitializer.TABLE_NAME + " WHERE " + DbInitializer.ID_COLUMN_NAME + " = :talkerId")
    TalkerProfileEntity findTalkerById(@Param("talkerId") UUID uuid);

    @Query("SELECT * FROM " + DbInitializer.TABLE_NAME + " WHERE " + DbInitializer.NICKNAME_COLUMN_NAME + " = :nickname")
    TalkerProfileEntity findTalkerByNickname(@Param("nickname") String nickname);

    @Query("INSERT INTO " + DbInitializer.TABLE_NAME + " VALUES (:talkerId, :nickname)")
    void addTalker(@Param("talkerId") UUID id, @Param("nickname") String nickname);
}
