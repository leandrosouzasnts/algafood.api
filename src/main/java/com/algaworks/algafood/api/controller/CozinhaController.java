package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cozinha getById(@PathVariable Long id){
        return cadastroCozinhaService.meetOrFail(id);

//        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//
//        if (cozinha.isPresent()) { //Nunca será null -> por isso verificamos se existe algo.
//            return ResponseEntity.ok(cozinha.get()); //usamos o get para obter o objeto presente.
//        }
//
//        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){

        Cozinha cozinhaOld = cadastroCozinhaService.meetOrFail(id);

        BeanUtils.copyProperties(cozinha, cozinhaOld, "id");

        return cadastroCozinhaService.salvar(cozinhaOld);

//        if (cozinhaOld.isPresent()){
//
//            BeanUtils.copyProperties(cozinha, cozinhaOld.get(), "id");
//
//            Cozinha cozinhaAtualizada = cadastroCozinhaService.salvar(cozinhaOld.get());
//
//            return ResponseEntity.ok(cozinhaAtualizada);
//        }
//        else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCozinhaService.remover(id);
    }
}

