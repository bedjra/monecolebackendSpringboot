package com.eschoolback.eschool.service;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // Générer le matricule
        String matricule = generateMatricule(eleve);
        eleve.setEleveMatricule(matricule);

        if (eleve.getEleveNationalite() == null) {
            eleve.setEleveNationalite("Non spécifiée");
        }

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

}
