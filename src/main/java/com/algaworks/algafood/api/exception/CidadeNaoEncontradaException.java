package com.algaworks.algafood.api.exception;


public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long cidadeId){
        this(String.format("Cidade de código %d não existe.", cidadeId));
    }
}
