package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Entity.Configuration;
import com.eschoolback.eschool.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    // Sauvegarde ou mise à jour d'une configuration
    public Configuration saveConfiguration(Configuration config) {
        return configurationRepository.save(config);
    }

    // Récupérer toutes les configurations
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    // Récupérer une configuration par ID
    public Optional<Configuration> getConfigurationById(Long id) {
        return configurationRepository.findById(id);
    }

    // Supprimer une configuration
    public void deleteConfiguration(Long id) {
        configurationRepository.deleteById(id);
    }
}
