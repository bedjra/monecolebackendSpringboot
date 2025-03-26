package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Paiement;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByEleve(Eleve eleve);
    List<Paiement> findByNiveauAndSpecialite(NiveauEtude niveau, Specialite specialite);
    @Query("SELECT DISTINCT p.specialite FROM Paiement p")
    List<Specialite> findDistinctSpecialites();

    @Query("SELECT DISTINCT p.niveau FROM Paiement p")
    List<NiveauEtude> findDistinctNiveaux();


    // Récupérer seulement ceux avec un reste d'écolage > 0
    List<Paiement> findByNiveauAndSpecialiteAndResteEcolageGreaterThan(NiveauEtude niveau, Specialite specialite, Double resteEcolage);
}
