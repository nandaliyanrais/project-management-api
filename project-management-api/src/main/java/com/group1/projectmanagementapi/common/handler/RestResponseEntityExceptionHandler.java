package com.group1.projectmanagementapi.common.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageBadRequest> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errorField = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField)
                .collect(Collectors.toList());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        ErrorMessageBadRequest message = new ErrorMessageBadRequest(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                getErrorsMap(errors, errorField),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessageBadRequest>(message, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> getErrorsMap(List<String> errors, List<String> errorFields) {
        Map<String, String> errorResponse = new HashMap<>();
        int index = 0;
        for (String field : errorFields) {
            errorResponse.put(field, errors.get(index));
            index++;
        }
        System.out.println(errors);
        return errorResponse;
    }

}
