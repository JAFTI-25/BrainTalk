package ru.jafti.braintalk.server.controller.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.jafti.braintalk.server.api.RegisterApi;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;

import java.util.UUID;

@RestController
public class RegisterController implements RegisterApi {

    private final TalkerProfileService talkerProfileService;

    public RegisterController(TalkerProfileService talkerProfileService) {
        this.talkerProfileService = talkerProfileService;
    }

    @PostMapping("/talker/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        String nickname = request.talkerNickname();
        UUID existingUUID = talkerProfileService.findByNickname(nickname);

        if (existingUUID != null) {
            return new RegisterResponse(existingUUID);
        }

        UUID newUUID = talkerProfileService.createWithNickname(nickname);
        return new RegisterResponse(newUUID);
    }
}

