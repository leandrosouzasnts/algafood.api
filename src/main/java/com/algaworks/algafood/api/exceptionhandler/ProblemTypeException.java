package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemTypeException {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade Não Encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade Em Uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação Regra de Negócio"),
    CORPO_REQUISICAO_DIVERGENTE("/corpo-da-requisicao-divergente", "Corpo da Requisição Divergente");

    private String title;
    private String uri;

    ProblemTypeException(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
