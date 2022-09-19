package com.malbano.rural.model.mapper;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;


public class EntitytoDTOParse {

    public static DadosDTO dadosEntitytoDTOParse(DadosEntity entity){
        return  DadosDTO.builder()
                .areaCusteio(entity.getAreaCusteio())
                .anoEmissao(entity.getAnoEmissao())
                .atividade(entity.getAtividade())
                .cdModalidade(entity.getCdModalidade())
                .cdFonteRecurso(entity.getCdFonteRecurso())
                .cdPrograma(entity.getCdPrograma())
                .cdSubPrograma(entity.getCdSubPrograma())
                .cdTipoSeguro(entity.getCdTipoSeguro())
                .nomeUF(entity.getNomeUF())
                .mesEmissao(entity.getMesEmissao())
                .nomeProduto(entity.getNomeProduto().replaceAll("\"", ""))
                .nomeRegiao(entity.getNomeRegiao())
                .qtdCusteio(entity.getQtdCusteio())
                .vlCusteio(entity.getVlCusteio())
                .build();
    }
}
