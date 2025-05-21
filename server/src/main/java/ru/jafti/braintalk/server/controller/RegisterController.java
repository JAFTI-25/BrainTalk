package ru.jafti.braintalk.server.controller;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.MatchPatternException;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileStorage;

import java.util.UUID;
import java.util.regex.Pattern;

import static ru.jafti.braintalk.common.CommonConstants.SYSTEM_TALKER;

@Deprecated
@Component("RegisterControllerSocketImpl")
public class RegisterController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/register (\\S+)");
    private final TalkerProfileStorage talkerProfileStorage;

    public RegisterController(TalkerProfileStorage talkerProfileStorage) {
        this.talkerProfileStorage = talkerProfileStorage;
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

            UUID existingUUID = talkerProfileStorage.findByNickname(nickname);
            if (existingUUID != null) {
                session.sendToOwner(SYSTEM_TALKER, "Nickname already registered. Your UUID: " + existingUUID);
            } else {
                UUID newUUID = talkerProfileStorage.createWithNickname(nickname);
                session.sendToOwner(SYSTEM_TALKER, "Registration successful. Your UUID: " + newUUID);
            }
        }
        else {
            throw new MatchPatternException("/register <nickname>");
        }
    }
}

