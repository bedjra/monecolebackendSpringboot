package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Entity.Professeur;
import com.eschoolback.eschool.Entity.Titulaire;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.ProfesseurRepository;
import com.eschoolback.eschool.repository.TitulaireRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TitulaireService {
    private final TitulaireRepository titulaireRepository;
    private final ProfesseurRepository professeurRepository;

    public TitulaireService(TitulaireRepository titulaireRepository, ProfesseurRepository professeurRepository) {
        this.titulaireRepository = titulaireRepository;
        this.professeurRepository = professeurRepository;
    }

    // Enregistrer un professeur
    public Professeur enregistrerProfesseur(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    // Récupérer tous les professeurs
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    // Récupérer uniquement les noms des professeurs
    public List<String> getAllProfesseurNoms() {
        return professeurRepository.findAll()
                .stream()
                .map(Professeur::getNom)
                .collect(Collectors.toList());
    }

    // Récupérer les niveaux d'étude (Enum)
    public List<NiveauEtude> getNiveaux() {
        return List.of(NiveauEtude.values());
    }

    // Récupérer les spécialités (Enum)
    public List<Specialite> getSpecialites() {
        return List.of(Specialite.values());
    }

    public Titulaire saveTitulaire(Titulaire titulaire) {
        return titulaireRepository.save(titulaire);
    }




    // Récupérer la liste des noms des professeurs


    // Assigner un professeur titulaire à un niveau et une spécialité
//    public Titulaire assignerProfesseurTitulaire(String nomProfesseur, NiveauEtude  niveau,    Specialite specialite) {
//        // Vérifier si le professeur existe
//        Professeur professeur = professeurRepository.findByNom(nomProfesseur)
//                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
//
//        // Créer un nouveau titulaire
//        Titulaire titulaire = new Titulaire();
//        titulaire.setProfesseurTitulaire(professeur);
//        titulaire.setNiveau(niveau);
//        titulaire.setSpecialite(specialite);
//
//        // Enregistrer dans la base de données
//        return titulaireRepository.save(titulaire);
//    }
}
