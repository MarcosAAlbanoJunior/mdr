package com.malbano.rural.service;

import com.malbano.rural.exception.ObjectNotFoundException;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.model.mapper.DTOtoEntityParse;
import com.malbano.rural.model.repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DadosService {

    @Autowired
    private DadosRepository rep;

    public List<DadosEntity> insert(DadosList dados) {
        for (DadosDTO x : dados.getValue()) {
            DadosEntity dadosEntity = DTOtoEntityParse.dadosDTOtoDadosEntity(x);
            rep.save(dadosEntity);
        }
        return rep.findAll();
    }

    public List<DadosDTO> getDados(){
        return rep.findAll().stream().map(DadosDTO::create).collect(Collectors.toList());

    }
    public DadosDTO getDadosByID(Long id) {
        Optional<DadosEntity> dados = rep.findById(id);
        return dados.map(DadosDTO::create).orElseThrow(() -> new ObjectNotFoundException("Dados não encontrado"));
    }

    public DadosDTO insert(DadosEntity dados) {
        Assert.isNull(dados.getId(), "Não foi possivel inserir o registro");

        return DadosDTO.create(rep.save(dados));
    }

    public DadosDTO update(DadosEntity dados, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<DadosEntity> optional = rep.findById(id);
        if(optional.isPresent()) {
            DadosEntity db = optional.get();
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

            // Atualiza o carro
            rep.save(db);

            return DadosDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
