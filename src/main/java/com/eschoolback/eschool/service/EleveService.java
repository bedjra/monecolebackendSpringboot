package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Dto.EleveDto;
import com.eschoolback.eschool.Dto.StatistiquesDTO;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Paiement;
import com.eschoolback.eschool.Entity.Scolarite;
import com.eschoolback.eschool.enums.NiveauEtude;
import com.eschoolback.eschool.enums.Specialite;
import com.eschoolback.eschool.repository.EleveRepository;
import com.eschoolback.eschool.repository.PaiementRepository;
import com.eschoolback.eschool.repository.ScolariteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private ScolariteRepository scolariteRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    public List<StatistiquesDTO> getStatistiquesParNiveau() {
        // Récupérer les résultats de la requête
        List<Object[]> result = eleveRepository.getStatistiquesParNiveau();
        List<StatistiquesDTO> statistiques = new ArrayList<>();

        // Transformation des résultats en DTO
        for (Object[] row : result) {
            NiveauEtude niveauEtude = (NiveauEtude) row[0];
            Long nbTotalEleves = (Long) row[1];
            Long nbFilles = (Long) row[2];
            Long nbGarcons = (Long) row[3];
            statistiques.add(new StatistiquesDTO(niveauEtude, nbTotalEleves, nbFilles, nbGarcons));
        }

        return statistiques;
    }

    private String generateMatricule(Eleve eleve) {
        // 1️⃣ Mapper le niveau d’étude en abréviation
        String niveauCode;
        switch (eleve.getNiveauEtude()) {
            case Seconde: niveauCode = "SEC"; break;
            case Premiere: niveauCode = "PRE"; break;
            case Terminal: niveauCode = "TLE"; break;
            default: throw new IllegalArgumentException("Niveau inconnu : " + eleve.getNiveauEtude());
        }

        // 2️⃣ Récupérer le code de la spécialité
        String specialiteCode = eleve.getSpecialite().name();

        // 3️⃣ Récupérer le nombre d’élèves existants dans CE niveau et CETTE spécialité
        long count = eleveRepository.countByNiveauEtudeAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite());

        // 4️⃣ Construire le matricule sous le format "SEC-A4-001"
        return niveauCode + "-" + specialiteCode + "-" + String.format("%03d", count + 1);
    }


    private EleveDto convertToDto(Eleve eleve) {
        return new EleveDto(
                eleve.getId(),
                eleve.getEleveMatricule(),
                eleve.getEleveNom(),
                eleve.getElevePrenom(),
                eleve.getEleveAdresse(),
                eleve.getEleveDateNaiss(),
                eleve.getEleveLieuNais(),
                eleve.getEleveSexe(),
                eleve.getEleveEtatProvenance(),
                eleve.getEleveDateIns(),
                eleve.getTuteurNom(),
                eleve.getTuteurPrenom(),
                eleve.getTuteurProfession(),
                eleve.getTuteurAdresse(),
                eleve.getTuteurTelDom(),
                eleve.getTuteurCel(),
                eleve.getNiveauEtude(),
                eleve.getSpecialite()
        );
    }

    public EleveDto saveEleve(Eleve eleve) {
        // Vérifier si l'élève existe déjà
        if (eleveRepository.findByEleveNomAndElevePrenom(eleve.getEleveNom(), eleve.getElevePrenom()).isPresent()) {
            throw new EntityExistsException("Un élève avec le même nom et prénom existe déjà.");
        }

        // Vérifier la validité des champs requis
        if (eleve.getSpecialite() == null || eleve.getNiveauEtude() == null) {
            throw new IllegalArgumentException("La spécialité et le niveau ne peuvent pas être nuls.");
        }

        // Vérifier l'existence d'une scolarité correspondante
        Scolarite scolarite = scolariteRepository.findByNiveauAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite())
                .orElseThrow(() -> new RuntimeException("Aucune scolarité trouvée pour cette spécialité et ce niveau."));

        // Générer le matricule et la date d'inscription
        eleve.setEleveMatricule(generateMatricule(eleve));
        eleve.setEleveDateIns(LocalDate.now());

        // Sauvegarde de l'élève
        Eleve savedEleve = eleveRepository.save(eleve);

        // Création et sauvegarde du paiement initial
        Paiement paiement = new Paiement();
        paiement.setEleve(savedEleve);
        paiement.setScolarite(scolarite);
        paiement.setNiveau(savedEleve.getNiveauEtude());
        paiement.setSpecialite(savedEleve.getSpecialite());
        paiement.setMontantDejaPaye(Double.valueOf(0));
        paiement.setResteEcolage(scolarite.getMontant());
        paiement.setMontantActuel((double) 0);
        paiement.setDatePaiement(LocalDate.now());

        // Sauvegarde du paiement
        paiementRepository.save(paiement);

        // Retourner un EleveDto
        return convertToDto(savedEleve);
    }

