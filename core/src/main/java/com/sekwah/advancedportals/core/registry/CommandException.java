package com.sekwah.advancedportals.core.registry;

public class CommandException {
    private CommandErrorCode errorCode;
    private String message;

    public CommandException(CommandErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public CommandErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
