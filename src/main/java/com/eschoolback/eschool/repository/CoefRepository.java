package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoefRepository extends JpaRepository<Coefficient, Long> {
    List<Coefficient> findByNiveauEtudeAndSpecialite(NiveauEtude niveauEtude, Specialite specialite);
}