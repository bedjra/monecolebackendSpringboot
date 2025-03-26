package com.eschoolback.eschool.repository;


import com.eschoolback.eschool.Dto.NoteDto;
import com.eschoolback.eschool.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteReposiory extends JpaRepository<Note, Long> {


}
