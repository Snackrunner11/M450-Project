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
    
    private final GeraeteRepository geraeteRepository;
    private final GeraeteAusgelihenRepository ausleiheRepository;
    
    public GeraeteAusleiheController(GeraeteRepository geraeteRepository, GeraeteAusgelihenRepository ausleiheRepository) {
        this.geraeteRepository = geraeteRepository;
        this.ausleiheRepository = ausleiheRepository;
    }
    
    @PostMapping("/ausleihen/{id}") 
    public String ausleihen(@PathVariable Long id, @RequestParam String name) {
        Optional<Gereate> optionalGeraet = geraeteRepository.findById(id);
        
        if (optionalGeraet.isPresent()) {
            Gereate geraet = optionalGeraet.get();

            if (ausleiheRepository.findByGeraetId(id).isEmpty()) {
                GeraeteAusleihe ausleihe = new GeraeteAusleihe();
                ausleihe.setGeraet(geraet);
                ausleihe.setEntleiherName(name);
                ausleiheRepository.save(ausleihe);

                geraet.setStatus(false);
                geraeteRepository.save(geraet);
            }
        }
        return "redirect:/geraete/view"; 
    }
    
    @PostMapping("/zurueckgeben/{id}")
    public String zurueckgeben(@PathVariable Long id) {
        Optional<GeraeteAusleihe> ausleihe = ausleiheRepository.findByGeraetId(id);
        
        if (ausleihe.isPresent()) {
            ausleiheRepository.delete(ausleihe.get());

            Optional<Gereate> optionalGeraet = geraeteRepository.findById(id);
            if (optionalGeraet.isPresent()) {
                Gereate geraet = optionalGeraet.get();
                geraet.setStatus(true);
                geraeteRepository.save(geraet);
            }
        }
        return "redirect:/geraete/view"; 
    }
}
