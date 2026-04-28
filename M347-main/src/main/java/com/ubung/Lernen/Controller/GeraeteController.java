package com.ubung.Lernen.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteRepository;

@RestController
@RequestMapping("/geraete") 
public class GeraeteController {

    private GeraeteRepository geraeteRepository;

    public GeraeteController(GeraeteRepository kundenRepository) {
        this.geraeteRepository = kundenRepository;
    }

    // Gibt alle Geräte zurück
    @GetMapping("")
    public List<Gereate> index() {
        return geraeteRepository.findAll();
    }

    // NEU: Erstellt ein neues Gerät
    @PostMapping("")
    public ResponseEntity<Gereate> create(@RequestBody Gereate neuesGeraet) {
        // Speichert das Gerät über das Repository
        Gereate gespeichertesGeraet = geraeteRepository.save(neuesGeraet);
        
        // Gibt das gespeicherte Gerät mit dem HTTP Statuscode 201 (CREATED) zurück
        return new ResponseEntity<>(gespeichertesGeraet, HttpStatus.CREATED);
    }

    // NEU: Gibt ein einzelnes Gerät anhand seiner ID zurück
    @GetMapping("/{id}")
    public ResponseEntity<Gereate> getById(@PathVariable Long id) {
        Optional<Gereate> geraet = geraeteRepository.findById(id);
        
        if (geraet.isPresent()) {
            // Gerät gefunden -> Status 200 OK
            return new ResponseEntity<>(geraet.get(), HttpStatus.OK);
        } else {
            // Gerät nicht gefunden -> Status 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // NEU: Aktualisiert ein bestehendes Gerät
    @PutMapping("/{id}")
    public ResponseEntity<Gereate> update(@PathVariable Long id, @RequestBody Gereate geraetDetails) {
        // 1. Prüfen, ob das Gerät existiert
        Optional<Gereate> existierendesGeraet = geraeteRepository.findById(id);

        if (existierendesGeraet.isPresent()) {
            // 2. Das existierende Objekt holen
            Gereate geraetZumUpdaten = existierendesGeraet.get();
            
            // 3. Die Werte mit den neuen Werten aus dem Request-Body überschreiben
            geraetZumUpdaten.setProdukt(geraetDetails.getProdukt());
            geraetZumUpdaten.setTyp(geraetDetails.getTyp());
            geraetZumUpdaten.setStatus(geraetDetails.getStatus());
            
            // 4. Speichern und zurückgeben
            Gereate gespeichertesGeraet = geraeteRepository.save(geraetZumUpdaten);
            return new ResponseEntity<>(gespeichertesGeraet, HttpStatus.OK);
        } else {
            // Wenn nicht gefunden, 404 zurückgeben
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // NEU: Löscht ein Gerät anhand seiner ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 1. Prüfen, ob das Gerät existiert
        Optional<Gereate> geraet = geraeteRepository.findById(id);

        if (geraet.isPresent()) {
            // 2. Gerät aus der Datenbank löschen
            geraeteRepository.delete(geraet.get());
            // 3. Status 200 OK zurückgeben (ohne Body)
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Wenn nicht gefunden, 404 zurückgeben
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}