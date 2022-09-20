package com.malbano.rural.service;

import com.malbano.rural.model.mapper.EntitytoDTOParse;
import com.malbano.rural.service.exception.MethodNotAllowed;
import com.malbano.rural.service.exception.ObjectNotFoundException;
import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.model.mapper.DTOtoEntityParse;
import com.malbano.rural.model.repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DadosService {

    @Autowired
    private DadosRepository rep;

    public Iterable<DadosEntity> onboarding(DadosList dados) {
        if (getDados().isEmpty()) {
            for (DadosDTO x : dados.getValue()) {
                DadosEntity dadosEntity = DTOtoEntityParse.dadosDTOtoDadosEntity(x);
                rep.save(dadosEntity);
            }
            return rep.findAll();
        } else
            throw new MethodNotAllowed("Já existe dados na tabela");
    }

    public List<DadosDTO> getDados() {
        return rep.findAll().stream().map(DadosDTO::create).collect(Collectors.toList());

    }

    public List<DadosDTO> getDadosFilter(String nomeProduto, String nomeRegiao, String nomeUF, String cdPrograma, String cdSubPrograma, String cdFonteRecurso, String cdTipoSeguro, String cdModalidade, String mesEmissao, String anoEmissao, Integer qtdCusteio, BigDecimal vlCusteio, String atividade, Double areaCusteio) {
        return rep.findAllFiltro(nomeProduto, nomeRegiao, nomeUF, cdPrograma, cdSubPrograma, cdFonteRecurso, cdTipoSeguro, cdModalidade, mesEmissao, anoEmissao, qtdCusteio, vlCusteio, atividade, areaCusteio);
    }

    public List<DadosDTO> getPageable(Pageable pageable) {

        return rep.findAll(pageable).stream().map(DadosDTO::create).collect(Collectors.toList());
    }

    public DadosDTO getDadosByID(Long id) {
        Optional<DadosEntity> dados = rep.findById(id);
        return dados.map(DadosDTO::create).orElseThrow(() -> new ObjectNotFoundException("Dados não encontrado"));
    }

    public DadosDTO insert(DadosEntity dados) {
        return DadosDTO.create(rep.save(dados));
    }

    public List<AcumuloDTO> total(String anoEmissao) {
        return rep.findByAnoEmissao(anoEmissao);
    }

    public DadosDTO update(DadosDTO dados, Long id) {
        Optional<DadosEntity> optional = rep.findById(id);
        DadosDTO db = EntitytoDTOParse.dadosEntitytoDTOParse(optional.orElseThrow());
        // Copiar as propriedades
        db.setAnoEmissao(dados.getAnoEmissao());
        db.setAtividade(dados.getAtividade());
        db.setAreaCusteio(dados.getAreaCusteio());
        db.setCdFonteRecurso(dados.getCdFonteRecurso());
        db.setCdModalidade(dados.getCdModalidade());
        db.setCdPrograma(dados.getCdPrograma());
        db.setCdSubPrograma(dados.getCdSubPrograma());
        db.setCdTipoSeguro(dados.getCdTipoSeguro());
        db.setMesEmissao(dados.getMesEmissao());
        db.setNomeProduto(dados.getNomeProduto());
        db.setNomeRegiao(dados.getNomeRegiao());
        db.setNomeUF(dados.getNomeUF());
        db.setQtdCusteio(dados.getQtdCusteio());
        db.setVlCusteio(dados.getVlCusteio());
        System.out.println("Dados id " + db.getId());

        rep.save(DTOtoEntityParse.dadosDTOtoDadosEntity(db));

        return db;
    }

    public void delete(Long id) {
        try {
            rep.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    public void deleteAll() {
        rep.deleteAll();
    }
}
