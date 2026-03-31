package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

// der Controller für die GeraeteView
@Controller
public class GeraeteViewController {
    private final GeraeteRepository geraeteRepository; // GeraeteRepository
    private final GeraeteAusgelihenRepository ausleiheRepository; // GeraeteAusgelihenRepository

    // Konstruktor für die Klasse GeraeteViewController
    public GeraeteViewController(GeraeteRepository geraeteRepository, GeraeteAusgelihenRepository ausleiheRepository) {
        this.geraeteRepository = geraeteRepository;
        this.ausleiheRepository = ausleiheRepository;
    }

    // Methode für die Anzeige der GeraeteView
    @GetMapping("/geraete/view")
    public String viewGeraete(Model model) {
        model.addAttribute("geraete", geraeteRepository.findAll());

        // Map für die Ausleihe von Geraeten
        Map<Long, String> ausleiheMap = new HashMap<>();
        for (GeraeteAusleihe ausleihe : ausleiheRepository.findAll()) {
            ausleiheMap.put(ausleihe.getGeraet().getId(), ausleihe.getEntleiherName());
        }
        // Hinzufügen der Map zur Model
        model.addAttribute("ausleiheMap", ausleiheMap);
        return "geraete";
    }
    // Methode für die Anzeige des Formulars zum Hinzufügen eines Geräts
    @GetMapping("/geraete/add")
    public String addGeraetForm(Model model) {
        model.addAttribute("geraet", new Gereate());
        return "addGeraet";
    }

    // Methode zum Speichern des neuen Geräts
    @PostMapping("/geraete/add")
    public String addGeraetSubmit(@ModelAttribute Gereate geraet) {
        geraet.setStatus(true); // Standardmäßig auf verfügbar setzen
        geraeteRepository.save(geraet);
        return "redirect:/geraete/view";
    }
}