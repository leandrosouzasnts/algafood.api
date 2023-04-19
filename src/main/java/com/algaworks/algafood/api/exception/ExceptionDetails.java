package com.algaworks.algafood.api.exception;


import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected String title;
    protected String message;
    protected String error;
    protected HttpStatus status;
    protected LocalDateTime timestamp;
    protected String developerMessage;
}
