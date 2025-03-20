package com.eschoolback.eschool.Controller;

import com.eschoolback.eschool.Dto.EleveDto;
import com.eschoolback.eschool.Dto.StatistiquesDTO;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.service.EleveService;
import com.eschoolback.eschool.service.ScolariteService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eleve")
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/stat")
    public List<StatistiquesDTO> getStatistiques() {
        return eleveService.getStatistiquesParNiveau();
    }

    @PostMapping("/save")
    public ResponseEntity<EleveDto> ajouterEleve(@RequestBody Eleve eleve) {
        try {
            EleveDto savedEleve = eleveService.saveEleve(eleve);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEleve);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

 //   @PutMapping("/{matricule}")
//    public ResponseEntity<EleveDto> updateEleveByMatricule(
//            @PathVariable String matricule,
//            @RequestBody EleveDto eleveDto) {
//
//        EleveDto updatedEleve = eleveService.updateEleveByMatricule(matricule, eleveDto);
//        return ResponseEntity.ok(updatedEleve);
//    }

    @GetMapping("/getByMatricule/{matricule}")
    public ResponseEntity<EleveDto> getEleveByMatricule(@PathVariable String matricule) {
        EleveDto eleveDto = eleveService.getEleveByMatricule(matricule);
        return ResponseEntity.ok(eleveDto);
    }



    @GetMapping("/{niveauEtude}")
    public ResponseEntity<List<EleveDto>> getElevesByNiveau(@PathVariable NiveauEtude niveauEtude) {
        List<EleveDto> eleves = eleveService.getElevesByNiveau(niveauEtude);
        return ResponseEntity.ok(eleves);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EleveDto> getEleveById(@PathVariable Long id) {
        EleveDto eleveDto = eleveService.getEleveById(id);
        return ResponseEntity.ok(eleveDto);
    }

    @GetMapping("/getby")
    public ResponseEntity<EleveDto> getEleveByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {

        return eleveService.getEleveByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("/niv/{niveauEtude}/spec/{specialite}")
    public ResponseEntity<List<EleveDto>> getElevesByNiveauEtSpecialite(
            @PathVariable NiveauEtude niveauEtude,
            @PathVariable Specialite specialite) {
        List<EleveDto> eleves = eleveService.getElevesByNiveauEtSpecialiteSansPaiement(niveauEtude, specialite);
        return ResponseEntity.ok(eleves);
    }

}
