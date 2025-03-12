package com.eschoolback.eschool.service;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // Générer le matricule
        String matricule = generateMatricule(eleve);
        eleve.setEleveMatricule(matricule);

        // Sauvegarde en base de données
        return eleveRepository.save(eleve);
    }

    private String generateMatricule(Eleve eleve) {
        // Mapper le niveau d'étude en abréviation
        String niveauCode;
        switch (eleve.getNiveauEtude()) {
            case Seconde:
                niveauCode = "SEC";
                break;
            case Premiere:
                niveauCode = "PRE";
                break;
            case Terminal:
                niveauCode = "TLE";
                break;
            default:
                throw new IllegalArgumentException("Niveau d'étude inconnu : " + eleve.getNiveauEtude());
        }

        // Récupérer tous les élèves du même niveau
        List<Eleve> eleves = eleveRepository.findByNiveauEtude(eleve.getNiveauEtude());

        // Trier les élèves par ordre alphabétique (nom puis prénom)
        eleves.sort(Comparator.comparing(Eleve::getEleveNom)
                .thenComparing(Eleve::getElevePrenom));

        // Déterminer la position actuelle de l'élève dans la liste triée
        int ordre = 1;
        for (Eleve e : eleves) {
            if (e.getEleveNom().compareToIgnoreCase(eleve.getEleveNom()) > 0) {
                break;
            }
            ordre++;
        }

        // Construire le matricule
        return "MAT_" + niveauCode + "_" + ordre;
    }

    public Eleve updatEleve(String matricule, Eleve updatedEleve) {
        // Vérifier si l'élève avec le matricule donné existe
        Optional<Eleve> existingEleveOpt = eleveRepository.findByEleveMatricule(matricule);

        if (!existingEleveOpt.isPresent()) {
            throw new RuntimeException("Aucun élève trouvé avec le matricule : " + matricule);
        }

        Eleve existingEleve = existingEleveOpt.get();

        // Mise à jour des informations
        existingEleve.setEleveNom(updatedEleve.getEleveNom());
        existingEleve.setElevePrenom(updatedEleve.getElevePrenom());
        existingEleve.setNiveauEtude(updatedEleve.getNiveauEtude());

        // Générer un nouveau matricule à chaque mise à jour
        String newMatricule = generateMatricule(existingEleve);
        System.out.println("Nouveau matricule généré : " + newMatricule);

        existingEleve.setEleveMatricule(newMatricule);

        // Sauvegarde des modifications
        return eleveRepository.save(existingEleve);
    }


    /////////////GET ETUDIANT ALL ///////////////////////////
    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    /////////////GET ETUDIANT BY NIVEU ETUDE ///////////////////////////
    public List<Eleve> getAllByNiveauEtude(NiveauEtude niveauEtude) {
        return eleveRepository.findByNiveauEtude(niveauEtude);
    }

    /////////////GET ETUDIANT BY MATRICULE ///////////////////////////
    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec l'ID : " + id));
    }

}
