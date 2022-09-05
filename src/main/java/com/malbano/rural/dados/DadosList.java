package com.malbano.rural.dados;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DadosList {

   private List<Dados> value;
}
