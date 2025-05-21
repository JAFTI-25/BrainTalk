package ru.jafti.braintalk.talker.profile.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.Optional;
import java.util.UUID;

@Component
public class TalkerProfileServiceImpl implements TalkerProfileService {
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;
    private static final Logger log = LoggerFactory.getLogger(TalkerProfileServiceImpl.class);
    private final TalkerProfileRepository talkerProfileRepository;
    public TalkerProfileServiceImpl(TalkerProfileRepository talkerProfileRepository) {
        this.talkerProfileRepository = talkerProfileRepository;
    }

    @Override
    public String findById(UUID id) {
        log.trace("Find profile by id: {}", id);
        Optional<TalkerProfileEntity> profile = talkerProfileRepository.findById(id);
        return profile.map(TalkerProfileEntity::getNickname).orElse(null);
    }

    @Override
    public UUID findByNickname(String nickname) {
        log.trace("Find profile by nickname: {}", nickname);
        Optional<TalkerProfileEntity> profile = talkerProfileRepository.findByNickname(nickname);
        return profile.map(TalkerProfileEntity::getTalkerId).orElse(null);
    }

    @Override
    public UUID createWithNickname(String nickname) {
        log.trace("Create profile with nickname: {}", nickname);
        UUID id = UUID.randomUUID();
        TalkerProfileEntity entity = new TalkerProfileEntity();
        entity.setNickname(nickname);
        entity.setTalkerId(id);
        jdbcAggregateTemplate.insert(entity);
        return id;
    }
}
