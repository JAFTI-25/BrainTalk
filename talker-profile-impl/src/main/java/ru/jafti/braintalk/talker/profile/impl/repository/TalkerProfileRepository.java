package ru.jafti.braintalk.talker.profile.impl.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TalkerProfileRepository extends CrudRepository<TalkerProfileEntity, UUID> {

    @Query("SELECT * FROM talker_profile WHERE nickname = :nickname")
    Optional<TalkerProfileEntity> findByNickname(@Param("nickname") String nickname);

}