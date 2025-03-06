package ru.jafti.braintalk.server.controller;


import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Channel;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.MatchPatternException;

import java.util.regex.Pattern;


public class SendController implements Controller {

    private static final Pattern PATTERN = Pattern.compile("^/send +(?<talker>\\w+) +(?<message>.*)");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/send.*");
    private final RendezvousPoint rendezvousPoint;

    public SendController(RendezvousPoint rendezvousPoint) {
        this.rendezvousPoint = rendezvousPoint;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            String message = matcher.group("message");
            Channel channel = rendezvousPoint.getOutput(talker);
            if (channel != null) {
                channel.sendToOwner(session.getTalkerOwner(), message);
            } else {
                session.sendToOwner("SystemBot",
                        "Sorry, but talker " + talker + " is unavailable");
            }
        } else{
            throw new MatchPatternException("/send <talker> <message>");
        }

    }
}
