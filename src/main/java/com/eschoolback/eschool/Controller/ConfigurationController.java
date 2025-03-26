package com.eschoolback.eschool.Controller;



import com.eschoolback.eschool.Entity.Configuration;
import com.eschoolback.eschool.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configurations")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    // Ajouter ou mettre à jour une configuration
    @PostMapping("/save")
    public ResponseEntity<Configuration> saveConfiguration(@RequestBody Configuration config) {
        Configuration savedConfig = configurationService.saveConfiguration(config);
        return ResponseEntity.ok(savedConfig);
    }


    // Récupérer toutes les configurations
    @GetMapping
    public List<Configuration> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }


    // Récupérer une configuration par ID
//    @GetMapping
//    public ResponseEntity<Configuration> getConfigurationById(@PathVariable Long id) {
//        Optional<Configuration> config = configurationService.getConfigurationById(id);
//        return config.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    // Supprimer une configuration
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() {
        byte[] image = configurationService.getImage();

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png"); // Modifier selon le format réel de l'image (ex: image/jpeg)

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
