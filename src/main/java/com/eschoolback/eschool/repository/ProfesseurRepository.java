package com.eschoolback.eschool.repository;


import com.eschoolback.eschool.Entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    List<Professeur> findByMatiere(String matiere);

}

