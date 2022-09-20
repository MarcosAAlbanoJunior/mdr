package com.malbano.rural.model.mapper;

import com.malbano.rural.lista.ListaDadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DTOtoEntityParseTest {

    ListaDadosDTO dados = new ListaDadosDTO();

    @Test
    public void converterDTOparaEntity(){

        DadosEntity dtoConvertido = DTOtoEntityParse.dadosDTOtoDadosEntity(dados.getDto1());
        Assertions.assertNotNull(dtoConvertido);

    }




}