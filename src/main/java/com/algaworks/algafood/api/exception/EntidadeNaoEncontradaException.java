package com.algaworks.algafood.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException{

    public EntidadeNaoEncontradaException (String mensagem){
        super(mensagem);
    }
}
