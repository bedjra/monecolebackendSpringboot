package com.eschoolback.eschool.Controller;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @PostMapping("/save")
    public Eleve saveEleve(@RequestBody Eleve eleve, @RequestParam NiveauEtude niveauEtude) {
        return eleveService.saveEleve(eleve, niveauEtude);
    }
}
