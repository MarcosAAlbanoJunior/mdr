package com.malbano.rural.lista;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaTesteUnitario {

    private DadosEntity entity = new DadosEntity(1L,"Leite", "SUL", "RS", "0999", "0", "0201", "3", "21", "06", 2, BigDecimal.valueOf(5580000.0),"2", "21", null);
    private DadosDTO dto = new DadosDTO(1L,"Leite", "SUL", "PR", "0999", "0", "0201", "3", "21", "06", 2, BigDecimal.valueOf(5580000.0),"2", "21", 10.0);
}
