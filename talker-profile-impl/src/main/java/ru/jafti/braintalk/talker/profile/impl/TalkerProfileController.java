package ru.jafti.braintalk.talker.profile.impl;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jafti.braintalk.talker.profile.impl.repository.TalkerProfileRepository;
import ru.jafti.braintalk.talker.profile.impl.repository.model.TalkerProfileEntity;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class TalkerProfileController {
    private final TalkerProfileRepository repository;

    public TalkerProfileController(TalkerProfileRepository repository) {
        this.repository = repository;
    }

    UUID createWithNickname(String nickname) {
        TalkerProfileEntity entity = new TalkerProfileEntity();
        entity.setTalkerNickname(nickname);
        return repository.save(entity).getTalkerId();
    }

    @Modifying
    @Query("INSERT INTO talker_profile (nickname) VALUES (:nickname) RETURNING talker_id")
    UUID createWithNicknameQuery(@Param("nickname") String nickname) {
        return repository.findByNickname(nickname);
    }


}
