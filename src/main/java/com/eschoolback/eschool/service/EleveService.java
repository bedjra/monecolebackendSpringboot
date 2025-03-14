package com.eschoolback.eschool.service;


import com.eschoolback.eschool.Dto.StatistiquesDTO;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;



    public Eleve saveEleve(Eleve eleve) {
        // Vérifier si un élève avec le même nom et prénom existe déjà
        Optional<Eleve> existingEleve = eleveRepository.findByEleveNomAndElevePrenom(
                eleve.getEleveNom(), eleve.getElevePrenom()
        );

        if (existingEleve.isPresent()) {
            throw new RuntimeException("Un élève avec le même nom et prénom existe déjà.");
        }

        // Vérifier que la spécialité est définie
        if (eleve.getSpecialite() == null) {
            throw new IllegalArgumentException("La spécialité de l'élève ne peut pas être nulle.");
        }

        // Générer le matricule
        String matricule = generateMatricule(eleve);
        eleve.setEleveMatricule(matricule);

        // Sauvegarde en base de données
        return eleveRepository.save(eleve);
    }

    private String generateMatricule(Eleve eleve) {
        // 1️⃣ Mapper le niveau d’étude en abréviation
        String niveauCode;
        switch (eleve.getNiveauEtude()) {
            case Seconde: niveauCode = "SEC"; break;
            case Premiere: niveauCode = "PRE"; break;
            case Terminal: niveauCode = "TLE"; break;
            default: throw new IllegalArgumentException("Niveau inconnu : " + eleve.getNiveauEtude());
        }

        // 2️⃣ Récupérer le code de la spécialité
        String specialiteCode = eleve.getSpecialite().name(); // Déjà sous forme de texte (A4, D, G1, G2, G3)

        // 3️⃣ Récupérer le nombre d’élèves existants dans CE niveau et CETTE spécialité
        long count = eleveRepository.countByNiveauEtudeAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite());

        // 4️⃣ Construire le matricule sous le format "SEC-A4-001"
        return niveauCode + "-" + specialiteCode + "-" + String.format("%03d", count + 1);
    }


    public Eleve updateEleveByMatricule(String matricule, Eleve updatedEleve) {
        Optional<Eleve> existingEleve = eleveRepository.findByEleveMatricule(matricule);

        if (existingEleve.isPresent()) {
            Eleve eleve = existingEleve.get();

            // Vérifier si le niveau ou la spécialité a changé
            boolean niveauChanged = !eleve.getNiveauEtude().equals(updatedEleve.getNiveauEtude());
            boolean specialiteChanged = !eleve.getSpecialite().equals(updatedEleve.getSpecialite());

            // Mettre à jour les informations de l'élève
            eleve.setEleveNom(updatedEleve.getEleveNom());
            eleve.setElevePrenom(updatedEleve.getElevePrenom());
            eleve.setEleveAdresse(updatedEleve.getEleveAdresse());
            eleve.setEleveSexe(updatedEleve.getEleveSexe());
            eleve.setEleveDateNaiss(updatedEleve.getEleveDateNaiss());
            eleve.setEleveEtatProvenance(updatedEleve.getEleveEtatProvenance());
            eleve.setEleveLieuNais(updatedEleve.getEleveLieuNais());

            eleve.setNiveauEtude(updatedEleve.getNiveauEtude());
            eleve.setSpecialite(updatedEleve.getSpecialite());

            // Régénérer le matricule si nécessaire
            if (niveauChanged || specialiteChanged) {
                String newMatricule = generateMatricule(eleve);
                eleve.setEleveMatricule(newMatricule);
            }

            // Sauvegarder les modifications
            return eleveRepository.save(eleve);
        } else {
            throw new RuntimeException("Élève introuvable avec le matricule : " + matricule);
        }
    }

    /////////////aide au put au fontend  ///////////////////////////
    public Long getIdByMatricule(String matricule) {
        Optional<Eleve> eleve = eleveRepository.findByEleveMatricule(matricule);
        return (eleve.isPresent()) ? eleve.get().getId() : null;  // 👈 Maintenant, c'est un Long
    }

    /////////////GET ETUDIANT ALL ///////////////////////////
    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    /////////////GET ETUDIANT BY NIVEU ETUDE ///////////////////////////
    public List<Eleve> getAllByNiveauEtude(NiveauEtude niveauEtude) {
        return eleveRepository.findByNiveauEtude(niveauEtude);
    }



    /////////////GET BY NIVEU ETUDE et SPECIALITE ///////////////////////////
    public List<Eleve> getElevesByNiveauEtSpecialite(NiveauEtude niveauEtude, Specialite specialite) {
        return eleveRepository.findByNiveauEtudeAndSpecialite(niveauEtude, specialite);
    }

    /////////////GET ETUDIANT BY MATRICULE ///////////////////////////
    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec l'ID : " + id));
    }


    public Optional<Eleve> getEleveByNomAndPrenom(String nom, String prenom) {
        return eleveRepository.findByEleveNomAndElevePrenom(nom, prenom);
    }


//    public List<StatistiquesDTO> getStatistiquesParNiveau() {
//        List<Object[]> result = eleveRepository.getStatistiquesParNiveau();
//        List<StatistiquesDTO> statistiques = new ArrayList<>();
//
//        for (Object[] row : result) {
//            NiveauEtude niveauEtude = (NiveauEtude) row[0];
//            Long nbTotalEleves = (Long) row[1];
//            Long nbFilles = (Long) row[2];
//            Long nbGarcons = (Long) row[3];
//            statistiques.add(new StatistiquesDTO(niveauEtude, nbTotalEleves, nbFilles, nbGarcons));
//        }
//
//        return statistiques;
//    }
//
    public List<StatistiquesDTO> getStatistiquesParNiveau() {
        // Récupérer les résultats de la requête
        List<Object[]> result = eleveRepository.getStatistiquesParNiveau();
        List<StatistiquesDTO> statistiques = new ArrayList<>();

        // Transformation des résultats en DTO
        for (Object[] row : result) {
            NiveauEtude niveauEtude = (NiveauEtude) row[0];
            Long nbTotalEleves = (Long) row[1];
            Long nbFilles = (Long) row[2];
            Long nbGarcons = (Long) row[3];
            statistiques.add(new StatistiquesDTO(niveauEtude, nbTotalEleves, nbFilles, nbGarcons));
        }

        return statistiques;
    }


}