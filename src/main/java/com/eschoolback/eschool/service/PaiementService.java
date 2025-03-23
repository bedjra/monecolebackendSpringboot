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

    public PaiementDto effectuerPaiement(PaiementDto paiementDto) {
        // Vérifier si montantActuel est négatif
        if (paiementDto.getMontantActuel() < 0) {
            throw new RuntimeException("Le montant actuel ne peut pas être négatif.");
        }

        // Récupérer l'élève par nom et prénom
        Optional<Eleve> eleveOpt = eleveRepository.findByEleveNomAndElevePrenom(
                paiementDto.getEleveNom(), paiementDto.getElevePrenom()
        );

        if (eleveOpt.isEmpty()) {
            throw new RuntimeException("Élève non trouvé !");
        }

        Eleve eleve = eleveOpt.get();

        // Récupérer la scolarité de l'élève
        Optional<Scolarite> scolariteOpt = scolariteRepository.findByNiveauAndSpecialite(
                eleve.getNiveauEtude(), eleve.getSpecialite()
        );

        if (scolariteOpt.isEmpty()) {
            throw new RuntimeException("Aucune scolarité trouvée !");
        }

        Scolarite scolarite = scolariteOpt.get();

        // Récupérer le paiement existant
        Optional<Paiement> paiementOpt = paiementRepository.findByEleve(eleve);

        Paiement paiement;
        if (paiementOpt.isPresent()) {
            paiement = paiementOpt.get();
        } else {
            paiement = new Paiement();
            paiement.setEleve(eleve);
            paiement.setScolarite(scolarite);
            paiement.setNiveau(eleve.getNiveauEtude());
            paiement.setSpecialite(eleve.getSpecialite());
            paiement.setMontantDejaPaye(Double.valueOf(0));
            paiement.setResteEcolage(scolarite.getMontant());
        }

        // Calcul du paiement
        long montantDejaPaye = (long) (paiement.getMontantDejaPaye() + paiementDto.getMontantActuel());
        long resteEcolage = (long) (scolarite.getMontant() - montantDejaPaye);

        if (resteEcolage < 0) {
            throw new RuntimeException("Le montant payé dépasse le total des frais de scolarité !");
        }

        // Mettre à jour le paiement
        paiement.setMontantDejaPaye((double) montantDejaPaye);
        paiement.setResteEcolage((double) resteEcolage);
        paiement.setMontantActuel((double) paiementDto.getMontantActuel());
        paiement.setDatePaiement(LocalDate.now());


        paiement = paiementRepository.save(paiement);


        return new PaiementDto(
                eleve.getId(),
                eleve.getEleveNom(),
                eleve.getElevePrenom(),
                eleve.getEleveMatricule(),
                eleve.getSpecialite(),
                eleve.getNiveauEtude(),
                scolarite,
                paiement.getDatePaiement(),
                paiement.getMontantActuel().longValue(),
                paiement.getResteEcolage().longValue(),
                paiement.getMontantDejaPaye().longValue()
        );

    }

    public PaiementDto getPaiementByMatricule(String matricule) {
        // Récupérer l'élève par matricule
        Optional<Eleve> eleveOpt = eleveRepository.findByEleveMatricule(matricule);

        if (eleveOpt.isEmpty()) {
            throw new RuntimeException("Élève non trouvé !");
        }

        Eleve eleve = eleveOpt.get();

        // Récupérer le paiement associé à l'élève
        Optional<Paiement> paiementOpt = paiementRepository.findByEleve(eleve);

        if (paiementOpt.isEmpty()) {
            throw new RuntimeException("Aucun paiement trouvé pour cet élève !");
        }

        Paiement paiement = paiementOpt.get();

        // Construire l'objet PaiementDto
        return new PaiementDto(
                eleve.getId(),
                eleve.getEleveNom(),
                eleve.getElevePrenom(),
                eleve.getEleveMatricule(),
                eleve.getSpecialite(),
                eleve.getNiveauEtude(),
                paiement.getScolarite(),
                paiement.getDatePaiement(),
                paiement.getMontantActuel().longValue(),
                paiement.getResteEcolage().longValue(),
                paiement.getMontantDejaPaye().longValue()
        );
    }
}


