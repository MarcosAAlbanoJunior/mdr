package com.malbano.rural.model.mapper;

import com.malbano.rural.lista.ListaTesteUnitario;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class DTOtoEntityParseTest {

    ListaTesteUnitario dados = new ListaTesteUnitario();

    @Test
    public void converterDTOparaEntity(){

        DadosEntity dtoConvertido = DTOtoEntityParse.dadosDTOtoDadosEntity(dados.getDto());
        Assertions.assertNotNull(dtoConvertido);

    }




}