package com.malbano.rural.unitario;

import com.malbano.rural.exception.ObjectNotFoundException;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import com.malbano.rural.model.entity.DadosList;
import com.malbano.rural.service.DadosService;
import com.malbano.rural.unitario.lista.ListaTesteUnitario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DadosServiceTest {

    @Autowired
    private DadosService service;

    @Test
    public void crud(){
        DadosEntity dados = new DadosEntity();
        dados.setAnoEmissao("2017");
        dados.setAtividade("1");
        dados.setAreaCusteio(null);
        dados.setCdFonteRecurso("0444");
        dados.setCdModalidade("30");
        dados.setCdPrograma("0999");
        dados.setCdSubPrograma("0");
        dados.setCdTipoSeguro("9");
        dados.setMesEmissao("10");
        dados.setNomeProduto("TOMATE");
        dados.setNomeRegiao("SUL");
        dados.setNomeUF("PR");
        dados.setQtdCusteio(9);
        dados.setVlCusteio(7000000.00);

        DadosDTO d = service.insert(dados);

        assertNotNull(d);

        long id = d.getId();
        assertNotNull(id);

        //Buscar o objeto
        d = service.getDadosByID(id);

        assertNotNull(d);

        assertEquals("2017", d.getAnoEmissao());
        assertEquals("1", d.getAtividade());
        assertEquals(null, d.getAreaCusteio());
        assertEquals("0444", d.getCdFonteRecurso());
        assertEquals("30", d.getCdModalidade());
        assertEquals("0999", d.getCdPrograma());
        assertEquals("0", d.getCdSubPrograma());
        assertEquals("9", d.getCdTipoSeguro());
        assertEquals("10", d.getMesEmissao());
        assertEquals("TOMATE", d.getNomeProduto());
        assertEquals("SUL", d.getNomeRegiao());
        assertEquals("PR", d.getNomeUF());
        assertEquals(9, d.getQtdCusteio());
        assertEquals(7000000.00, d.getVlCusteio());

        // Deletar o objeto
        service.delete(id);

        //Verificar se deletou
        try{
            assertNull(service.getDadosByID(id));
            fail("Os dados não foi excluido");
        }
        catch(ObjectNotFoundException e){
            //OK
        }
    }
//
//    @Test
//    public void listDTO(){
//        ListaTesteUnitario teste = null;
//        DadosList list = null;
//        list.setValue((List<DadosDTO>) teste.getD1());
//        assertNotNull(list);
//
//    }
}
