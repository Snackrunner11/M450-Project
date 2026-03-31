package com.ubung.Lernen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubung.Lernen.Entity.Gereate;

// GeraeteRepository Interface für die Gereate Entity Klasse
public interface GeraeteRepository extends JpaRepository<Gereate, Long> {

}
