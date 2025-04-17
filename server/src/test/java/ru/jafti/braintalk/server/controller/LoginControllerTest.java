package ru.jafti.braintalk.server.controller;

import org.junit.jupiter.api.Test;
//import ru.jafti.braintalk.server.RendezvousPoint;
//import ru.jafti.braintalk.server.connection.Session;
//import ru.jafti.braintalk.server.service.TalkerProfileService;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Test
    void parseTest() {
        UUID uuid = UUID.fromString("4f9c7a67-878f-4175-9a82-fda35bfefa14");

//        RendezvousPoint rendezvousPoint = mock(RendezvousPoint.class);
//        TalkerProfileService talkerProfileService = mock(TalkerProfileService.class);
//        when(talkerProfileService.findByNickname("Joe")).thenReturn(uuid);
//        Session session = mock(Session.class);
//        LoginController uut = new LoginController(rendezvousPoint, talkerProfileService);
//
//        uut.apply("/login Joe", session);
//        verify(rendezvousPoint).goIn("Joe", session);
//        verify(session).setLoggedIn("Joe", uuid);
    }
}