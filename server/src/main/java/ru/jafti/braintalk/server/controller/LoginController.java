package ru.jafti.braintalk.server.controller;


import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.UserException;
import ru.jafti.braintalk.server.service.TalkerProfileService;

import java.util.UUID;
import java.util.regex.Pattern;

import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;

@Component
public class LoginController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/login +(?<talker>\\w+)");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/login.*");

    private final RendezvousPoint rendezvousPoint;
    private final TalkerProfileService talkerProfileService;

    public LoginController(RendezvousPoint rendezvousPoint, TalkerProfileService talkerProfileService) {
        this.rendezvousPoint = rendezvousPoint;
        this.talkerProfileService = talkerProfileService;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            UUID talkerGuid = talkerProfileService.findByNickname(talker);
            if (talkerGuid == null) {
                throw new UserException("user not found, please register");
            }
            rendezvousPoint.goIn(talker, session);
            session.setLoggedIn(talker, talkerGuid);
            session.sendToOwner(SYSTEM_TALKER, "Welcome " + talker);
        }
    }
}
