package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.exception.BadRequestException;
import com.algaworks.algafood.api.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.api.exception.NegocioException;
import com.algaworks.algafood.api.exception.NotFoundException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cidadeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cidade não encontrada"));
        //return cidadeService.meetOrFail(id);
    }

    @PostMapping
    public Cidade adicionar(@RequestBody @Valid Cidade cidade){
        return cidadeService.adicionar(cidade);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade entity){
        try{
            Cidade cidade = cidadeService.meetOrFail(id);

            BeanUtils.copyProperties(entity, cidade, "id");
            return cidadeService.salvar(entity);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}