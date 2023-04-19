package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/testes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //Outra maneira de conseguir injetar a dependência
public class TesteController {

    private final RestauranteRepository restauranteRepository;

    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> findAll(String nome, Double taxaInicial, Double taxaFinal){
        return ResponseEntity.ok(restauranteRepository.find(nome, taxaInicial, taxaFinal));
    }
}
