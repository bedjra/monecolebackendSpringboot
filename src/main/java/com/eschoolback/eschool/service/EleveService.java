package com.eschoolback.eschool.service;


import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    public Eleve saveEleve(Eleve eleve, NiveauEtude niveauEtude) {
        eleve.setNiveauEtude(niveauEtude);
        return eleveRepository.save(eleve);
    }
}
