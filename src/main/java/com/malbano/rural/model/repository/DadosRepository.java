package com.malbano.rural.model.repository;

import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.dto.DadosDTO;
import com.malbano.rural.model.entity.DadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DadosRepository extends JpaRepository<DadosEntity, Long> {

    List<DadosEntity> findByNomeRegiaoContains(String query);

    @Query(value = "SELECT new com.malbano.rural.model.dto.AcumuloDTO(c.nomeProduto as nomeProduto, c.anoEmissao as anoEmissao, SUM(c.vlCusteio) as vlCusteioTotal) FROM DadosEntity c " +
            "WHERE c.anoEmissao = :anoEmissao AND c.nomeProduto IN ('SOJA', 'MILHO', 'FEIJÃO', 'TRIGO', 'CANA DE AÇUCAR') " +
            "GROUP BY c.anoEmissao, c.nomeProduto ")
    List<AcumuloDTO> findByAnoEmissao(String anoEmissao);

    @Query(value = "SELECT new com.malbano.rural.model.dto.DadosDTO(c.id as id, c.nomeProduto as nomeProduto, c.nomeRegiao as nomeRegiao, c.nomeUF as nomeUF, c.mesEmissao as mesEmissao, c.anoEmissao as anoEmissao, " +
            "c.cdPrograma as cdPrograma, c.cdSubPrograma as cdSubPrograma, c.cdFonteRecurso as cdFonteRecurso, c.cdTipoSeguro as cdTipoSeguro, c.qtdCusteio as qtdCusteio, c.vlCusteio as vlCusteio, " +
            "c.atividade as atividade, c.cdModalidade as cdModalidade, c.areaCusteio as areaCusteio) FROM DadosEntity c " +
            "WHERE (:nomeProduto IS NULL OR c.nomeProduto = :nomeProduto) AND (:nomeRegiao IS NULL OR c.nomeRegiao = :nomeRegiao) AND (:nomeUF IS NULL OR c.nomeUF = :nomeUF) AND (:cdPrograma IS NULL OR c.cdPrograma = :cdPrograma) AND (:cdSubPrograma IS NULL OR c.cdSubPrograma = :cdSubPrograma) AND (:cdFonteRecurso IS NULL OR c.cdFonteRecurso = :cdFonteRecurso) AND (:cdTipoSeguro IS NULL OR c.cdTipoSeguro = :cdTipoSeguro) " +
            "AND (:cdModalidade IS NULL OR c.cdModalidade = :cdModalidade) AND (:mesEmissao IS NULL OR c.mesEmissao = :mesEmissao) AND (:anoEmissao IS NULL OR c.anoEmissao = :anoEmissao) AND (:qtdCusteio IS NULL OR c.qtdCusteio = :qtdCusteio) AND (:vlCusteio IS NULL OR c.vlCusteio = :vlCusteio) AND (:atividade IS NULL OR c.atividade = :atividade) AND (:areaCusteio IS NULL OR c.areaCusteio = :areaCusteio)")
    List<DadosDTO> findAllFiltro(String nomeProduto, String nomeRegiao, String nomeUF, String cdPrograma, String cdSubPrograma, String cdFonteRecurso, String cdTipoSeguro, String cdModalidade, String mesEmissao, String anoEmissao, Integer qtdCusteio, BigDecimal vlCusteio, String atividade, Double areaCusteio);


}
