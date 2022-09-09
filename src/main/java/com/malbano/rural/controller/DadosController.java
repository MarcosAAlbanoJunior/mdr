package com.malbano.rural.controller;

import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.service.DadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dados")
public class DadosController {

    @Autowired
    private DadosService service;

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

    @GetMapping("/pageable")
    public ResponseEntity getPageable(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        List<DadosDTO> dados = service.getPageable(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        DadosDTO carro = service.getDadosByID(id);

        return ResponseEntity.ok(carro);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam("anoEmissao") String anoEmissao) {
        List<AcumuloDTO> dados = service.total(anoEmissao);
        return dados.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(dados);
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

    @DeleteMapping ("/deleteAll")
    public ResponseEntity deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

}
