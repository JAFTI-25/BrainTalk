package ru.jafti.braintalk.server.controller;

import ru.jafti.braintalk.server.TalkerProfileService;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.MatchPatternException;

import java.util.UUID;
import java.util.regex.Pattern;

import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;

public class RegisterController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/register (\\S+)");
    private final TalkerProfileService talkerProfileService;

    public RegisterController(TalkerProfileService talkerProfileService) {
        this.talkerProfileService = talkerProfileService;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return PATTERN.matcher(inputLine).find();
    }

    @Override
    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String nickname = matcher.group(1);

            UUID existingUUID = talkerProfileService.findByNickname(nickname);
            if (existingUUID != null) {
                session.sendToOwner(SYSTEM_TALKER, "Nickname already registered. Your UUID: " + existingUUID);
            } else {
                UUID newUUID = talkerProfileService.createWithNickname(nickname);
                session.sendToOwner(SYSTEM_TALKER, "Registration successful. Your UUID: " + newUUID);
            }
        }
        else {
            throw new MatchPatternException("/register <nickname>");
        }
    }
}

