package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Dto.PaiementDto;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Paiement;
import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import com.eschoolback.eschool.repository.PaiementRepository;
import com.eschoolback.eschool.repository.ScolariteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ScolariteRepository scolariteRepository;

    @Autowired
    private EleveRepository eleveRepository;

    public List<PaiementDto> getPaiementsByNiveauEtSpecialite(NiveauEtude niveau, Specialite specialite) {
        List<Paiement> paiements = paiementRepository.findByNiveauAndSpecialite(niveau, specialite);

        return paiements.stream()
                .map(this::convertToPaiementDto)
                .collect(Collectors.toList());
    }

    private PaiementDto convertToPaiementDto(Paiement paiement) {
        return new PaiementDto(
                paiement.getEleve().getId(),
                paiement.getEleve().getEleveNom(),
                paiement.getEleve().getElevePrenom(),
                paiement.getEleve().getEleveMatricule(),
                paiement.getSpecialite(),
                paiement.getNiveau(),
                paiement.getScolarite(),
                paiement.getDatePaiement(),
                paiement.getMontantActuel().longValue(),
                paiement.getResteEcolage().longValue(),
                paiement.getMontantDejaPaye().longValue()

        );
    }

    public List<Specialite> getAllSpecialitesLieesAuxPaiements() {
        return paiementRepository.findDistinctSpecialites();
    }

    public List<NiveauEtude> getAllNiveauxLiesAuxPaiements() {
        return paiementRepository.findDistinctNiveaux();
    }

}


