package com.malbano.rural.model.mapper;

import com.malbano.rural.lista.ListaTesteUnitario;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntitytoDTOParseTest {

    ListaTesteUnitario dados = new ListaTesteUnitario();

    @Test
    public void converterEntitytoDto(){
        DadosDTO entityConvertido = EntitytoDTOParse.dadosEntitytoDTOParse(dados.getEntity());
        Assertions.assertNotNull(entityConvertido);

    }

}