//    public EleveDto updateEleveByMatricule(String matricule, EleveDto updatedEleveDto) {
//        // Vérifier si l'élève existe
//        Eleve existingEleve = eleveRepository.findByEleveMatricule(matricule)
//                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec le matricule : " + matricule));
//
//        // Vérifier si un autre élève avec le même nom et prénom existe déjà
//        Optional<Eleve> existingWithSameName = eleveRepository.findByEleveNomAndElevePrenom(
//                updatedEleveDto.getEleveNom(), updatedEleveDto.getElevePrenom());
//
//        if (existingWithSameName.isPresent() && !existingWithSameName.get().getEleveMatricule().equals(matricule)) {
//            throw new EntityExistsException("Un autre élève avec ce nom et prénom existe déjà.");
//        }
//
//        // Vérifier la validité des champs obligatoires
//        if (updatedEleveDto.getSpecialite() == null || updatedEleveDto.getNiveauEtude() == null) {
//            throw new IllegalArgumentException("La spécialité et le niveau ne peuvent pas être nuls.");
//        }
//
//        // Vérifier l'existence d'une scolarité correspondante
//        Scolarite scolarite = scolariteRepository.findByNiveauAndSpecialite(
//                        updatedEleveDto.getNiveauEtude(), updatedEleveDto.getSpecialite())
//                .orElseThrow(() -> new RuntimeException("Aucune scolarité trouvée pour cette spécialité et ce niveau."));
//
//        // Mise à jour des informations de l'élève
//        existingEleve.setEleveNom(updatedEleveDto.getEleveNom());
//        existingEleve.setElevePrenom(updatedEleveDto.getElevePrenom());
//        existingEleve.setEleveAdresse(updatedEleveDto.getEleveAdresse());
//        existingEleve.setEleveDateNaiss(updatedEleveDto.getEleveDateNaiss());
//        existingEleve.setEleveLieuNais(updatedEleveDto.getEleveLieuNais());
//        existingEleve.setEleveSexe(updatedEleveDto.getEleveSexe());
//        existingEleve.setEleveEtatProvenance(updatedEleveDto.getEleveEtatProvenance());
//        existingEleve.setTuteurNom(updatedEleveDto.getTuteurNom());
//        existingEleve.setTuteurPrenom(updatedEleveDto.getTuteurPrenom());
//        existingEleve.setTuteurProfession(updatedEleveDto.getTuteurProfession());
//        existingEleve.setTuteurAdresse(updatedEleveDto.getTuteurAdresse());
//        existingEleve.setTuteurTelDom(updatedEleveDto.getTuteurTelDom());
//        existingEleve.setTuteurCel(updatedEleveDto.getTuteurCel());
//        existingEleve.setNiveauEtude(updatedEleveDto.getNiveauEtude());
//        existingEleve.setSpecialite(updatedEleveDto.getSpecialite());
//
//        // Sauvegarde de l'élève mis à jour
//        Eleve updatedEleve = eleveRepository.save(existingEleve);
//
//        // Mise à jour du paiement associé
//        Paiement paiement = paiementRepository.findByEleve_Matricule(matricule)
//                .orElseThrow(() -> new RuntimeException("Aucun paiement trouvé pour cet élève."));
//
//        paiement.setNiveau(existingEleve.getNiveauEtude());
//        paiement.setSpecialite(existingEleve.getSpecialite());
//        paiement.setScolarite(scolarite);
//        paiement.setResteEcolage(scolarite.getMontant() - paiement.getMontantDejaPaye());
//
//        // Sauvegarde du paiement mis à jour
//        paiementRepository.save(paiement);
//
//        return convertToDto(updatedEleve);
//    }

    public EleveDto getEleveByMatricule(String matricule) {
        Eleve eleve = eleveRepository.findByEleveMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec le matricule : " + matricule));

        return convertToDto(eleve);
    }


    public List<EleveDto> getElevesByNiveau(NiveauEtude niveauEtude) {
        List<Eleve> eleves = eleveRepository.findByNiveauEtude(niveauEtude);
        return eleves.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EleveDto getEleveById(Long id) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec l'ID : " + id));

        return convertToDto(eleve);
    }

    public Optional<EleveDto> getEleveByNomAndPrenom(String nom, String prenom) {
        return eleveRepository.findByEleveNomAndElevePrenom(nom, prenom)
                .map(this::convertToDto);
    }


    public List<EleveDto> getElevesByNiveauEtSpecialiteSansPaiement(NiveauEtude niveauEtude, Specialite specialite) {
        List<Eleve> eleves = eleveRepository.findByNiveauEtudeAndSpecialite(niveauEtude, specialite);

        // Conversion de la liste d'entités Eleve en liste de DTO EleveDto
        return eleves.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



}
