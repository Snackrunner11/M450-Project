package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GeraeteViewController {
    private final GeraeteRepository geraeteRepository;
    private final GeraeteAusgelihenRepository ausleiheRepository;

    public GeraeteViewController(GeraeteRepository geraeteRepository, GeraeteAusgelihenRepository ausleiheRepository) {
        this.geraeteRepository = geraeteRepository;
        this.ausleiheRepository = ausleiheRepository;
    }

    @GetMapping("/geraete/view")
    public String viewGeraete(Model model) {
        model.addAttribute("geraete", geraeteRepository.findAll());
        Map<Long, String> ausleiheMap = new HashMap<>();
        for (GeraeteAusleihe ausleihe : ausleiheRepository.findAll()) {
            if (ausleihe.getGeraet() != null) {
                ausleiheMap.put(ausleihe.getGeraet().getId(), ausleihe.getEntleiherName());
            }
        }
        model.addAttribute("ausleiheMap", ausleiheMap);
        return "geraete";
    }

    @GetMapping("/geraete/add")
    public String addGeraetForm(Model model) {
        model.addAttribute("geraet", new Gereate());
        return "addGeraet";
    }

    @PostMapping("/geraete/add")
    public String addGeraetSubmit(@ModelAttribute Gereate geraet) {
        geraet.setStatus(true);
        geraeteRepository.save(geraet);
        return "redirect:/geraete/view";
    }

    @GetMapping("/geraete/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Gereate geraet = geraeteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
        model.addAttribute("geraet", geraet);
        return "editGeraet";
    }

    @PostMapping("/geraete/update/{id}")
    public String updateGeraet(@PathVariable("id") Long id, @ModelAttribute Gereate bearbeitetesGeraet) {
        Gereate existierendesGeraet = geraeteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
        
        existierendesGeraet.setProdukt(bearbeitetesGeraet.getProdukt());
        existierendesGeraet.setTyp(bearbeitetesGeraet.getTyp());
        existierendesGeraet.setStatus(bearbeitetesGeraet.getStatus());
        
        geraeteRepository.save(existierendesGeraet);
        return "redirect:/geraete/view";
    }

    @GetMapping("/geraete/delete/{id}")
    public String deleteGeraet(@PathVariable("id") Long id) {
        ausleiheRepository.findAll().forEach(ausleihe -> {
            if (ausleihe.getGeraet() != null && ausleihe.getGeraet().getId().equals(id)) {
                ausleiheRepository.delete(ausleihe);
            }
        });
        geraeteRepository.deleteById(id);
        return "redirect:/geraete/view";
    }
}