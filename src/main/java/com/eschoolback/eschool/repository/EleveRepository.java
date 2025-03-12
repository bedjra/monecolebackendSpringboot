package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    List<Eleve> findByNiveauEtude(NiveauEtude niveauEtude);
    Optional<Eleve> findByEleveNomAndElevePrenom(String eleveNom, String elevePrenom);
    Optional<Eleve> findByEleveMatricule(String matricule);

}
