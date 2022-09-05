package com.malbano.rural.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.malbano.rural.model.dto.DadosDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class DadosList {

   private List<DadosDTO> value;
}
