package ru.jafti.braintalk.server.controller.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.jafti.braintalk.server.api.RegisterApi;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileStorage;

import java.util.UUID;

@RestController
public class RegisterController implements RegisterApi {

    private final TalkerProfileStorage talkerProfileStorage;

    public RegisterController(TalkerProfileStorage talkerProfileStorage) {
        this.talkerProfileStorage = talkerProfileStorage;
    }

    @PostMapping("/talker/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        String nickname = request.talkerNickname();
        UUID existingUUID = talkerProfileStorage.findByNickname(nickname);

        if (existingUUID != null) {
            return new RegisterResponse(existingUUID);
        }

        UUID newUUID = talkerProfileStorage.createWithNickname(nickname);
        return new RegisterResponse(newUUID);
    }
}

