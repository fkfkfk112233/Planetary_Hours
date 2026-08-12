package com.example.Planetary_hours.exception;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Planetary_hours.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 缺少必要的 Query Parameter
     */
    @ExceptionHandler(
            MissingServletRequestParameterException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingParameter(
            MissingServletRequestParameterException e) {

        return new ErrorResponse(
                400,
                e.getParameterName()
                        + " is required"
        );
    }

    /**
     * 日期格式錯誤
     */
    @ExceptionHandler(
            MethodArgumentTypeMismatchException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(
            MethodArgumentTypeMismatchException e) {

        if ("date".equals(e.getName())) {

            return new ErrorResponse(
                    400,
                    "date must be in yyyy-MM-dd format"
            );
        }
        
        if ("location".equals(e.getName())) {

            return new ErrorResponse(
                    400,
                    "invalid location"
            );
        }

        return new ErrorResponse(
                400,
                "invalid parameter: "
                        + e.getName()
        );
    }

    /**
     * 日期解析錯誤
     */
    @ExceptionHandler(
            DateTimeParseException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateFormat(
            DateTimeParseException e) {

        return new ErrorResponse(
                400,
                "date must be in yyyy-MM-dd format"
        );
    }
}
