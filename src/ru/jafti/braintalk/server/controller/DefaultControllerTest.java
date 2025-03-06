package ru.jafti.braintalk.server.controller;

import org.junit.jupiter.api.Test;
import ru.jafti.braintalk.server.connection.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;


class DefaultControllerTest {

    DefaultController uut = new DefaultController();

    @Test
    void isApplicable() {
        boolean result = uut.isApplicable("/who");
        assertTrue(result);
        assertEquals(result, true);
    }

    @Test
    void apply() {
        Session session = mock(Session.class);
        uut.apply("qwer", session);
        verify(session).sendToOwner(SYSTEM_TALKER, "Bad command");
    }
}