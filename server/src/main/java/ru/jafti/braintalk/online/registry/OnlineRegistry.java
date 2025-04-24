package ru.jafti.braintalk.online.registry;

import java.util.List;
import java.util.UUID;

public interface OnlineRegistry {
    void goIn(GoInRequest request);
    void goOut(GoOutRequest request);
    List<String> getActiveTalkers();
}
