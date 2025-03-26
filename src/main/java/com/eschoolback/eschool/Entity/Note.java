package com.eschoolback.eschool.Entity;

import com.eschoolback.eschool.enums.Observation;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interro1")
    private Float int1;

    @Column(name = "interro2")
    private Float int2;

    private Double dev;
    private Double comp;
    private Double moyClasse;
    private Double moyTotal;
    private Double moyCoef;
    @Enumerated(EnumType.STRING)
    private Observation observation;



    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;


    @ManyToOne
    @JoinColumn(name = "coefficient_id", nullable = false)
    private Coefficient coefficient; // Relation avec le coefficient


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getInt1() {
        return int1;
    }

    public void setInt1(Float int1) {
        this.int1 = int1;
    }

    public Float getInt2() {
        return int2;
    }

    public void setInt2(Float int2) {
        this.int2 = int2;
    }

    public Double getDev() {
        return dev;
    }

    public void setDev(Double dev) {
        this.dev = dev;
    }

    public Double getComp() {
        return comp;
    }

    public void setComp(Double comp) {
        this.comp = comp;
    }

    public Double getMoyClasse() {
        return moyClasse;
    }

    public void setMoyClasse(Double moyClasse) {
        this.moyClasse = moyClasse;
    }

    public Double getMoyTotal() {
        return moyTotal;
    }

    public void setMoyTotal(Double moyTotal) {
        this.moyTotal = moyTotal;
    }

    public Double getMoyCoef() {
        return moyCoef;
    }

    public void setMoyCoef(Double moyCoef) {
        this.moyCoef = moyCoef;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }


    public Coefficient getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Coefficient coefficient) {
        this.coefficient = coefficient;
    }
}
