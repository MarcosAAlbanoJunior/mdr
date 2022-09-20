package com.malbano.rural.controller;

import com.malbano.rural.RuralApplication;
import com.malbano.rural.feign.ConectaAPI;
import com.malbano.rural.lista.ListaDadosDTO;
import com.malbano.rural.lista.ListaDadosEntity;
import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.service.DadosService;
import com.malbano.rural.service.exception.MethodNotAllowed;
import com.malbano.rural.service.exception.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RuralApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DadosControllerTest {

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

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testSaveComId() {
       try {
           rest.postForEntity("/api/dados/1", listDTO.getDto3(), null);
       }
       catch(RuntimeException e){
           //ok
       }

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
    public void testGetByIDNotFound() {
        try {
            ResponseEntity<DadosDTO> response = rest.getForEntity("/api/dados/{id}", DadosDTO.class, 100);
        } catch (ObjectNotFoundException e) {
            //ok
        }
    }

    @Test
    public void testGetByIDString() {
        ResponseEntity<DadosDTO> response = rest.getForEntity("/api/dados/{id}", DadosDTO.class, "xxx");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
    public void TestAnoCusteio() {
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/custeioAno?anoEmissao={anoEmissao}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {
                        },
                        "2020"
                );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void TestAnoCusteioEmpty() {
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/custeioAno?anoEmissao={anoEmissao}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {
                        },
                        "2023"
                );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void TestPut() {
        Long id = service.getDados().stream().map(x -> x.getId()).findFirst().orElse(null);
        listDTO.getDto3().setId(null);
        rest.put(
                "/api/dados/{id}",
                listDTO.getDto3(),
                id);
        Assertions.assertEquals("LEITE", service.getDadosByID(id).getNomeProduto());
    }

    @Test
    public void TestPutNumberException() {
        try {
            Long id = service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null);
            listDTO.getDto3().setId(null);
            rest.put(
                    "/api/dados/{id}",
                    listDTO.getDto3(),
                    "xxx");
        } catch (NumberFormatException e) {
            //ok
        }
    }

    @Test
    public void TestPutNumberException1() {
        try {
            Long id = service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null);
            listDTO.getDto3().setId(null);
//            listDTO.getDto3().setMesEmissao(value="${type.uniqueTypeStringValue);
            rest.put(
                    "/api/dados/{id}",
                    listDTO.getDto3(),
                    id);
        } catch (HttpMessageNotReadableException e) {
            //ok
        }
    }

    @Test
    public void TestDelete() {
        Long id = service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null);
        rest.delete("/api/dados/{id}", id);
        try {
            service.getDadosByID(id);
        } catch (ObjectNotFoundException e) {
            //ok
        }
    }

    @Test
    public void TestDeleteAll() {
        rest.delete("/api/dados/deleteAll");
        Assertions.assertTrue(service.getDados().isEmpty());
    }

    @Test
    public void TestFiltro() {
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/filter?anoEmissao=2020&nomeProduto=SOJA",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {
                        }
                );
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void TestFiltroEmpity() {
        ResponseEntity<List<AcumuloDTO>> responseEntity =
                rest.exchange(
                        "/api/dados/filter?anoEmissao=2023&nomeProduto=LEITE",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AcumuloDTO>>() {
                        }
                );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void TesteOnboard() {
        service.deleteAll();
        rest.postForLocation(
                "/api/dados/insert",
                DadosEntity.class);
        Assertions.assertFalse(service.getDados().isEmpty());
    }

    @Test
    public void TesteOnboardFail() {
        try {
            rest.postForLocation(
                    "/api/dados/insert",
                    DadosEntity.class);
            Assertions.assertFalse(service.getDados().isEmpty());
        } catch (MethodNotAllowed e) {
            //ok
        }
    }


}