package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    List<Eleve> findByNiveauEtude(NiveauEtude niveauEtude);
    Optional<Eleve> findByEleveNomAndElevePrenom(String eleveNom, String elevePrenom);
    Optional<Eleve> findByEleveMatricule(String matricule);
    long countByNiveauEtudeAndSpecialite(NiveauEtude niveauEtude, Specialite specialite);

//    @Query("SELECT e.niveauEtude, " +
//            "COUNT(e) AS nbTotalEleves, " +
//            "SUM(CASE WHEN e.eleveSexe = 'F' THEN 1 ELSE 0 END) AS nbFilles, " +
//            "SUM(CASE WHEN e.eleveSexe = 'M' THEN 1 ELSE 0 END) AS nbGarcons " +
//            "FROM Eleve e " +
//            "GROUP BY e.niveauEtude")
//    List<Object[]> getStatistiquesParNiveau();

    List<Eleve> findByNiveauEtudeAndSpecialite(NiveauEtude niveauEtude, Specialite specialite);


    @Query("SELECT e.niveauEtude, " +
            " COUNT(e) AS nbTotalEleves, " +
            " SUM(CASE WHEN e.eleveSexe = 'F' THEN 1 ELSE 0 END) AS nbFilles, " +
            " SUM(CASE WHEN e.eleveSexe = 'M' THEN 1 ELSE 0 END) AS nbGarcons " +
            " FROM Eleve e GROUP BY e.niveauEtude")
    List<Object[]> getStatistiquesParNiveau();

}
