package ru.jafti.braintalk.cli.server_api;

public interface SendMessageClient {
    void send(String toTalker, String message);
}
