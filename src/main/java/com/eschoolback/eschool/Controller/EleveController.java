package com.eschoolback.eschool.Controller;


import com.eschoolback.eschool.Dto.StatistiquesDTO;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import com.eschoolback.eschool.service.EleveService;
import com.eschoolback.eschool.service.ScolariteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;


    @Autowired
    private ScolariteService scolariteService;

    @GetMapping("/scolarite")
    public List<Scolarite> getAllScolarites() {
        return scolariteService.getAll();
    }

    @PostMapping("/scolarite")
    public Scolarite createScolarite(@RequestBody Scolarite scolarite) {
        return scolariteService.save(scolarite);
    }

    @GetMapping("/niveau")
    public List<NiveauEtude> getAllNiveauxEtude() {
        return Arrays.asList(NiveauEtude.values());
    }

    @GetMapping("/specialite")
    public List<String> getSpecialites() {
        // Retourner la liste des spécialités directement
        return Arrays.stream(Specialite.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


    @PostMapping("/save")
    public Eleve saveEleve(@RequestBody Eleve eleve) {
        return eleveService.saveEleve(eleve);
    }

    // PUT: Mettre à jour un élève par son matricule
    @PutMapping("/{matricule}")
    public ResponseEntity<Eleve> updateEleveByMatricule(
            @PathVariable String matricule,
            @RequestBody Eleve updatedEleve) {
        try {
            Eleve updated = eleveService.updateEleveByMatricule(matricule, updatedEleve);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /////////////aide au put au fontend  ///////////////////////////
    @GetMapping("/getIdByMatricule/{matricule}")
    public ResponseEntity<Long> getIdByMatricule(@PathVariable String matricule) {
        Long id = eleveService.getIdByMatricule(matricule);
        if (id != null) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    /////////////GET ETUDIANT   ALL  ///////////////////////////
    @GetMapping
    public ResponseEntity<List<Eleve>> getAllEleves() {
        List<Eleve> eleves = eleveService.getAllEleves();
        return new ResponseEntity<>(eleves, HttpStatus.OK);
    }


    /////////////GET ETUDIANT BY NIVEU ETUDE ///////////////////////////
    @GetMapping("/{niveauEtude}")
    public ResponseEntity<List<Eleve>> getAllByNiveauEtude(@PathVariable NiveauEtude niveauEtude) {
        List<Eleve> eleves = eleveService.getAllByNiveauEtude(niveauEtude);
        if (eleves.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Pas d'élèves pour ce niveau
        }
        return new ResponseEntity<>(eleves, HttpStatus.OK);
    }

    /////////////GET BY NIVEU ETUDE et SPECIALITE ///////////////////////////
    @GetMapping("/niv/{niveauEtude}/spec/{specialite}")
    public List<Eleve> getElevesByNiveauEtSpecialite(
            @PathVariable NiveauEtude niveauEtude,  // Utilisation de l'énumération NiveauEtude
            @PathVariable Specialite specialite) {  // Utilisation de l'énumération Specialite
        return eleveService.getElevesByNiveauEtSpecialite(niveauEtude, specialite);
    }

    /////////////GET ETUDIANT BY MATRICULE ///////////////////////////
    @GetMapping("/id/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable Long id) {
        try {
            Eleve eleve = eleveService.getEleveById(id);
            return ResponseEntity.ok(eleve);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @GetMapping("/getby")
    public Optional<Eleve> getEleveByNomAndPrenom(@RequestParam String nom, @RequestParam String prenom) {
        return eleveService.getEleveByNomAndPrenom(nom, prenom);
    }

    @GetMapping("/stat")
    public List<StatistiquesDTO> getStatistiques() {
        return eleveService.getStatistiquesParNiveau();
    }




}
