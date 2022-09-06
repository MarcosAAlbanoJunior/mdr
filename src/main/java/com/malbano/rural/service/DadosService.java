package com.malbano.rural.service;

import com.malbano.rural.exception.ObjectNotFoundException;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.model.mapper.DTOtoEntityParse;
import com.malbano.rural.model.repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DadosService {

    @Autowired
    private DadosRepository rep;

    public Iterable<DadosEntity> insert(DadosList dados) {
        for (DadosDTO x : dados.getValue()) {
            DadosEntity dadosEntity = DTOtoEntityParse.dadosDTOtoDadosEntity(x);
            rep.save(dadosEntity);
        }
        return rep.findAll();
    }

    public List<DadosDTO> getDados(){
        return rep.findAll().stream().map(DadosDTO::create).collect(Collectors.toList());

    }

    public Page<DadosEntity> getPageable(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return rep.pageable(pageRequest);
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

        // Busca os dados no banco de dados
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

            // Atualiza os dados
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
