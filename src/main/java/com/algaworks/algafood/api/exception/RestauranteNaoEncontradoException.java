package com.algaworks.algafood.api.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId){
        this(String.format("Restaurante de código %d não existe.", restauranteId ));
    }
}
