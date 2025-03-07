package ru.jafti.braintalk.server.controller;


import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;

import java.util.regex.Pattern;


class LoginController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/login +(?<talker>\\w+)");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/login.*");

    private final RendezvousPoint rendezvousPoint;

    public LoginController(RendezvousPoint rendezvousPoint) {
        this.rendezvousPoint = rendezvousPoint;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            rendezvousPoint.goIn(talker, session);
            session.setLoggedIn(talker);
            session.sendToOwner("SystemBot", "Welcome " + talker);
        }
    }
}
