package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.api.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    private final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida pois está em uso.";
    @Autowired
    EstadoRepository estadoRepository;

    public Estado meetOrFail(Long id){
        return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }
}
