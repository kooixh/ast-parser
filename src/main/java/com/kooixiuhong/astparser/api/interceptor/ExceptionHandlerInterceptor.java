package com.kooixiuhong.astparser.api.interceptor;

import com.kooixiuhong.astparser.api.exceptions.ErrorResponse;
import com.kooixiuhong.astparser.api.exceptions.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerInterceptor {

    @ExceptionHandler(ParseException.class)
    @ResponseBody
    public ErrorResponse handleParserException(HttpServletRequest request, HttpServletResponse response,
                                               ParseException exception) {

        String errorUrl = request.getRequestURI();
        String errorMessage = exception.getErrorCode().getErrorMessage();
        int errorCode = exception.getErrorCode().getErrorCode();

        response.setStatus(exception.getErrorCode().getHttpStatus());
        return new ErrorResponse(errorCode, errorMessage, errorUrl);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleUncheckedException(HttpServletRequest request, Exception exception) {

        String errorUrl = request.getRequestURI();
        String errorMessage = exception.getMessage();
        int errorCode = 9999;

        return new ErrorResponse(errorCode, errorMessage, errorUrl);
    }
}
