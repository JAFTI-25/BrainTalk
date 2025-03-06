package ru.jafti.braintalk.server.controller;

import ru.jafti.braintalk.server.connection.Session;

public interface Controller {
    boolean isApplicable(String inputLine);
    void apply(String inputLine, Session session);
}
