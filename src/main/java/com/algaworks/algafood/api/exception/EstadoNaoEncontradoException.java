package com.algaworks.algafood.api.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public EstadoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId){
        this(String.format("Estado de código %d não existe.", estadoId));
    }
}
