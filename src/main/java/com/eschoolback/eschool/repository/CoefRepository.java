package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Coef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoefRepository extends JpaRepository<Coef, Long> {
    List<Coef> findByMatiere(String matiere);
}