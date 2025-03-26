package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoefRepository extends JpaRepository<Coefficient, Long> {
    Optional<Coefficient> findByNiveauEtudeAndSpecialiteAndMatiere(
            NiveauEtude niveauEtude, Specialite specialite, String matiere);
    List<Coefficient> findByNiveauEtudeAndSpecialite(NiveauEtude niveauEtude, Specialite specialite);
}