package com.eschoolback.eschool.Entity;

import jakarta.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double int1;
    private Double int2;
    private Double dev;
    private Double comp;
    private Double moyClasse;
    private Double moyTotal;
    private Double moyCoef;
    private Integer rang;


    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;


    @ManyToOne
    @JoinColumn(name = "coefficient_id", nullable = false)
    private Coefficient coefficient; // Relation avec le coefficient


}
