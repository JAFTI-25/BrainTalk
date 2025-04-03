package ru.jafti.braintalk.server.controller;

import org.junit.jupiter.api.Test;
import ru.jafti.braintalk.server.TalkerProfileService;
import ru.jafti.braintalk.server.connection.Session;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.jafti.braintalk.server.Constants.SYSTEM_TALKER;

class RegisterControllerTest {
    private final TalkerProfileService talkerProfileService = mock(TalkerProfileService.class);
    private final RegisterController registerController = new RegisterController(talkerProfileService);
    private final Session session = mock(Session.class);

    @Test
    void isApplicable_ValidInput_ShouldReturnTrue() {
        assertTrue(registerController.isApplicable("/register JohnDoe"));
    }

    @Test
    void isApplicable_InvalidInputWithoutNickname_ShouldReturnFalse() {
        assertFalse(registerController.isApplicable("/register"));
    }

    @Test
    void isApplicable_InvalidInputWithoutCommand_ShouldReturnFalse() {
        assertFalse(registerController.isApplicable("/register"));
    }

    @Test
    void apply_NicknameIsAvailable_ShouldSendSuccessMessage() {
        String nickname = "JohnDoe";
        UUID newUUID = UUID.randomUUID();

        when(talkerProfileService.findByNickname(nickname)).thenReturn(null);
        when(talkerProfileService.createWithNickname(nickname)).thenReturn(newUUID);

        registerController.apply("/register " + nickname, session);

        verify(talkerProfileService).findByNickname(nickname);
        verify(talkerProfileService).createWithNickname(nickname);
        verify(session).sendToOwner(SYSTEM_TALKER, "Registration successful. Your UUID: " + newUUID);
    }

    @Test
    void apply_NicknameIsTaken_ShouldSendErrorMessage() {
        String nickname = "JohnDoe";
        UUID existingUUID = UUID.randomUUID();

        when(talkerProfileService.findByNickname(nickname)).thenReturn(existingUUID);

        registerController.apply("/register " + nickname, session);

        verify(talkerProfileService).findByNickname(nickname);
        verify(talkerProfileService, never()).createWithNickname(anyString());
        verify(session).sendToOwner(SYSTEM_TALKER, "Nickname already registered. Your UUID: " + existingUUID);
    }
}


