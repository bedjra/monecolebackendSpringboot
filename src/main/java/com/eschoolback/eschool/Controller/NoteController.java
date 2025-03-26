package com.eschoolback.eschool.Controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
}
