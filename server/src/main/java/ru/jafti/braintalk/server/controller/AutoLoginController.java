package ru.jafti.braintalk.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.online.registry.GoInRequest;
import ru.jafti.braintalk.online.registry.OnlineRegistry;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class AutoLoginController implements Controller{

    private static final Pattern PATTERN = Pattern.compile("^/auto-login\\s+(?<id>[a-fA-F0-9-]{36})");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/auto-login.*");
    private static final Logger log = LoggerFactory.getLogger(AutoLoginController.class);

    private final TalkerProfileService talkerProfileService;
    private final OnlineRegistry onlineRegistry;

    public AutoLoginController(TalkerProfileService talkerProfileService, OnlineRegistry onlineRegistry) {
        this.talkerProfileService = talkerProfileService;
        this.onlineRegistry = onlineRegistry;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    @Override
    public void apply(String inputLine, Session session) {
        log.debug("Apply input {}", inputLine);
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String idString = matcher.group("id");
            try{
                UUID id = UUID.fromString(idString);
                String talker = talkerProfileService.findById(id);
                if (talker == null) {
                    log.debug("Talker not found");
                    session.sendToOwner("SystemBot", "Register first, please");
                    return;
                }
                onlineRegistry.goIn(new GoInRequest(id, talker, session));
                session.setLoggedIn(talker, id);
                session.sendToOwner("SystemBot", "Welcome " + talker);
            }
            catch(Exception e){
                session.sendToOwner("SystemBot", "Auto-login failed");
            }
        }
    }
}
