//package com.eschoolback.eschool.Controller;
//
//
//import com.eschoolback.eschool.Dto.EleveDto;
//import com.eschoolback.eschool.Dto.StatistiquesDTO;
//import com.eschoolback.eschool.Entity.Eleve;
//import com.eschoolback.eschool.Entity.Scolarite;
//import com.eschoolback.eschool.enums.NiveauEtude;
//import com.eschoolback.eschool.enums.Specialite;
//import com.eschoolback.eschool.service.ElService;
//import com.eschoolback.eschool.service.ScolariteService;
//import jakarta.persistence.EntityExistsException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/eleve")
//@CrossOrigin(origins = "http://localhost:4200")
//public class ElController {
//
//    @Autowired
//    private ElService eleveService;
//
//
//    @Autowired
//    private ScolariteService scolariteService;
//
//    @GetMapping("/scolarite")
//    public List<Scolarite> getAllScolarites() {
//        return scolariteService.getAll();
//    }
//
//    @PostMapping("/scolarite")
//    public Scolarite createScolarite(@RequestBody Scolarite scolarite) {
//        return scolariteService.save(scolarite);
//    }
//
//    @GetMapping("/niveau")
//    public List<NiveauEtude> getAllNiveauxEtude() {
//        return Arrays.asList(NiveauEtude.values());
//    }
//
//    @GetMapping("/specialite")
//    public List<String> getSpecialites() {
//        // Retourner la liste des spécialités directement
//        return Arrays.stream(Specialite.values())
//                .map(Enum::name)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/getby")
//    public Optional<Eleve> getEleveByNomAndPrenom(@RequestParam String nom, @RequestParam String prenom) {
//        return eleveService.getEleveByNomAndPrenom(nom, prenom);
//    }
//
//    @GetMapping("/stat")
//    public List<StatistiquesDTO> getStatistiques() {
//        return eleveService.getStatistiquesParNiveau();
//    }
//
//
//
//    /*---------------------------------------------------------------------------------------------*/
//    /*---------------------------------------------------------------------------------------------*/
//    /*---------------------------------------------------------------------------------------------*/
//
//    @PostMapping("/save")
//    public ResponseEntity<EleveDto> ajouterEleve(@RequestBody Eleve eleve) {
//        try {
//            EleveDto savedEleve = eleveService.saveEleve(eleve);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedEleve);
//        } catch (EntityExistsException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//
//
//
//    // PUT: Mettre à jour un élève par son matricule
//    @PutMapping("/{matricule}")
//    public ResponseEntity<Eleve> updateEleveByMatricule(
//            @PathVariable String matricule,
//            @RequestBody Eleve updatedEleve) {
//        try {
//            Eleve updated = eleveService.updateEleveByMatricule(matricule, updatedEleve);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    /////////////aide au put au fontend  ///////////////////////////
//    @GetMapping("/getIdByMatricule/{matricule}")
//    public ResponseEntity<Long> getIdByMatricule(@PathVariable String matricule) {
//        Long id = eleveService.getIdByMatricule(matricule);
//        if (id != null) {
//            return ResponseEntity.ok(id);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//
//
//    /////////////GET ETUDIANT   ALL  ///////////////////////////
//    @GetMapping
//    public ResponseEntity<List<Eleve>> getAllEleves() {
//        List<Eleve> eleves = eleveService.getAllEleves();
//        return new ResponseEntity<>(eleves, HttpStatus.OK);
//    }
//
//
//    /////////////GET ETUDIANT BY NIVEU ETUDE ///////////////////////////
//    @GetMapping("/{niveauEtude}")
//    public ResponseEntity<List<Eleve>> getAllByNiveauEtude(@PathVariable NiveauEtude niveauEtude) {
//        List<Eleve> eleves = eleveService.getAllByNiveauEtude(niveauEtude);
//        if (eleves.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Pas d'élèves pour ce niveau
//        }
//        return new ResponseEntity<>(eleves, HttpStatus.OK);
//    }
//
//    /////////////GET BY NIVEU ETUDE et SPECIALITE ///////////////////////////
//    @GetMapping("/niv/{niveauEtude}/spec/{specialite}")
//    public ResponseEntity<List<EleveDto>> getElevesByNiveauEtSpecialite(
//            @PathVariable NiveauEtude niveauEtude,
//            @PathVariable Specialite specialite) {
//        List<EleveDto> eleves = eleveService.getElevesByNiveauEtSpecialiteSansPaiement(niveauEtude, specialite);
//        return ResponseEntity.ok(eleves);
//    }
//
//    /////////////GET ETUDIANT BY MATRICULE ///////////////////////////
//    @GetMapping("/id/{id}")
//    public ResponseEntity<Eleve> getEleveById(@PathVariable Long id) {
//        try {
//            Eleve eleve = eleveService.getEleveById(id);
//            return ResponseEntity.ok(eleve);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//
//
//
//
//
//}
