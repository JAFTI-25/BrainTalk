package ru.jafti.braintalk.server.controller;

import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.service.TalkerProfileService;

import java.util.UUID;
import java.util.regex.Pattern;

public class AutoLoginController implements Controller{

    private static final Pattern PATTERN = Pattern.compile("^/auto-login\\s+(?<id>[a-fA-F0-9-]{36})");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/auto-login.*");

    private final TalkerProfileService talkerProfileService;
    private final RendezvousPoint rendezvousPoint;
    public AutoLoginController(TalkerProfileService talkerProfileService, RendezvousPoint rendezvousPoint) {
        this.talkerProfileService = talkerProfileService;
        this.rendezvousPoint = rendezvousPoint;
    }
    @Override
    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    @Override
    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String idString = matcher.group("id");
            try{
                UUID id = UUID.fromString(idString);
                String talker = talkerProfileService.findById(id);
                rendezvousPoint.goIn(talker, session);
                session.setLoggedIn(talker, id);
                session.sendToOwner("SystemBot", "Welcome " + talker);
            }
            catch(Exception e){
                session.sendToOwner("SystemBot", "Auto-login failed");
            }
        }
    }
}
