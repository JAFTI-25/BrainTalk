package ru.jafti.braintalk.talker.profile.impl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TalkerProfileRepository extends CrudRepository<TalkerProfileEntity, UUID> {
    Optional<TalkerProfileEntity> findByNickname(String nickname);
}

