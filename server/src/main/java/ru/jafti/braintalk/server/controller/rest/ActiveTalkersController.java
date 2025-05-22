package ru.jafti.braintalk.server.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jafti.braintalk.online.registry.ActiveTalkersHolder;
import ru.jafti.braintalk.server.api.ActiveTalkersApi;

import java.util.List;

@RestController
public class ActiveTalkersController implements ActiveTalkersApi {

    private final ActiveTalkersHolder activeTalkersHolder;

    public ActiveTalkersController(ActiveTalkersHolder activeTalkersHolder) {
        this.activeTalkersHolder = activeTalkersHolder;
    }

    @Override
    @GetMapping(TALKER_ACTIVE_FETCH_ALL_PATH)
    public ActiveTalkersResponse fetchAll() {
        List<String> activeTalkers = activeTalkersHolder.getActiveTalkers();

        return new ActiveTalkersResponse(activeTalkers
                .stream()
                .map(ActiveTalker::new)
                .toList());
    }
}
