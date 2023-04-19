package com.algaworks.algafood.api.exception;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class NotFoundExceptionDetails extends ExceptionDetails{

}
