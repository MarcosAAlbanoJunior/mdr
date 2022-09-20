package com.malbano.rural.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malbano.rural.model.entity.DadosEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosDTO{

    private Long id;
    private String nomeProduto;
    private String nomeRegiao;
    private String nomeUF;
    @JsonProperty("MesEmissao")
    private String mesEmissao;
    @JsonProperty("AnoEmissao")
    private String anoEmissao;
    private String cdPrograma;
    private String cdSubPrograma;
    private String cdFonteRecurso;
    private String cdTipoSeguro;
    @JsonProperty("QtdCusteio")
    private Integer qtdCusteio;
    @JsonProperty("VlCusteio")
    private BigDecimal vlCusteio;
    @JsonProperty("Atividade")
    private String atividade;
    private String cdModalidade;
    @JsonProperty("AreaCusteio")
    private Double areaCusteio;


    public static DadosDTO create(DadosEntity dados) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dados, DadosDTO.class);
    }
}
