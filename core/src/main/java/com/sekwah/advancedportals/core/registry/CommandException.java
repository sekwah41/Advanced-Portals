package com.sekwah.advancedportals.core.registry;

public class CommandException {
    private final CommandErrorCode errorCode;
    private final String message;

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
