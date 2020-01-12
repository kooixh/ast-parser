package com.kooixiuhong.astparser.api.exceptions;

import lombok.Getter;

@Getter
public class ParseException extends RuntimeException {

    private ErrorCodes errorCode;

    public ParseException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
