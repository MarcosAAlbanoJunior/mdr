package com.malbano.rural.controller;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.model.repository.DadosRepository;
import com.malbano.rural.service.DadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class DadosController {

    @Autowired
    private DadosService service;

    @Autowired
    private DadosRepository dadosDAO;
    @Autowired
    ConectaAPI conectaAPI;

    @PostMapping(path = "/insert")
    public ResponseEntity<Iterable<DadosEntity>> insert() {
        DadosList dadosList = conectaAPI.getDados();
        return ResponseEntity.ok().body(service.insert(dadosList));
    }

    @GetMapping()
    public ResponseEntity get(){
        return ResponseEntity.ok(service.getDados());
    }

    @GetMapping("/dados")
    public ResponseEntity<?> getPageable(Pageable pageable){
        return new ResponseEntity<>(dadosDAO.findAll(pageable), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        DadosDTO carro = service.getDadosByID(id);

        return ResponseEntity.ok(carro);

    }

    @PostMapping
    public ResponseEntity post(@RequestBody DadosEntity dados){
        DadosDTO d = service.insert(dados);

        URI location = getUri(d.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody DadosEntity dados){
        DadosDTO c = service.update(dados, id);

        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);

        return ResponseEntity.ok().build();
    }

}
