package com.malbano.rural.service;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.model.mapper.DTOtoEntityParse;
import com.malbano.rural.model.repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DadosService {

    @Autowired
    private DadosRepository rep;

    public List<DadosEntity> insert(DadosList dados) {
        dados.getValue();

        for (DadosDTO x : dados.getValue()) {
            DadosEntity dadosEntity = DTOtoEntityParse.dadosDTOtoDadosEntity(x);
            rep.save(dadosEntity);
        }

        return rep.findAll();
    }

}
