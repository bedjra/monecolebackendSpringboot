package com.eschoolback.eschool.repository;


import com.eschoolback.eschool.Entity.Coefficient;
import com.eschoolback.eschool.Entity.Eleve;
import com.eschoolback.eschool.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByEleveAndCoefficient(Eleve eleve, Coefficient coefficient);

}
