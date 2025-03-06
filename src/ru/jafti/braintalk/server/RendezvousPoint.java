package ru.jafti.braintalk.server;


import ru.jafti.braintalk.server.connection.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RendezvousPoint {

    public static final RendezvousPoint INSTANCE = new RendezvousPoint();

    private final List<String> activeTalkers = new ArrayList<>();
    private final Map<String, Channel> outputStreams = new HashMap<>();

    public RendezvousPoint() {
    }

    public void goIn(String talker, Channel out) {
        activeTalkers.add(talker);
        outputStreams.put(talker, out);
    }

    public void goOut(String talker) {
        activeTalkers.remove(talker);
        outputStreams.remove(talker);
    }

    public Channel getOutput(String talker) {
        return outputStreams.get(talker);
    }

    public List<String> getActiveTalkers() {
        return activeTalkers;
    }
}
