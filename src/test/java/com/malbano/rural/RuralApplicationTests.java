package com.malbano.rural;

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

import java.util.ArrayList;
import java.util.List;


class RuralApplicationTests {

    @Test
    public void applicationContextLoaded(){

    }

    @Test
    public void applicationContextTest(){
        RuralApplication.main(new String[] {});
    }


}