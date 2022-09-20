package com.malbano.rural.controller;

import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.service.DadosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/dados")
public class DadosController {

    @Autowired
    private DadosService service;

    @Autowired
    ConectaAPI conectaAPI;

    @Tag(name = "Onboarding")
    @Operation(summary = "Carrega o banco de dados com os primeiros 1000 dados da Matriz de Dados do Crédito Rural")
    @PostMapping(path = "/onboard")
    public ResponseEntity<Iterable<DadosEntity>> insert() {
        DadosList dadosList = conectaAPI.getDados();
        return ResponseEntity.ok().body(service.onboarding(dadosList));
    }

    @Tag(name = "Busca")
    @Operation(summary = "Retorna contratos de custeio com os filtros informados")
    @GetMapping("/filter")
    public ResponseEntity<List<DadosDTO>> getSearch(@Param(value = "nomeProduto") String nomeProduto, @Param(value = "nomeRegiao") String nomeRegiao, @Param(value = "nomeUF") String nomeUF, @Param(value = "cdPrograma") String cdPrograma, @Param(value = "cdSubPrograma") String cdSubPrograma,
                                                    @Param(value = "cdFonteRecurso") String cdFonteRecurso, @Param(value = "cdTipoSeguro") String cdTipoSeguro, @Param(value = "cdModalidade") String cdModalidade, @Param(value = "mesEmissao") String mesEmissao, @Param(value = "anoEmissao") String anoEmissao, @Param(value = "qtdCusteio") Integer qtdCusteio,
                                                    @Param(value = "vlCusteio") BigDecimal vlCusteio, @Param(value = "atividade") String atividade, @Param(value = "areaCusteio") Double areaCusteio) {
        List<DadosDTO> list = service.getDadosFilter(nomeProduto, nomeRegiao, nomeUF, cdPrograma, cdSubPrograma, cdFonteRecurso, cdTipoSeguro, cdModalidade, mesEmissao, anoEmissao, qtdCusteio, vlCusteio, atividade, areaCusteio);
        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @Tag(name = "Busca")
    @Operation(summary = "Retorna contratos de custeio com paginação, com número e tamanho da pagina")
    @GetMapping("/pageable")
    public ResponseEntity<List<DadosDTO>> getPageable(@RequestParam(value = "Pagina", defaultValue = "0") Integer pagina,
                                                      @RequestParam(value = "Tamanho", defaultValue = "10") Integer tamanho) {
        try {
            List<DadosDTO> dados = service.getPageable(PageRequest.of(pagina, tamanho, Sort.Direction.ASC, "id"));
            return dados.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(dados);
        }catch(java.lang.IllegalArgumentException e){
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }
  
    @Tag(name = "Busca")
    @Operation(summary = "Retorna um contrato com o ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<DadosDTO> get(@PathVariable("id") Long id) {
        DadosDTO dados = service.getDadosByID(id);
        return ResponseEntity.ok(dados);
    }

    @Tag(name = "Busca")
    @Operation(summary = "Retorna o valor total de SOJA, MILHO, FEIJÃO, TRIGO e CANA DE AÇUCAR do ano informado")
    @GetMapping("/custeioAno")
    public ResponseEntity search(@RequestParam("anoEmissao") String anoEmissao) {
        List<AcumuloDTO> dados = service.total(anoEmissao);
        return dados.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(dados);
    }

    @Tag(name = "Manutenção")
    @Operation(summary = "Insere um novo contrato de Custeio por Produto, Região e UF")
    @PostMapping
    public ResponseEntity post(@RequestBody DadosEntity dados) {
        DadosDTO d = service.insert(dados);

        URI location = getUri(d.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @Tag(name = "Manutenção")
    @Operation(summary = "Atualiza um contrato de Custeio por Produto, Região e UF do ID informado")
    @PutMapping("/{id}")
    public ResponseEntity<DadosDTO> put(@PathVariable("id") Long id, @RequestBody DadosDTO dados) {
        DadosDTO c = service.update(dados, id);

        return ResponseEntity.ok(c);
    }

    @Tag(name = "Manutenção")
    @Operation(summary = "Remove um contrato de Custeio por Municipio e Produto do ID informado") 
    @DeleteMapping ("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "Manutenção")
    @Operation(summary = "Deleta todos os dados do Banco de Dados")
    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
