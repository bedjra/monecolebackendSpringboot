package com.eschoolback.eschool.Controller;

import com.eschoolback.eschool.Dto.PaiementDto;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paiement")
@CrossOrigin(origins = "http://localhost:4200")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping("/specialites")
    public ResponseEntity<List<Specialite>> getSpecialitesLieesAuxPaiements() {
        List<Specialite> specialites = paiementService.getAllSpecialitesLieesAuxPaiements();
        return ResponseEntity.ok(specialites);
    }

    @GetMapping("/niveaux")
    public ResponseEntity<List<NiveauEtude>> getNiveauxLiesAuxPaiements() {
        List<NiveauEtude> niveaux = paiementService.getAllNiveauxLiesAuxPaiements();
        return ResponseEntity.ok(niveaux);
    }

    @GetMapping("/niv/{niveau}/spec/{specialite}")
    public ResponseEntity<List<PaiementDto>> getPaiementsByNiveauEtSpecialite(
            @PathVariable NiveauEtude niveau,
            @PathVariable Specialite specialite) {
        List<PaiementDto> paiements = paiementService.getPaiementsByNiveauEtSpecialite(niveau, specialite);
        return ResponseEntity.ok(paiements);
    }

    @PostMapping
    public ResponseEntity<PaiementDto> effectuerPaiement(@RequestBody PaiementDto paiementDto) {
        PaiementDto paiementEffectue = paiementService.effectuerPaiement(paiementDto);
        return ResponseEntity.ok(paiementEffectue);
    }

    @GetMapping("/{matricule}")
    public ResponseEntity<PaiementDto> getPaiementByMatricule(@PathVariable String matricule) {
        try {
            PaiementDto paiementDto = paiementService.getPaiementByMatricule(matricule);
            return ResponseEntity.ok(paiementDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
