package com.malbano.rural.controller;

import com.malbano.rural.dados.Dados;
import com.malbano.rural.dados.DadosList;
import com.malbano.rural.feign.ConectaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DadosController {

    @Autowired
    ConectaAPI conectaAPI;

    @GetMapping
    public DadosList getDados(){
        return conectaAPI.buscarDados();
    }
}
