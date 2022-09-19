package com.malbano.rural.service;

import com.malbano.rural.lista.ListaDadosDTO;
import com.malbano.rural.lista.ListaDadosEntity;
import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.service.exception.MethodNotAllowed;
import com.malbano.rural.service.exception.ObjectNotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DadosServiceTest {
    @Autowired
    private DadosService service;

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
    public void deleteSetup(){
        service.deleteAll();
    }
    ListaDadosEntity listEntity = new ListaDadosEntity();
    ListaDadosDTO listDTO = new ListaDadosDTO();

    @Test
    void onboarding() {
        try{
            setup();
            fail();

        }catch(MethodNotAllowed e){
            //ok
        }
    }

    @Test
    void getDados() {
        service.getDados();
        assertFalse(service.getDados().isEmpty());
    }

    @Test
    void getDadosFilter() {
        List<DadosDTO> list = service.getDadosFilter("SOJA", null,null,null,null,
                                                    null,null,null,null,null,
                                                        null,null,null,null);
        assertFalse(list.isEmpty());
    }

    @Test
    void getDadosByID() {
        assertNotNull(service.getDadosByID(service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null)));
    }

    @Test
    void getPageable() {
        List<DadosDTO> list = service.getPageable(PageRequest.of(0, 2, Sort.Direction.ASC, "id"));
        assertFalse(list.isEmpty());
    }

    @Test
    void insert() {
        DadosDTO d = service.insert(listEntity.getEntity1());
        assertNotNull(d);
    }

    @Test
    void total(){
        List<AcumuloDTO> dados = service.total("2020");
        assertFalse(dados.isEmpty());
    }

    @Test
    void update() {
       DadosDTO dados;
        Long id = service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null);
        dados = service.update(listDTO.getDto3(), id);
        assertEquals("LEITE", dados.getNomeProduto());

    }

    @Test
    void delete() {
       Long id = service.getDados().stream().map(DadosDTO::getId).findFirst().orElse(null);
        service.delete(id);
         try{
             service.delete(id);
             fail("Objeto nao existe");
         }catch (ObjectNotFoundException e){
             //ok
         }
    }

    @Test
    void deleteAll() {
        service.deleteAll();
        assertTrue(service.getDados().isEmpty());
    }
}