package com.sekwah.advancedportals.core.registry;

public class CommandException {
    private ErrorCode errorCode;
    private String message;

    public  CommandException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
