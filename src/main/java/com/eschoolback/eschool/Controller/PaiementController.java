package com.eschoolback.eschool.Controller;

import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paiement")
public class PaiementController {
    @Autowired
    private PaiementService paiementService;


}
