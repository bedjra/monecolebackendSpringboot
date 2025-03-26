package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Dto.NoteDto;
import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Note;
import com.eschoolback.eschool.Entity.Professeur;
import com.eschoolback.eschool.enums.Observation;
import com.eschoolback.eschool.repository.CoefRepository;
import com.eschoolback.eschool.repository.EleveRepository;
import com.eschoolback.eschool.repository.NoteRepository;
import com.eschoolback.eschool.repository.ProfesseurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EleveRepository eleveRepository;
    private final ProfesseurRepository professeurRepository;
    private final CoefRepository coefRepository;
    public NoteService(NoteRepository noteRepository, EleveRepository eleveRepository, CoefRepository coefRepository, ProfesseurRepository professeurRepository) {
        this.noteRepository = noteRepository;
        this.eleveRepository = eleveRepository;
        this.coefRepository = coefRepository;
        this.professeurRepository = professeurRepository;
    }

    public List<NoteDto> getNotesByEleve(Long eleveId) {
        // Vérifier si l'élève existe
        Optional<Eleve> eleveOpt = eleveRepository.findById(eleveId);
        if (eleveOpt.isEmpty()) {
            throw new RuntimeException("Élève non trouvé !");
        }
        Eleve eleve = eleveOpt.get();

        // Récupérer les matières associées au niveau et spécialité de l'élève
        List<Coefficient> coefficients = coefRepository.findByNiveauEtudeAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite());

        // Mapper les coefficients en DTOs avec les professeurs et les notes
        return coefficients.stream().map(coefficient -> {
            // Récupérer le professeur de la matière
            List<Professeur> profOpt = professeurRepository.findByMatiere(coefficient.getMatiere());
            List<String> nomsProfesseurs = profOpt.stream()
                    .map(Professeur::getNom)
                    .collect(Collectors.toList());

            // Récupérer la note de l'élève pour cette matière
            Optional<Note> noteOpt = noteRepository.findByEleveAndCoefficient(eleve, coefficient);

            // Construire le DTO
            NoteDto noteDTO = new NoteDto();
            noteDTO.setEleveId(eleveId);
            noteDTO.setMatiere(coefficient.getMatiere());
            noteDTO.setCoefficient(coefficient.getCoefficient());
            noteDTO.setProfesseur(String.join(", ", nomsProfesseurs)); // Convertit la liste en chaîne

            // Si une note existe, remplir les champs
            noteOpt.ifPresent(note -> {
                noteDTO.setInt1(note.getInt1());
                noteDTO.setInt2(note.getInt2());
                noteDTO.setDev(note.getDev());
                noteDTO.setComp(note.getComp());
                noteDTO.setMoyClasse(note.getMoyClasse());
                noteDTO.setMoyTotal(note.getMoyTotal());
                noteDTO.setMoyCoef(note.getMoyCoef());
                noteDTO.setObservation(note.getObservation().name());
            });

            return noteDTO;
        }).collect(Collectors.toList());
    }

    public List<NoteDto> postNotesByEleve(Long eleveId, List<NoteDto> notesDtoList) {
        // Vérifier si l'élève existe
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé !"));

        // Liste des notes enregistrées à retourner
        List<NoteDto> savedNotesDtoList = new ArrayList<>();

        for (NoteDto noteDto : notesDtoList) {
            // Récupérer les matières associées au niveau et spécialité de l'élève
            List<Coefficient> coefficients = coefRepository.findByNiveauEtudeAndSpecialite(eleve.getNiveauEtude(), eleve.getSpecialite());

            // Trouver le coefficient correspondant à la matière
            Coefficient coefficient = coefficients.stream()
                    .filter(c -> c.getMatiere().equals(noteDto.getMatiere()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Coefficient non trouvé pour la matière : " + noteDto.getMatiere()));

            // Récupérer les professeurs enseignant cette matière
            List<Professeur> professeurs = professeurRepository.findByMatiere(noteDto.getMatiere());
            List<String> nomsProfesseurs = professeurs.stream()
                    .map(Professeur::getNom)
                    .collect(Collectors.toList());

            // Calcul des moyennes
            double moyClasse = (noteDto.getInt1() + noteDto.getInt2() + noteDto.getDev()) / 3;
            double moyTotal = (moyClasse + noteDto.getComp()) / 2;
            double moyCoef = moyTotal * coefficient.getCoefficient();

            // Arrondi à 2 chiffres après la virgule
            moyClasse = Math.round(moyClasse * 100.0) / 100.0;
            moyTotal = Math.round(moyTotal * 100.0) / 100.0;
            moyCoef = Math.round(moyCoef * 100.0) / 100.0;


            // Déterminer l'observation automatiquement
            Observation observation = Observation.getObservationFromMoyTotal(moyTotal);

            // Créer et enregistrer la note
            Note note = new Note();
            note.setEleve(eleve);
            note.setCoefficient(coefficient); // ✅ Correction ici
            note.setInt1(noteDto.getInt1());
            note.setInt2(noteDto.getInt2());
            note.setDev(noteDto.getDev());
            note.setComp(noteDto.getComp());
            note.setMoyClasse(moyClasse);
            note.setMoyTotal(moyTotal);
            note.setMoyCoef(moyCoef);
            note.setObservation(observation); // Automatique maintenant !

            noteRepository.save(note);

            // Mettre à jour le DTO pour l'affichage
            noteDto.setEleveId(eleveId);
            noteDto.setMoyClasse(moyClasse);
            noteDto.setMoyTotal(moyTotal);
            noteDto.setMoyCoef(moyCoef);
            noteDto.setProfesseur(String.join(", ", nomsProfesseurs));
            noteDto.setObservation(observation.name());

            savedNotesDtoList.add(noteDto);
        }

        return savedNotesDtoList;
    }

}
