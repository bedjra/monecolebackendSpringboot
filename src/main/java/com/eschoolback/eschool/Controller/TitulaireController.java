package com.eschoolback.eschool.Controller;



import com.eschoolback.eschool.Dto.TitulaireRequest;
import com.eschoolback.eschool.Entity.Professeur;
import com.eschoolback.eschool.Entity.Titulaire;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.service.TitulaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/titulaire")
public class TitulaireController {

    private final TitulaireService titulaireService;

    public TitulaireController(TitulaireService titulaireService) {
        this.titulaireService = titulaireService;
    }

    @Autowired
    private TitulaireService professeurService;

    // Récupérer la liste des noms des professeurs
    @GetMapping("/all")
    public List<String> getAllProfesseurNoms() {
        return titulaireService.getAllProfesseurNoms();
    }


    // Récupérer les niveaux d'étude
    @GetMapping("/niveaux")
    public List<NiveauEtude> getNiveaux() {
        return Arrays.asList(NiveauEtude.values());
    }

    // Récupérer les spécialités
    @GetMapping("/specialites")
    public List<Specialite> getSpecialites() {
        return Arrays.asList(Specialite.values());
    }


    @PostMapping
    public ResponseEntity<Titulaire> saveTitulaire(@RequestBody TitulaireRequest titulaireRequest) {
        Titulaire titulaire = new Titulaire();
        titulaire.setNom(titulaireRequest.getProfesseur()); // Correspond au champ professeur dans le frontend
        titulaire.setSpecialite(titulaireRequest.getSpecialite());
        titulaire.setNiveau(titulaireRequest.getNiveau());

        Titulaire savedTitulaire = titulaireService.saveTitulaire(titulaire);
        return ResponseEntity.ok(savedTitulaire);
    }



    // Assigner un professeur titulaire
//    @PostMapping
//    public ResponseEntity<Titulaire> assignerTitulaire(@RequestBody TitulaireRequest request) {
//        try {
//            System.out.println("JSON reçu : " + request.getNiveau() + ", " + request.getSpecialite() + ", " + request.getProfesseurnom());
//
//            // Convertir les chaînes en enums
//            NiveauEtude niveau = Arrays.stream(NiveauEtude.values())
//                    .filter(n -> n.name().equalsIgnoreCase(request.getNiveau()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("Niveau invalide : " + request.getNiveau()));
//
//            Specialite specialite = Arrays.stream(Specialite.values())
//                    .filter(s -> s.name().equalsIgnoreCase(request.getSpecialite()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("Spécialité invalide : " + request.getSpecialite()));
//
//            // Appeler le service
//            Titulaire nouveauTitulaire = titulaireService.assignerProfesseurTitulaire(
//                    request.getProfesseurnom(),
//                    niveau,
//                    specialite
//            );
//
//            return ResponseEntity.ok(nouveauTitulaire);
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

}
