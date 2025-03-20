package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScolariteRepository extends JpaRepository<Scolarite, Long> {
    Optional<Scolarite> findByNiveauAndSpecialite(NiveauEtude niveau, Specialite specialite);


}
