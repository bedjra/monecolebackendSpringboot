package com.eschoolback.eschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitulaireRepository extends JpaRepository<Titulaire, Long> {
}
