package ru.jafti.braintalk.online.registry;

public interface Channel {
    void sendToOwner(String fromTalker, String message);
}
