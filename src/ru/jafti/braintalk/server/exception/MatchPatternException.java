package ru.jafti.braintalk.server.exception;

public class MatchPatternException extends RuntimeException{
    private final String messageWithCorrectSyntax;

    public MatchPatternException(String message) {
        this.messageWithCorrectSyntax = message;
    }
}
