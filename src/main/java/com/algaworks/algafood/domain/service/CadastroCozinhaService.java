package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.api.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.api.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    private final String MSG_ENTIDADE_EM_USO = "Cozinha de código %d não pode ser removida pois está em uso.";
    @Autowired
    CozinhaRepository cozinhaRepository;

    public Cozinha meetOrFail(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void remover(Long id){
        try{
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex){
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException((String.format(MSG_ENTIDADE_EM_USO, id)));
        }
    }
}
