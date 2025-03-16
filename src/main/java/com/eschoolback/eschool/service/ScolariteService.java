package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.repository.ScolariteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScolariteService {

    @Autowired
    private ScolariteRepository scolariteRepository;


    public List<Scolarite> getAll() {
        return scolariteRepository.findAll();
    }

    public Scolarite save(Scolarite scolarite) {
        return scolariteRepository.save(scolarite);
    }


}
