package com.malbano.rural.model.mapper;

import com.malbano.rural.lista.ListaDadosEntity;
import com.malbano.rural.model.dto.DadosDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EntitytoDTOParseTest {

    ListaDadosEntity dados = new ListaDadosEntity();

    @Test
    public void converterEntitytoDto(){
        DadosDTO entityConvertido = EntitytoDTOParse.dadosEntitytoDTOParse(dados.getEntity1());
        Assertions.assertNotNull(entityConvertido);

    }

}