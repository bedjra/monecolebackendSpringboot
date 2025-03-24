package com.eschoolback.eschool.Controller;

import com.eschoolback.eschool.Dto.MatiereDTO;
import com.eschoolback.eschool.Entity.Professeur;
import com.eschoolback.eschool.enums.Facultative;
import com.eschoolback.eschool.enums.Litteraire;
import com.eschoolback.eschool.enums.Scientifique;
import com.eschoolback.eschool.service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matieres")
@CrossOrigin(origins = "http://localhost:4200")
public class MatiereController {
    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    @GetMapping
    public List<MatiereDTO> getMatieres() {
        return matiereService.getAllMatieres();
    }


    @PostMapping("/professeur")
    public ResponseEntity<Professeur> createProfesseur(@RequestBody Professeur professeur) {
        Professeur savedProfesseur = matiereService.saveProfesseur(professeur);
        return new ResponseEntity<>(savedProfesseur, HttpStatus.CREATED);

    }

    @GetMapping("/professeur")
    public List<Professeur> getAllProfesseurs() {
        return matiereService.getAllProfesseurs();
    }

    @GetMapping("/sans")
    public List<MatiereDTO> getMatieresSansCategories() {
        return matiereService.getAllMatieresSansCategories();
    }
    @GetMapping("/all")
    public List<String> getAllMatieres() {
        List<String> matieres = new ArrayList<>();

        // Ajout des matières littéraires
        for (Litteraire matiere : Litteraire.values()) {
            matieres.add(matiere.getDisplayName());
        }

        // Ajout des matières scientifiques
        for (Scientifique matiere : Scientifique.values()) {
            matieres.add(matiere.getDisplayName());
        }

        // Ajout des matières facultatives
        for (Facultative matiere : Facultative.values()) {
            matieres.add(matiere.getDisplayName());
        }

        return matieres;
    }

    @GetMapping("/professeurs/noms")
    public List<String> getAllProfesseurNoms() {
        return matiereService.getAllProfesseurNoms();
    }


}
