package com.malbano.rural.model.repository;

import com.malbano.rural.model.entity.DadosEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DadosRepository extends JpaRepository<DadosEntity, Long> {
    @Query(value = "SELECT p FROM DadosEntity p")
    Page<DadosEntity> pageable(Pageable pageable);
}
