package com.malbano.rural.feign;

import com.malbano.rural.dados.Dados;
import com.malbano.rural.dados.DadosList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "dados", url = "https://olinda.bcb.gov.br/")
public interface ConectaAPI {

    @GetMapping(path = "olinda/servico/SICOR/versao/v2/odata/ComercRegiaoUFProduto?$top=1000&$format=json")
    DadosList buscarDados();
}
