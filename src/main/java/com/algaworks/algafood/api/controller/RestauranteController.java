package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id){
        return cadastroRestauranteService.meetOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody Restaurante restaurante){
        return cadastroRestauranteService.salvar(restaurante);
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){

        Restaurante restauranteAtual = cadastroRestauranteService.meetOrFail(id);

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
                "dataCadastro", "produto");

        try{
            return cadastroRestauranteService.salvar(restauranteAtual);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @GetMapping("/taxa-frete")
    public ResponseEntity<List<Restaurante>> buscarIntervaloTaxaFrete(Double taxaInicial, Double taxaFinal){
        return ResponseEntity.ok(cadastroRestauranteService.buscarIntervaloTaxaFrete(taxaInicial, taxaFinal));
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<List<Restaurante>> buscarPorNome(String nome){
        return ResponseEntity.ok(cadastroRestauranteService.buscarPorNome(nome));
    }

    @GetMapping("/frete-gratis")
    public ResponseEntity<List<Restaurante>> buscarFreteGratis(){
        return ResponseEntity.ok(restauranteRepository.findFreteGratis());
    }

    @GetMapping("/buscar-primeiro")
    public ResponseEntity<Restaurante> buscarPrimeiro(){
        return ResponseEntity.ok(restauranteRepository.findFirst().get());
    }
}


