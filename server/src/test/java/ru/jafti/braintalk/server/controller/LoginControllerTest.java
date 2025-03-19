package ru.jafti.braintalk.server.controller;

import org.junit.jupiter.api.Test;
import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoginControllerTest {

    @Test
    void parseTest() {
        RendezvousPoint rendezvousPoint = mock(RendezvousPoint.class);
        Session session = mock(Session.class);
        LoginController uut = new LoginController(rendezvousPoint);

        uut.apply("/login Joe", session);
        verify(rendezvousPoint).goIn("Joe", session);
        verify(session).setLoggedIn("Joe");
    }
}