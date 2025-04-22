package ru.jafti.braintalk.server.controller;


import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.connection.Session;

import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;

@Component
public class DefaultController implements Controller {

    public boolean isApplicable(String inputLine) {
        return true;
    }

    public void apply(String inputLine, Session session) {
        session.sendToOwner(SYSTEM_TALKER, "Bad command");
    }
}
