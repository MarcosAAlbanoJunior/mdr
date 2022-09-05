package com.malbano.rural.feign;

import com.malbano.rural.model.entity.DadosList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dados", url = "https://olinda.bcb.gov.br/")
public interface ConectaAPI {

    @GetMapping(path = "olinda/servico/SICOR/versao/v2/odata/ComercRegiaoUFProduto?$top=500&$format=json")
    DadosList getDados();
}
