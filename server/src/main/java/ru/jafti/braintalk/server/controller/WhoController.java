package ru.jafti.braintalk.server.controller;


import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;

import java.util.List;
import java.util.regex.Pattern;

import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;


class WhoController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/who");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/who");
    private final RendezvousPoint rendezvousPoint;

    public WhoController(RendezvousPoint rendezvousPoint) {
        this.rendezvousPoint = rendezvousPoint;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            List<String> activeTalkers = rendezvousPoint.getActiveTalkers();
            if (activeTalkers.isEmpty()) {
                session.sendToOwner(SYSTEM_TALKER, "No online talkers, yet" );
            } else {
                StringBuilder sb = new StringBuilder("Active talkers:\n\r");
                for (String activeTalker : activeTalkers) {
                    sb.append(" - ").append(activeTalker);
                }
                session.sendToOwner(SYSTEM_TALKER, sb.toString());
            }
        }

    }
}
