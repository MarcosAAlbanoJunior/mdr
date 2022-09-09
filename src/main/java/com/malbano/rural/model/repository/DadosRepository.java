package com.malbano.rural.model.repository;

import com.malbano.rural.model.dto.AcumuloDTO;
import com.malbano.rural.model.entity.DadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DadosRepository extends JpaRepository<DadosEntity, Long> {
//    @Query(value = "SELECT p FROM DadosEntity p")
//    Page<DadosEntity> pageable(Pageable pageable);

    List<DadosEntity> findByNomeRegiaoContains(String query);


//    @Query(value = "SELECT sum(vlCusteio) FROM DadosEntity")
//    Double totalYear();

    @Query(value = "SELECT new com.malbano.rural.model.dto.AcumuloDTO(c.nomeProduto as nomeProduto, c.nomeRegiao as nomeRegiao, c.nomeUF as nomeUF, c.anoEmissao as anoEmissao, SUM(c.vlCusteio) as vlCusteioTotal) FROM DadosEntity c " +
    "WHERE c.anoEmissao = :anoEmissao AND c.nomeProduto IN ('SOJA', 'MILHO', 'FEIJÃO', 'TRIGO', 'CANA DE AÇUCAR') " +
    "GROUP BY c.anoEmissao, c.nomeRegiao, c.nomeUF, c.nomeProduto ")
    List<AcumuloDTO> findByAnoEmissao(String anoEmissao);
}