package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileStorage;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@Component
public class TalkerProfileStorageImpl implements TalkerProfileStorage {

    private final TalkerProfileRepository talkerProfileRepository;

    public TalkerProfileStorageImpl(TalkerProfileRepository talkerProfileRepository) {
        this.talkerProfileRepository = talkerProfileRepository;
    }

    @Override
    public String findById(UUID talkerId) {
        TalkerProfileEntity entity = talkerProfileRepository.findByTalkerId(talkerId);
        if (entity == null) {
            return null;
        }

        return entity.getNickname();
    }

    @Override
    public UUID findByNickname(String nickname) {
        TalkerProfileEntity entity = talkerProfileRepository.findByNickname(nickname);
        if (entity == null) {
            return null;
        }

        return entity.getTalkerId();
    }

    @Override
    public UUID createWithNickname(String nickname) {
        UUID newTalkerId = UUID.randomUUID();
        talkerProfileRepository.insertTalkerProfileEntity(newTalkerId, nickname);
        return newTalkerId;
    }
}
