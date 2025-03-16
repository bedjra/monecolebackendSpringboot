package com.eschoolback.eschool.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;
    private long resteEcolage;
    private long montantDejaPaye;
    private long montantActuel;


    @ManyToOne
    @JoinColumn(name = "id")
    private Eleve eleve;

}