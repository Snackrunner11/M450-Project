package com.ubung.Lernen.repository;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// GeraeteAusgelihenRepository Interface für die GeraeteAusleihe Entity Klasse
public interface GeraeteAusgelihenRepository extends JpaRepository<GeraeteAusleihe, Long> {
    Optional<GeraeteAusleihe> findByGeraetId(Long geraetId);
}
