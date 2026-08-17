package com.example.Planetary_hours.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Planetary_hours.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // ========================================
    // 日期格式錯誤
    // ========================================

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateTimeParseException(
            DateTimeParseException exception) {

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request"
        );
    }


    // ========================================
    // Request Parameter 型別錯誤
    // ========================================

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException exception) {

        String message =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getDefaultMessage()
                        )
                        .findFirst()
                        .orElse("資料格式錯誤");

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request"

        );
    }


    // ========================================
    // 缺少 Request Parameter
    // ========================================

    @ExceptionHandler(
            MissingServletRequestParameterException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingParameter(
            MissingServletRequestParameterException exception) {

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request"
        );
    }


    // ========================================
    // 其他未預期錯誤
    // ========================================

    @ExceptionHandler(Exception.class)
    @ResponseStatus(
            HttpStatus.INTERNAL_SERVER_ERROR
    )
    public ErrorResponse handleException(
            Exception exception) {

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error"
        );
    }
}