package com.malbano.rural;

import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.lista.ListaDadosDTO;
import com.malbano.rural.lista.ListaDadosEntity;
import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.service.DadosService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = RuralApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RuralApplicationTests {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
     private DadosService service;

    @Autowired
    ConectaAPI conectaAPI;

    ListaDadosEntity listEntity = new ListaDadosEntity();
    ListaDadosDTO listDTO = new ListaDadosDTO();

    @BeforeEach
    public void setup() {
        List<DadosDTO> listMock = new ArrayList<>();
        listMock.add(listDTO.getDto1());
        listMock.add(listDTO.getDto2());

        DadosList list = new DadosList();
        list.setValue(listMock);

        service.onboarding(list);
    }

    @AfterEach
    public void deleteSetup() {
        service.deleteAll();
    }

    private ResponseEntity<DadosDTO> getDado(String url) {
        return
                rest.getForEntity(url, DadosDTO.class);
    }

    private ResponseEntity<List<DadosDTO>> getDados(String url) {
        return rest.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DadosDTO>>() {
                });
    }

    private ResponseEntity<List<AcumuloDTO>> getAcumulos(String url) {
        return rest.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AcumuloDTO>>() {
                });
    }

    @Test
    public void testSave() {
        ResponseEntity response = rest
                .postForEntity("/api/dados", listEntity.getEntity3(), null);

        System.out.println(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetByID() {
        Long id = service.getDados().stream().map(x -> x.getId()).findFirst().orElse(null);
        ResponseEntity<DadosDTO> response = rest.getForEntity("/api/dados/{id}", DadosDTO.class, id);
        DadosDTO d = response.getBody();
        Assertions.assertEquals("SOJA", d.getNomeProduto());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetComPageable() {

        ResponseEntity<DadosDTO[]> responseEntity = rest.getForEntity("/api/dados/pageable", DadosDTO[].class, 0, 2, Sort.Direction.ASC, "id");
        DadosDTO[] objects = responseEntity.getBody();

        Assert.assertNotNull(objects);
    }
    @Test
    public void testGetComPageableNoContent() {
        service.deleteAll();
        ResponseEntity<DadosDTO[]> responseEntity = rest.getForEntity("/api/dados/pageable", DadosDTO[].class, 2, 2, Sort.Direction.ASC, "id");
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void TestAnoCusteio(){
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/custeioAno?anoEmissao={anoEmissao}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {},
                        "2020"
                );
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void TestAnoCusteioEmpty(){
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/custeioAno?anoEmissao={anoEmissao}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {},
                        "2023"
                );
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }




}