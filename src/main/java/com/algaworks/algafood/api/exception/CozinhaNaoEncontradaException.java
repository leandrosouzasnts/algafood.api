package com.algaworks.algafood.api.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Cozinha de código %d não existe.", cozinhaId));
    }
}
