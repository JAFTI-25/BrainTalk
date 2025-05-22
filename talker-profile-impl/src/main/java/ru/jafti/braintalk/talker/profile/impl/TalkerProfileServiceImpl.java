package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.talker.profile.impl.persist.DbConnection;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@Component
public class TalkerProfileServiceImpl implements TalkerProfileService {
    private TalkerProfileRepository repository;

    public void setRepository(TalkerProfileRepository repository){ this.repository=repository;}

    public TalkerProfileServiceImpl(DbConnection dbConnection) {
        this.repository = repository;
    }

    @Override
    public String findById(UUID id){
        return repository.findById(id);
    }

    @Override
    public UUID findByNickname(String nickname){
        return repository.findByNickname(nickname);
    }

    @Override
    public UUID createWithNickname(String nickname) {
        TalkerProfileEntity entity = new TalkerProfileEntity();
        entity.setTalkerNickname(nickname);
        repository.save(entity);
        return entity.getTalkerId();
    }
}
