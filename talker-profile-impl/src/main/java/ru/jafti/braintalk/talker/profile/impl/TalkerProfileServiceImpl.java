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
        TalkerProfileEntity profile = repository.findTalkerById(id);
        if (profile == null) {
            return null;
        }
        return profile.nickname();
    }

    @Override
    public UUID findByNickname(String nickname) {
        TalkerProfileEntity profile = repository.findTalkerByNickname(nickname);
        if (profile == null) {
            return null;
        }
        return profile.id();
    }

    @Override
    public UUID createWithNickname(String nickname) {
        UUID id = UUID.randomUUID();
        repository.addTalker(id, nickname);
        return id;
    }
}
