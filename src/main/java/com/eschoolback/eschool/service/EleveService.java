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
import jakarta.persistence.EntityNotFoundException;
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


    public EleveDto updateEleveByMatricule(String matricule, EleveDto updatedEleveDto) {
        // Vérifier si l'élève existe
        Eleve eleve = eleveRepository.findByEleveMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Aucun élève trouvé avec le matricule : " + matricule));

        // Mettre à jour les champs de l'élève
        eleve.setEleveNom(updatedEleveDto.getEleveNom());
        eleve.setElevePrenom(updatedEleveDto.getElevePrenom());
        eleve.setEleveAdresse(updatedEleveDto.getEleveAdresse());
        eleve.setEleveDateNaiss(updatedEleveDto.getEleveDateNaiss());
        eleve.setEleveLieuNais(updatedEleveDto.getEleveLieuNais());
        eleve.setEleveSexe(updatedEleveDto.getEleveSexe());
        eleve.setEleveEtatProvenance(updatedEleveDto.getEleveEtatProvenance());
        eleve.setTuteurNom(updatedEleveDto.getTuteurNom());
        eleve.setTuteurPrenom(updatedEleveDto.getTuteurPrenom());
        eleve.setTuteurProfession(updatedEleveDto.getTuteurProfession());
        eleve.setTuteurAdresse(updatedEleveDto.getTuteurAdresse());
        eleve.setTuteurTelDom(updatedEleveDto.getTuteurTelDom());
        eleve.setTuteurCel(updatedEleveDto.getTuteurCel());

        // Vérifier si le niveau ou la spécialité ont changé
        if (!eleve.getNiveauEtude().equals(updatedEleveDto.getNiveauEtude()) ||
                !eleve.getSpecialite().equals(updatedEleveDto.getSpecialite())) {

            // Vérifier l'existence d'une scolarité correspondante
            Scolarite scolarite = scolariteRepository.findByNiveauAndSpecialite(
                            updatedEleveDto.getNiveauEtude(), updatedEleveDto.getSpecialite())
                    .orElseThrow(() -> new RuntimeException("Aucune scolarité trouvée pour cette spécialité et ce niveau."));

            // Mettre à jour le niveau et la spécialité de l'élève
            eleve.setNiveauEtude(updatedEleveDto.getNiveauEtude());
            eleve.setSpecialite(updatedEleveDto.getSpecialite());

            // Générer un nouveau matricule
            eleve.setEleveMatricule(generateMatricule(eleve));

            // Mettre à jour les informations de paiement
            Paiement paiement = paiementRepository.findByEleve(eleve)
                    .orElseThrow(() -> new RuntimeException("Aucun paiement trouvé pour cet élève."));

            paiement.setNiveau(updatedEleveDto.getNiveauEtude());
            paiement.setSpecialite(updatedEleveDto.getSpecialite());
            paiement.setScolarite(scolarite);
            paiement.setResteEcolage(scolarite.getMontant() - paiement.getMontantDejaPaye());
            paiementRepository.save(paiement);
        }

        // Sauvegarde de l'élève mis à jour
        Eleve updatedEleve = eleveRepository.save(eleve);

        // Retourner un EleveDto mis à jour
        return convertToDto(updatedEleve);
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



        public Eleve updateEleveByMatricule(String matricule, Eleve updatedEleve) {
        Optional<Eleve> existingEleveOpt = eleveRepository.findByEleveMatricule(matricule);

        if (existingEleveOpt.isEmpty()) {
            throw new RuntimeException("Élève introuvable avec le matricule : " + matricule);
        }

        Eleve eleve = existingEleveOpt.get();

        // Vérifier si le niveau ou la spécialité a changé
        boolean niveauChanged = !eleve.getNiveauEtude().equals(updatedEleve.getNiveauEtude());
        boolean specialiteChanged = !eleve.getSpecialite().equals(updatedEleve.getSpecialite());

        // Mettre à jour les informations de l'élève (sauf la date d'inscription)
        eleve.setEleveNom(updatedEleve.getEleveNom());
        eleve.setElevePrenom(updatedEleve.getElevePrenom());
        eleve.setEleveAdresse(updatedEleve.getEleveAdresse());
        eleve.setEleveSexe(updatedEleve.getEleveSexe());
        eleve.setEleveDateNaiss(updatedEleve.getEleveDateNaiss());
        eleve.setEleveEtatProvenance(updatedEleve.getEleveEtatProvenance());
        eleve.setEleveLieuNais(updatedEleve.getEleveLieuNais());

        // Ne pas modifier la date d'inscription
        // eleve.setEleveDateIns(eleve.getEleveDateIns());  // Pas nécessaire, la valeur est conservée.

        // Mise à jour du niveau et de la spécialité
        eleve.setNiveauEtude(updatedEleve.getNiveauEtude());
        eleve.setSpecialite(updatedEleve.getSpecialite());

        // Si le niveau ou la spécialité a changé, mettre à jour la scolarité et recalculer le paiement
        if (niveauChanged || specialiteChanged) {
            // Trouver la nouvelle scolarité
            Optional<Scolarite> scolariteOpt = scolariteRepository.findByNiveauAndSpecialite(
                    updatedEleve.getNiveauEtude(), updatedEleve.getSpecialite()
            );

            if (scolariteOpt.isEmpty()) {
                throw new RuntimeException("Aucune scolarité trouvée pour ce niveau et cette spécialité.");
            }

            Scolarite scolarite = scolariteOpt.get();

            // Régénérer le matricule
            String newMatricule = generateMatricule(eleve);
            eleve.setEleveMatricule(newMatricule);

            // Mettre à jour le paiement
            Optional<Paiement> paiementOpt = paiementRepository.findByEleve(eleve);
            if (paiementOpt.isPresent()) {
                Paiement paiement = paiementOpt.get();
                paiement.setScolarite(scolarite);
                paiement.setNiveau(updatedEleve.getNiveauEtude());
                paiement.setSpecialite(updatedEleve.getSpecialite());
                paiement.setResteEcolage(scolarite.getMontant() - paiement.getMontantDejaPaye());

                paiementRepository.save(paiement);
            } else {
                // Créer un nouveau paiement si aucun n'existe
                Paiement paiement = new Paiement();
                paiement.setEleve(eleve);
                paiement.setScolarite(scolarite);
                paiement.setNiveau(updatedEleve.getNiveauEtude());
                paiement.setSpecialite(updatedEleve.getSpecialite());
                paiement.setMontantDejaPaye(0.0);
                paiement.setResteEcolage(scolarite.getMontant());
                paiement.setMontantActuel(0.0);
                paiement.setDatePaiement(LocalDate.now());

                paiementRepository.save(paiement);
            }
        }

        // Sauvegarde de l'élève mis à jour
        return eleveRepository.save(eleve);
    }


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
