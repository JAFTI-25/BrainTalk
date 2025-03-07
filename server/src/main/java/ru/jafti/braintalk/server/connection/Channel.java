package ru.jafti.braintalk.server.connection;

public interface Channel {
    void sendToOwner(String fromTalker, String message);
}
