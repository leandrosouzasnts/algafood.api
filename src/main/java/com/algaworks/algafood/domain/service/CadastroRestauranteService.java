package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.api.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    public Restaurante meetOrFail(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    public Restaurante salvar(Restaurante model){

        Cozinha cozinha = cozinhaService.meetOrFail(model.getCozinha().getId());

        Restaurante restaurante = restauranteRepository.save(model);

        restaurante.setCozinha(cozinha);

        return restaurante;     
    }

    public Restaurante atualizar(Long id, Restaurante model){

        Restaurante restaurante = meetOrFail(id);

        BeanUtils.copyProperties(model, restaurante, "id", "formasPagamento", "endereco",
                "dataCadastro", "produto");

        Restaurante restauranteAtualizado = salvar(restaurante);

        return restauranteAtualizado;
    }

    public List<Restaurante> buscarIntervaloTaxaFrete(Double taxaInicial, Double taxaFinal){
        return restauranteRepository.findBytaxaFreteBetween(taxaInicial, taxaFinal);
    }

    public List<Restaurante> buscarPorNome(String nome){
        return restauranteRepository.buscarPorNome(nome);
    }
}
