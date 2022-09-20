package com.malbano.rural.controller;

import com.malbano.rural.controller.exceptions.StandardError;
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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
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
        return ResponseEntity.ok().body(service.onboarding(dadosList));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DadosDTO>> getSearch(@Param(value = "nomeProduto") String nomeProduto, @Param(value = "nomeRegiao") String nomeRegiao, @Param(value = "nomeUF") String nomeUF, @Param(value = "cdPrograma") String cdPrograma, @Param(value = "cdSubPrograma") String cdSubPrograma,
                                    @Param(value = "cdFonteRecurso") String cdFonteRecurso, @Param(value = "cdTipoSeguro") String cdTipoSeguro, @Param(value = "cdModalidade") String cdModalidade, @Param(value = "mesEmissao") String mesEmissao, @Param(value = "anoEmissao") String anoEmissao, @Param(value = "qtdCusteio") Integer qtdCusteio,
                                    @Param(value = "vlCusteio") BigDecimal vlCusteio, @Param(value = "atividade") String atividade, @Param(value = "areaCusteio") Double areaCusteio){
        List<DadosDTO> list = service.getDadosFilter(nomeProduto, nomeRegiao, nomeUF, cdPrograma, cdSubPrograma, cdFonteRecurso, cdTipoSeguro, cdModalidade, mesEmissao, anoEmissao, qtdCusteio, vlCusteio, atividade, areaCusteio);
        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<DadosDTO>> getPageable(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        List<DadosDTO> dados = service.getPageable(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
        return dados.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(dados);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDTO> get(@PathVariable("id") Long id){
        DadosDTO dados = service.getDadosByID(id);
        return ResponseEntity.ok(dados);
    }
    @GetMapping("/custeioAno")
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
    public ResponseEntity<DadosDTO> put(@PathVariable("id") Long id, @RequestBody DadosDTO dados){
        DadosDTO c = service.update(dados, id);

        return ResponseEntity.ok(c);
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
