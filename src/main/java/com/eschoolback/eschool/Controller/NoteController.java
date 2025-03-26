package com.eschoolback.eschool.Controller;


import com.eschoolback.eschool.Dto.NoteDto;
import com.eschoolback.eschool.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{eleveId}")
    public ResponseEntity<List<NoteDto>> getNotesByEleve(@PathVariable Long eleveId) {
        return ResponseEntity.ok(noteService.getNotesByEleve(eleveId));
    }

    @PostMapping("/{eleveId}")
    public ResponseEntity<List<NoteDto>> postNotesByEleve(
            @PathVariable Long eleveId,
            @RequestBody List<NoteDto> notesDtoList) {
        List<NoteDto> savedNotes = noteService.postNotesByEleve(eleveId, notesDtoList);
        return ResponseEntity.ok(savedNotes);
    }
}
