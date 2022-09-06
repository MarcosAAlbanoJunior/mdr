package com.malbano.rural;

import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.service.DadosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = RuralApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RuralApplicationTests {

	@Autowired
	protected TestRestTemplate rest;

	@Autowired
	private DadosService service;

//	private ResponseEntity<DadosDTO> getCarro(String url) {
//		return get(url, DadosDTO.class);
//	}




}
