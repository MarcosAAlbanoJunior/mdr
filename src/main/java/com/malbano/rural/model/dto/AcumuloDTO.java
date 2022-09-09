package com.malbano.rural.model.dto;

import com.malbano.rural.model.entity.DadosEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcumuloDTO {

    private String nomeProduto;
    private String nomeRegiao;
    private String nomeUF;
    private String anoEmissao;
    private BigDecimal vlCusteioTotal;


    public static AcumuloDTO create(DadosEntity dados) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dados, AcumuloDTO.class);
    }

}
