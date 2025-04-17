package ru.jafti.braintalk.server.exception;

/**
 * Если пользователь делает что-то не так
 * Аналог 4XX кодов HTTP
 */
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
