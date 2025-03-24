package com.eschoolback.eschool.Controller;

import com.eschoolback.eschool.Dto.CoefficientRequest;
import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.service.CoefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coef")
@CrossOrigin(origins = "http://localhost:4200")
public class CoefController {
    @Autowired
    private CoefService coefService;


    // Endpoint pour enregistrer plusieurs coefficients
    @PostMapping
    public ResponseEntity<List<Coefficient>> saveAllCoefficients(@RequestBody CoefficientRequest request) {
        List<Coefficient> savedCoefficients = coefService.saveAllCoefficients(request);
        return ResponseEntity.ok(savedCoefficients);
    }

    // Endpoint pour récupérer tous les coefficients enregistrés
    @GetMapping
    public ResponseEntity<List<Coefficient>> getAllCoefficients() {
        return ResponseEntity.ok(coefService.getAllCoefficients());
    }


}
