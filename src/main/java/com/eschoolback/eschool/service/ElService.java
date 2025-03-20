//package com.eschoolback.eschool.service;
//
//
//import com.eschoolback.eschool.Dto.EleveDto;
//import com.eschoolback.eschool.Dto.StatistiquesDTO;
//import com.eschoolback.eschool.Entity.Eleve;
//import com.eschoolback.eschool.Entity.Paiement;
//import com.eschoolback.eschool.Entity.Scolarite;
//import com.eschoolback.eschool.enums.NiveauEtude;
//import com.eschoolback.eschool.enums.Specialite;
//import com.eschoolback.eschool.repository.EleveRepository;
//import com.eschoolback.eschool.repository.PaiementRepository;
//import com.eschoolback.eschool.repository.ScolariteRepository;
//import jakarta.persistence.EntityExistsException;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ElService {
//
//    @Autowired
//    private EleveRepository eleveRepository;
//
//    @Autowired
//    private ScolariteRepository scolariteRepository;
//
//    @Autowired
//    private PaiementRepository paiementRepository;
//
//
//    private String generateMatricule(Eleve eleve) {
//        // 1️⃣ Mapper le niveau d’étude en abréviation
//        String niveauCode;
//        switch (eleve.getNiveauEtude()) {
//            case Seconde: niveauCode = "SEC"; break;
//            case Premiere: niveauCode = "PRE"; break;
//            case Terminal: niveauCode = "TLE"; break;
//            default: throw new IllegalArgumentException("Niveau inconnu : " + eleve.getNiveauEtude());
//        }
//
//        // 2️⃣ Récupérer le code de la spécialité
//        String specialiteCode = eleve.getSpecialite().name(); // Déjà sous forme de texte (A4, D, G1, G2, G3)
//
//        // 3️⃣ Récupérer le nombre d’élèves existants dans CE niveau et CETTE spécialité
//        long count = eleveRepository.countByNiveauEtudeAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite());
//
//        // 4️⃣ Construire le matricule sous le format "SEC-A4-001"
//        return niveauCode + "-" + specialiteCode + "-" + String.format("%03d", count + 1);
//    }
//
//
//
//    @Transactional
//    public EleveDto saveEleve(Eleve eleve) {
//        // Vérifier si l'élève existe déjà
//        if (eleveRepository.findByEleveNomAndElevePrenom(eleve.getEleveNom(), eleve.getElevePrenom()).isPresent()) {
//            throw new EntityExistsException("Un élève avec le même nom et prénom existe déjà.");
//        }
//
//        // Vérifier la validité des champs requis
//        if (eleve.getSpecialite() == null || eleve.getNiveauEtude() == null) {
//            throw new IllegalArgumentException("La spécialité et le niveau ne peuvent pas être nuls.");
//        }
//
//        // Vérifier l'existence d'une scolarité correspondante
//        Scolarite scolarite = scolariteRepository.findByNiveauAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite())
//                .orElseThrow(() -> new RuntimeException("Aucune scolarité trouvée pour cette spécialité et ce niveau."));
//
//        // Générer le matricule et la date d'inscription
//        eleve.setEleveMatricule(generateMatricule(eleve));
//        eleve.setEleveDateIns(LocalDate.now());
//
//        // Sauvegarde de l'élève
//        Eleve savedEleve = eleveRepository.save(eleve);
//
//        // Création et sauvegarde du paiement initial
//        Paiement paiement = new Paiement();
//        paiement.setEleve(savedEleve);
//        paiement.setScolarite(scolarite);
//        paiement.setNiveau(savedEleve.getNiveauEtude());
//        paiement.setSpecialite(savedEleve.getSpecialite());
//        paiement.setMontantDejaPaye(Double.valueOf(0));
//        paiement.setResteEcolage(scolarite.getMontant());
//        paiement.setMontantActuel((double) 0);
//        paiement.setDatePaiement(LocalDate.now());
//
//        // Sauvegarde du paiement
//        paiementRepository.save(paiement);
//
//        // Retourner un EleveDto
//        return convertToDto(savedEleve);
//    }
//
//    private EleveDto convertToDto(Eleve eleve) {
//        return new EleveDto(
//                eleve.getId(),
//                eleve.getEleveMatricule(),
//                eleve.getEleveNom(),
//                eleve.getElevePrenom(),
//                eleve.getEleveAdresse(),
//                eleve.getEleveDateNaiss(),
//                eleve.getEleveLieuNais(),
//                eleve.getEleveSexe(),
//                eleve.getEleveEtatProvenance(),
//                eleve.getEleveDateIns(),
//                eleve.getTuteurNom(),
//                eleve.getTuteurPrenom(),
//                eleve.getTuteurProfession(),
//                eleve.getTuteurAdresse(),
//                eleve.getTuteurTelDom(),
//                eleve.getTuteurCel(),
//                eleve.getNiveauEtude(),
//                eleve.getSpecialite()
//        );
//    }
//
//
//    public Eleve updateEleveByMatricule(String matricule, Eleve updatedEleve) {
//        Optional<Eleve> existingEleveOpt = eleveRepository.findByEleveMatricule(matricule);
//
//        if (existingEleveOpt.isEmpty()) {
//            throw new RuntimeException("Élève introuvable avec le matricule : " + matricule);
//        }
//
//        Eleve eleve = existingEleveOpt.get();
//
//        // Vérifier si le niveau ou la spécialité a changé
//        boolean niveauChanged = !eleve.getNiveauEtude().equals(updatedEleve.getNiveauEtude());
//        boolean specialiteChanged = !eleve.getSpecialite().equals(updatedEleve.getSpecialite());
//
//        // Mettre à jour les informations de l'élève (sauf la date d'inscription)
//        eleve.setEleveNom(updatedEleve.getEleveNom());
//        eleve.setElevePrenom(updatedEleve.getElevePrenom());
//        eleve.setEleveAdresse(updatedEleve.getEleveAdresse());
//        eleve.setEleveSexe(updatedEleve.getEleveSexe());
//        eleve.setEleveDateNaiss(updatedEleve.getEleveDateNaiss());
//        eleve.setEleveEtatProvenance(updatedEleve.getEleveEtatProvenance());
//        eleve.setEleveLieuNais(updatedEleve.getEleveLieuNais());
//
//        // Ne pas modifier la date d'inscription
//        // eleve.setEleveDateIns(eleve.getEleveDateIns());  // Pas nécessaire, la valeur est conservée.
//
//        // Mise à jour du niveau et de la spécialité
//        eleve.setNiveauEtude(updatedEleve.getNiveauEtude());
//        eleve.setSpecialite(updatedEleve.getSpecialite());
//
//        // Si le niveau ou la spécialité a changé, mettre à jour la scolarité et recalculer le paiement
//        if (niveauChanged || specialiteChanged) {
//            // Trouver la nouvelle scolarité
//            Optional<Scolarite> scolariteOpt = scolariteRepository.findByNiveauAndSpecialite(
//                    updatedEleve.getNiveauEtude(), updatedEleve.getSpecialite()
//            );
//
//            if (scolariteOpt.isEmpty()) {
//                throw new RuntimeException("Aucune scolarité trouvée pour ce niveau et cette spécialité.");
//            }
//
//            Scolarite scolarite = scolariteOpt.get();
//
//            // Régénérer le matricule
//            String newMatricule = generateMatricule(eleve);
//            eleve.setEleveMatricule(newMatricule);
//
//            // Mettre à jour le paiement
//            Optional<Paiement> paiementOpt = paiementRepository.findByEleve(eleve);
//            if (paiementOpt.isPresent()) {
//                Paiement paiement = paiementOpt.get();
//                paiement.setScolarite(scolarite);
//                paiement.setNiveau(updatedEleve.getNiveauEtude());
//                paiement.setSpecialite(updatedEleve.getSpecialite());
//                paiement.setResteEcolage(scolarite.getMontant() - paiement.getMontantDejaPaye());
//
//                paiementRepository.save(paiement);
//            } else {
//                // Créer un nouveau paiement si aucun n'existe
//                Paiement paiement = new Paiement();
//                paiement.setEleve(eleve);
//                paiement.setScolarite(scolarite);
//                paiement.setNiveau(updatedEleve.getNiveauEtude());
//                paiement.setSpecialite(updatedEleve.getSpecialite());
//                paiement.setMontantDejaPaye(0.0);
//                paiement.setResteEcolage(scolarite.getMontant());
//                paiement.setMontantActuel(0.0);
//                paiement.setDatePaiement(LocalDate.now());
//
//                paiementRepository.save(paiement);
//            }
//        }
//
//        // Sauvegarde de l'élève mis à jour
//        return eleveRepository.save(eleve);
//    }
//
//    /////////////aide au put au fontend  ///////////////////////////
//    public Long getIdByMatricule(String matricule) {
//        Optional<Eleve> eleve = eleveRepository.findByEleveMatricule(matricule);
//        return (eleve.isPresent()) ? eleve.get().getId() : null;  // 👈 Maintenant, c'est un Long
//    }
//
//    /////////////GET ETUDIANT ALL ///////////////////////////
//    public List<Eleve> getAllEleves() {
//        return eleveRepository.findAll();
//    }
//
//    /////////////GET ETUDIANT BY NIVEU ETUDE ///////////////////////////
//    public List<Eleve> getAllByNiveauEtude(NiveauEtude niveauEtude) {
//        return eleveRepository.findByNiveauEtude(niveauEtude);
//    }
//
//
//
//    /////////////GET BY NIVEU ETUDE et SPECIALITE ///////////////////////////
//    public List<EleveDto> getElevesByNiveauEtSpecialiteSansPaiement(NiveauEtude niveauEtude, Specialite specialite) {
//        List<Eleve> eleves = eleveRepository.findByNiveauEtudeAndSpecialite(niveauEtude, specialite);
//        return eleves.stream().map(this::convertToDtoSansPaiement).collect(Collectors.toList());
//    }
//
//    private EleveDto convertToDtoSansPaiement(Eleve eleve) {
//        return new EleveDto(
//                eleve.getId(),
//                eleve.getEleveMatricule(),
//                eleve.getEleveNom(),
//                eleve.getElevePrenom(),
//                eleve.getEleveAdresse(),
//                eleve.getEleveDateNaiss(),
//                eleve.getEleveLieuNais(),
//                eleve.getEleveSexe(),
//                eleve.getEleveEtatProvenance(),
//                eleve.getEleveDateIns(),
//                eleve.getTuteurNom(),
//                eleve.getTuteurPrenom(),
//                eleve.getTuteurProfession(),
//                eleve.getTuteurAdresse(),
//                eleve.getTuteurTelDom(),
//                eleve.getTuteurCel(),
//                eleve.getNiveauEtude(),
//                eleve.getSpecialite()
//                // On n'inclut pas les paiements ici
//        );
//    }
//
//
//
//    /////////////GET ETUDIANT BY MATRICULE ///////////////////////////
//    public Eleve getEleveById(Long id) {
//        return eleveRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Aucun élève trouvé avec l'ID : " + id));
//    }
//
//
//    public Optional<Eleve> getEleveByNomAndPrenom(String nom, String prenom) {
//        return eleveRepository.findByEleveNomAndElevePrenom(nom, prenom);
//    }
//
//
//
//
//
//
//
//
//    public List<StatistiquesDTO> getStatistiquesParNiveau() {
//        // Récupérer les résultats de la requête
//        List<Object[]> result = eleveRepository.getStatistiquesParNiveau();
//        List<StatistiquesDTO> statistiques = new ArrayList<>();
//
//        // Transformation des résultats en DTO
//        for (Object[] row : result) {
//            NiveauEtude niveauEtude = (NiveauEtude) row[0];
//            Long nbTotalEleves = (Long) row[1];
//            Long nbFilles = (Long) row[2];
//            Long nbGarcons = (Long) row[3];
//            statistiques.add(new StatistiquesDTO(niveauEtude, nbTotalEleves, nbFilles, nbGarcons));
//        }
//
//        return statistiques;
//    }
//
//
//}