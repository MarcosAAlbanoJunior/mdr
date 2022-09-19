package com.malbano.rural.lista;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDadosEntity {

    private DadosEntity entity1 = new DadosEntity(1L,"SOJA", "SUL", "RS", "0999", "2020", "0201", "3", "21", "06", 2, BigDecimal.valueOf(5580000.0),"2", "21", null);
    private DadosEntity entity2 = new DadosEntity(2L,"FEIJÃO", "SUL", "PR", "0999", "2020", "0201", "3", "21", "06", 2, BigDecimal.valueOf(5580000.0),"2", "21", null);
    private DadosEntity entity3 = new DadosEntity(3L,"LEITE", "SUL", "PR", "0999", "0", "0201", "3", "21", "06", 2, BigDecimal.valueOf(5580000.0),"2", "21", null);

}
