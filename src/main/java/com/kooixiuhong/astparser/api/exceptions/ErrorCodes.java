package com.kooixiuhong.astparser.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCodes {

    SYNTAX_ERROR(1001, "Syntax error in expression", HttpStatus.BAD_REQUEST.value()),
    DUPLICATED_OPERATOR(1002, "Same operator symbol defined multiple times", HttpStatus.BAD_REQUEST.value()),
    INVALID_OPERATOR_SYMBOL(1003, "Operator contains reserved symbol", HttpStatus.BAD_REQUEST.value()),
    INVALID_OPERATOR_TYPE(1004, "Unknown operator type, only binary or unary", HttpStatus.BAD_REQUEST.value()),
    PARSE_ERROR(1005, "Error in the parsing engine", HttpStatus.INTERNAL_SERVER_ERROR.value());

    private int errorCode;
    private String errorMessage;
    private int httpStatus;

}
