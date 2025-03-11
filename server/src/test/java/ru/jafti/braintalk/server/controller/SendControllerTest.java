package ru.jafti.braintalk.server.controller;

import org.junit.jupiter.api.Test;
import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Channel;
import ru.jafti.braintalk.server.connection.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SendControllerTest {

    @Test
    void testParse() {
        RendezvousPoint rendezvousPoint = mock(RendezvousPoint.class);
        Session session = mock(Session.class);
        Channel joesChannel = mock(Channel.class);

        SendController uut = new SendController(rendezvousPoint);
        when(rendezvousPoint.getOutput("Joe")).thenReturn(joesChannel);
        uut.apply("/send Joe Hello my friend!", session);
    }
}