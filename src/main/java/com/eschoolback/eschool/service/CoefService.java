package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Dto.CoefficientRequest;
import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.CoefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoefService {


    private final CoefRepository coefficientRepository;

    public CoefService(CoefRepository coefficientRepository) {
        this.coefficientRepository = coefficientRepository;
    }

    // Enregistrer plusieurs coefficients en une seule requête
    public List<Coefficient> saveAllCoefficients(CoefficientRequest request) {
        List<Coefficient> coefficients = request.getCoefficients().stream().map(mc ->
                new Coefficient(request.getNiveauEtude(), request.getSpecialite(), mc.getMatiere(), mc.getCoefficient())
        ).collect(Collectors.toList());

        return coefficientRepository.saveAll(coefficients);
    }

    // Récupérer tous les coefficients
    public List<Coefficient> getAllCoefficients() {
        return coefficientRepository.findAll();
    }



}
