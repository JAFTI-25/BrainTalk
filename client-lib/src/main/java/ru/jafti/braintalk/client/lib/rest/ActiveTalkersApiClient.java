package ru.jafti.braintalk.client.lib.rest;

import com.github.mizosoft.methanol.Methanol;
import com.github.mizosoft.methanol.MutableRequest;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.api.ActiveTalkersApi;

@Component
public class ActiveTalkersApiClient implements ActiveTalkersApi {

    private final Methanol client;

    public ActiveTalkersApiClient(Methanol client) {
        this.client = client;
    }

    @Override
    public ActiveTalkersResponse fetchAll() {
        try {
            return client.send(MutableRequest.GET(TALKER_ACTIVE_FETCH_ALL_PATH), ActiveTalkersResponse.class).body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
