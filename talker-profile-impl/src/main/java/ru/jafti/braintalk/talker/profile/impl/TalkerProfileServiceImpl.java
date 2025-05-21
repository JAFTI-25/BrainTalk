package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class TalkerProfileServiceImpl implements TalkerProfileService {

    private final TalkerProfileRepository repository;

    public TalkerProfileServiceImpl(TalkerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findById(UUID id) {
        Optional<TalkerProfileEntity> entity = repository.findById(id);
        return entity.map(TalkerProfileEntity::getNickname).orElse(null);
    }

    @Override
    public UUID findByNickname(String nickname) {
        Optional<TalkerProfileEntity> entity = repository.findByNickname(nickname);
        return entity.map(TalkerProfileEntity::getTalkerId).orElse(null);
    }

    @Override
    public UUID createWithNickname(String nickname) {
        var entity = new TalkerProfileEntity();
        var uuid = UUID.randomUUID();
        entity.setTalkerId(uuid);
        entity.setNickname(nickname);

        repository.save(entity);
        return uuid;
    }
}

