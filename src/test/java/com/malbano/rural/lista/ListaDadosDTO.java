package com.malbano.rural.lista;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.mapper.EntitytoDTOParse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDadosDTO {
    ListaDadosEntity entity = new ListaDadosEntity();

    private DadosDTO dto1 = EntitytoDTOParse.dadosEntitytoDTOParse(entity.getEntity1());
    private DadosDTO dto2 = EntitytoDTOParse.dadosEntitytoDTOParse(entity.getEntity2());
    private DadosDTO dto3 = EntitytoDTOParse.dadosEntitytoDTOParse(entity.getEntity3());
}
