package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@Component
public class TalkerProfileServiceImpl implements TalkerProfileService {
    private final TalkerProfileRepository repository;

    public TalkerProfileServiceImpl(TalkerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findById(UUID id) {
        return repository.findByTalkerId(id)
                .map(TalkerProfileEntity::getNickname)
                .orElse(null);
    }

    @Override
    public UUID findByNickname(String nickname) {
        return repository.findByNickname(nickname)
                .map(TalkerProfileEntity::getTalkerId)
                .orElse(null);
    }

    @Override
    public UUID createWithNickname(String nickname) {
        var uuid = UUID.randomUUID();

        TalkerProfileEntity newEntity = new TalkerProfileEntity();
        newEntity.setTalkerId(uuid);
        newEntity.setNickname(nickname);

        return repository.insert(newEntity);
    }
}
