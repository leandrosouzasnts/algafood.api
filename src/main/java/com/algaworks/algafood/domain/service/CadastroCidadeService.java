package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    private final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida pois está em uso.";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    CadastroEstadoService estadoService;

    public Cidade adicionar(Cidade cidade){

        Estado estado = estadoService.meetOrFail(cidade.getEstado().getId());

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public Cidade meetOrFail(long id){
        return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public Cidade salvar(Cidade entity) {

        Estado estado = estadoService.meetOrFail(entity.getEstado().getId());

        Cidade cidade = cidadeRepository.save(entity);

        return cidade;
    }

}
