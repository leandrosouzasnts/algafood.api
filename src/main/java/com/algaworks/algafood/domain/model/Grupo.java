package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
        joinColumns = @JoinColumn(name = "grupo_id"),inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private List<Permissao> permissao = new ArrayList<>();

}
