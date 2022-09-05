package com.malbano.rural.controller;

import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.service.DadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DadosController {

    @Autowired
    private DadosService service;
    @Autowired
    ConectaAPI conectaAPI;

    @PostMapping(path = "/insert")
    public ResponseEntity<List<DadosEntity>> insert() {

        DadosList dadosList = conectaAPI.getDados();
        return ResponseEntity.ok().body(service.insert(dadosList));
    }
}
