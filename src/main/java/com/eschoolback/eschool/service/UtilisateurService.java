package com.eschoolback.eschool.service;

import com.eschoolback.eschool.Entity.Utilisateur;
import com.eschoolback.eschool.enums.Role;
import com.eschoolback.eschool.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public boolean authenticate(String email, String password, Role role) {
        Utilisateur utilisateur = utilisateurRepository.findByEmailAndPasswordAndRole(email, password, role);
        return utilisateur != null;
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public String getRoleByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null) {
            return utilisateur.getRole().name();  // Retourne le rôle sous forme de chaîne
        }
        return null;
    }

}
