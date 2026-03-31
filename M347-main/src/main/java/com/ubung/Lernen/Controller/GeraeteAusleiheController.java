package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ausleihe")
public class GeraeteAusleiheController {
    // GeraeteRepository und GeraeteAusgelihenRepository sind zwei Klassen, die von Spring Data JPA generiert wurden
    private final GeraeteRepository geraeteRepository;
    private final GeraeteAusgelihenRepository ausleiheRepository;
    // Konstruktor der Klasse GeraeteAusleiheController
    public GeraeteAusleiheController(GeraeteRepository geraeteRepository, GeraeteAusgelihenRepository ausleiheRepository) {
        this.geraeteRepository = geraeteRepository;
        this.ausleiheRepository = ausleiheRepository;
    }
    // Die Methode ausleihen() wird aufgerufen, wenn ein Gerät ausgeliehen wird
    @PostMapping("/ausleihen/{id}") // Die Methode wird aufgerufen, wenn ein POST-Request an /ausleihe/ausleihen/{id} gesendet wird
    public String ausleihen(@PathVariable Long id, @RequestParam String name) {
        Optional<Gereate> optionalGeraet = geraeteRepository.findById(id);
        // Prüfen, ob das Gerät existiert
        if (optionalGeraet.isPresent()) {
            Gereate geraet = optionalGeraet.get();

            // Prüfen, ob das Gerät bereits ausgeliehen ist
            if (ausleiheRepository.findByGeraetId(id).isEmpty()) {
                GeraeteAusleihe ausleihe = new GeraeteAusleihe();
                ausleihe.setGeraet(geraet);
                ausleihe.setEntleiherName(name);
                ausleiheRepository.save(ausleihe);

                // Status des Geräts setzen
                geraet.setStatus(false);
                geraeteRepository.save(geraet);
            }
        }
        return "redirect:/geraete/view"; // Weiterleitung auf die Seite, auf der alle Geräte angezeigt werden
    }
    // Die Methode zurueckgeben() wird aufgerufen, wenn ein Gerät zurückgegeben wird
    @PostMapping("/zurueckgeben/{id}")
    public String zurueckgeben(@PathVariable Long id) {
        Optional<GeraeteAusleihe> ausleihe = ausleiheRepository.findByGeraetId(id);
        // Prüfen, ob das Gerät ausgeliehen ist
        if (ausleihe.isPresent()) {
            ausleiheRepository.delete(ausleihe.get());

            // Gerät wieder als verfügbar setzen
            Optional<Gereate> optionalGeraet = geraeteRepository.findById(id);
            if (optionalGeraet.isPresent()) {
                Gereate geraet = optionalGeraet.get();
                geraet.setStatus(true);
                geraeteRepository.save(geraet);
            }
        }
        return "redirect:/geraete/view"; // Weiterleitung auf die Seite, auf der alle Geräte angezeigt werden
    }
}
