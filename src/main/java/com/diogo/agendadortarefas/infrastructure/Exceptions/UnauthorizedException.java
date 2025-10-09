package com.diogo.agendadortarefas.infrastructure.Exceptions;


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {

        super(message);
    }
    public UnauthorizedException(String message, Throwable throwable) {

        super(message, throwable);
    }

}
