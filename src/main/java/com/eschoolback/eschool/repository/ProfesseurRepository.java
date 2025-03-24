package com.eschoolback.eschool.repository;


import com.eschoolback.eschool.Entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    List<Professeur> findByMatiere(String matiere);
    Optional<Professeur> findByNom(String nom);

}

