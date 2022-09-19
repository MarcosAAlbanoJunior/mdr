package com.malbano.rural.model.mapper;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;



public class DTOtoEntityParse {

    public static DadosEntity dadosDTOtoDadosEntity(DadosDTO dto){
        return DadosEntity.builder()
                .areaCusteio(dto.getAreaCusteio())
                .anoEmissao(dto.getAnoEmissao())
                .atividade(dto.getAtividade())
                .cdModalidade(dto.getCdModalidade())
                .cdFonteRecurso(dto.getCdFonteRecurso())
                .cdPrograma(dto.getCdPrograma())
                .cdSubPrograma(dto.getCdSubPrograma())
                .cdTipoSeguro(dto.getCdTipoSeguro())
                .nomeUF(dto.getNomeUF())
                .mesEmissao(dto.getMesEmissao())
                .nomeProduto(dto.getNomeProduto().replaceAll("\"", ""))
                .nomeRegiao(dto.getNomeRegiao())
                .qtdCusteio(dto.getQtdCusteio())
                .vlCusteio(dto.getVlCusteio())
                .build();
    }
}
