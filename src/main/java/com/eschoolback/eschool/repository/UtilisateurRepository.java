package com.eschoolback.eschool.repository;

import com.eschoolback.eschool.Entity.Utilisateur;
import com.eschoolback.eschool.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmailAndPasswordAndRole(String email, String password, Role role);
    Utilisateur findByEmail(String email);

}
