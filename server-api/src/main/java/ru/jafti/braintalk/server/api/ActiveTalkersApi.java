package ru.jafti.braintalk.server.api;

import java.util.List;

public interface ActiveTalkersApi {

    String TALKER_ACTIVE_FETCH_ALL_PATH = "/talker/active/fetch-all";

    ActiveTalkersResponse fetchAll();

    record ActiveTalkersResponse(List<ActiveTalker> activeTalkers) {}
    record ActiveTalker(String nickname) {}
}
