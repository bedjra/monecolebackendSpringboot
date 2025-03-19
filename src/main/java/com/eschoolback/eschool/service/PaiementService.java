package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Paiement;
import com.eschoolback.eschool.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;



}
