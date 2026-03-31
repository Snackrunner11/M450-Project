package com.ubung.Lernen.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteRepository;

//KundenController Klasse für die Kunden API Endpunkte
@RestController
@RequestMapping("/geraete") //URL Pfad
public class GeraeteController {

    //KundenRepository
    private GeraeteRepository geraeteRepository;

    //Konstruktor
    public GeraeteController(GeraeteRepository kundenRepository) {
        this.geraeteRepository = kundenRepository;
    }


    //Gibt alle Kunden zurück
    @GetMapping("")
    public List<Gereate> index() {
        return geraeteRepository.findAll();
    }
}
