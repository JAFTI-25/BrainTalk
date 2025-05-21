package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileStorage;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;

import java.util.UUID;

@Component
public class TalkerProfileStorageImpl implements TalkerProfileStorage {

    private final TalkerProfileRepository talkerProfileRepository;

    @Autowired
    public TalkerProfileStorageImpl(TalkerProfileRepository talkerProfileRepository) {
        this.talkerProfileRepository = talkerProfileRepository;
    }

    @Override
    public String findById(UUID talkerId) {
        return talkerProfileRepository.findByTalkerId(talkerId).getNickname();
    }

    @Override
    public UUID findByNickname(String nickname) {
        return talkerProfileRepository.findByNickname(nickname).getTalkerId();
    }

    @Override
    public UUID createWithNickname(String nickname) {
        UUID newTalkerId = UUID.randomUUID();
        talkerProfileRepository.insertTalkerProfileEntity(newTalkerId, nickname);
        return newTalkerId;
    }
}
