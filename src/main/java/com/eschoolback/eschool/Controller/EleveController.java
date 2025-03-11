package com.eschoolback.eschool.Controller;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @GetMapping("/niveau")
    public List<NiveauEtude> getAllNiveauxEtude() {
        return Arrays.asList(NiveauEtude.values());
    }

    @PostMapping("/save")
    public Eleve saveEleve(@RequestBody Eleve eleve) {
        return eleveService.saveEleve(eleve);
    }



}
