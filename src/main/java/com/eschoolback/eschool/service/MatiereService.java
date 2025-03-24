package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Dto.MatiereDTO;
import com.eschoolback.eschool.Entity.Professeur;
import com.eschoolback.eschool.enums.Facultative;
import com.eschoolback.eschool.enums.Litteraire;
import com.eschoolback.eschool.enums.Scientifique;
import com.eschoolback.eschool.repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatiereService {
    @Autowired
    private ProfesseurRepository professeurRepository;

    public List<MatiereDTO> getAllMatieres() {
        List<MatiereDTO> matieres = new ArrayList<>();

        // Ajouter les matières littéraires
        for (Litteraire matiere : Litteraire.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), "Littéraire", getProfesseurs(matiere.getDisplayName())));
        }

        // Ajouter les matières scientifiques
        for (Scientifique matiere : Scientifique.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), "Scientifique", getProfesseurs(matiere.getDisplayName())));
        }

        // Ajouter les matières facultatives
        for (Facultative matiere : Facultative.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), "Facultative", getProfesseurs(matiere.getDisplayName())));
        }

        return matieres;
    }



    public Professeur saveProfesseur(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    public List<MatiereDTO> getAllMatieresSansCategories() {
        List<MatiereDTO> matieres = new ArrayList<>();

        // Récupérer toutes les matières littéraires
        for (Litteraire matiere : Litteraire.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), null, getProfesseurs(matiere.getDisplayName())));
        }

        // Récupérer toutes les matières scientifiques
        for (Scientifique matiere : Scientifique.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), null, getProfesseurs(matiere.getDisplayName())));
        }

        // Récupérer toutes les matières facultatives
        for (Facultative matiere : Facultative.values()) {
            matieres.add(new MatiereDTO(matiere.getDisplayName(), null, getProfesseurs(matiere.getDisplayName())));
        }

        return matieres;
    }

    private List<String> getProfesseurs(String matiere) {
        List<Professeur> professeurs = professeurRepository.findByMatiere(matiere);
        List<String> nomsProfesseurs = new ArrayList<>();
        for (Professeur professeur : professeurs) {
            nomsProfesseurs.add(professeur.getNom());
        }
        return nomsProfesseurs;
    }



    public List<String> getAllProfesseurNoms() {
        return professeurRepository.findAll().stream()
                .map(Professeur::getNom)
                .collect(Collectors.toList());
    }

}
