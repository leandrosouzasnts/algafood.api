package com.algaworks.algafood.api.handler;

import com.algaworks.algafood.api.exception.NotFoundException;
import com.algaworks.algafood.api.exception.NotFoundExceptionDetails;
import com.algaworks.algafood.api.exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundExceptionDetails> handlerNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(NotFoundExceptionDetails.builder()
                .title("Not Found Exception")
                .message(notFoundException.getMessage())
                .error("Not Found")
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .developerMessage(notFoundException.getClass().getName())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerNotFoundException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidationExceptionDetails.builder()
                .title("Bad Request")
                .message(ex.getMessage())
                .error("Fields invalid")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .developerMessage(ex.getClass().getName())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }

}
