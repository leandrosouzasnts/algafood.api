package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.api.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.coyote.Request;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.ref.Reference;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable  rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        ProblemTypeException problemTypeException = ProblemTypeException.CORPO_REQUISICAO_DIVERGENTE;
        String detail = "Corpo inválido na requisição. Verifique erros de sintaxe.";

        ProblemException problem = createProblemBuilder(status, problemTypeException, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream().map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));

        ProblemTypeException problemTypeException = ProblemTypeException.CORPO_REQUISICAO_DIVERGENTE;
        String detail = String.format("A propriedade %s recebeu o valor %s que é do tipo inválido. Informe um " +
                "valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());

        ProblemException problem = createProblemBuilder(status, problemTypeException, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemTypeException problemTypeException = ProblemTypeException.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();

        ProblemException problem = createProblemBuilder(status, problemTypeException, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioNaoEncontradaException(NegocioException ex, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemTypeException problemTypeException = ProblemTypeException.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        ProblemException problem = createProblemBuilder(status, problemTypeException, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(NegocioException ex, WebRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemTypeException problemTypeException = ProblemTypeException.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        ProblemException problem = createProblemBuilder(status, problemTypeException, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null){
            body = ProblemException.builder().status(status.value()).title(status.getReasonPhrase()).build();
        }else if (body instanceof String) {
            body = ProblemException.builder().status(status.value()).title(body.toString()).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    public ProblemException.ProblemExceptionBuilder createProblemBuilder(HttpStatus status, ProblemTypeException problemTypeException, String detail){

        return ProblemException.builder()
                .status(status.value())
                .title(problemTypeException.getTitle())
                .type(problemTypeException.getUri())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }
}
