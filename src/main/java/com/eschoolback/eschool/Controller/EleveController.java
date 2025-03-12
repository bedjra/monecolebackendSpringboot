package com.eschoolback.eschool.Controller;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import com.eschoolback.eschool.service.EleveService;
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
    private EleveRepository eleveRepository;

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
    public ResponseEntity<Eleve> updateEleve(@PathVariable String matricule, @Valid @RequestBody Eleve updatedEleve) {
        try {
            // Appel au service pour mettre à jour l'élève
            Eleve updatedStudent = eleveService.updatEleve(matricule, updatedEleve);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si l'élève avec ce matricule n'est pas trouvé, retourner une erreur 404
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
}
