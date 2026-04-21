package com.ubung.Lernen;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // WICHTIG: Macht alle Datenbankänderungen nach dem Test wieder rückgängig!
public class GeraeteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // injizieren die echten Repositories, keine Mocks!
    @Autowired
    private GeraeteRepository geraeteRepository;

    @Autowired
    private GeraeteAusgelihenRepository ausleiheRepository;

    @Test
    public void testFullAusleiheLifecycle() throws Exception {
        // ==========================================
        // 1. Neues Gerät hinzufügen
        // ==========================================
        mockMvc.perform(post("/geraete/add")
                .param("produkt", "Test-Kamera")
                .param("typ", "Video"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));

        // Prüfen, ob es in der echten DB gespeichert wurde
        Gereate savedGeraet = geraeteRepository.findAll().stream()
                .filter(g -> "Test-Kamera".equals(g.getProdukt()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Gerät wurde nicht in der DB gefunden!"));
        
        assertTrue(savedGeraet.getStatus(), "Neues Gerät sollte standardmäßig verfügbar sein.");

        Long geraetId = savedGeraet.getId();

        // ==========================================
        // 2. Gerät ausleihen
        // ==========================================
        mockMvc.perform(post("/ausleihe/ausleihen/" + geraetId)
                .param("name", "Max Mustermann"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));

        // Prüfen, ob der Status in der DB auf "false" (ausgeliehen) gewechselt ist
        Gereate ausgeliehenesGeraet = geraeteRepository.findById(geraetId).orElseThrow();
        assertFalse(ausgeliehenesGeraet.getStatus(), "Gerät sollte nun als nicht verfügbar markiert sein.");

        // Prüfen, ob ein Eintrag in der Ausleihe-Tabelle existiert
        Optional<GeraeteAusleihe> ausleihe = ausleiheRepository.findByGeraetId(geraetId);
        assertTrue(ausleihe.isPresent(), "Ausleihe-Eintrag muss in der DB existieren.");
        assertEquals("Max Mustermann", ausleihe.get().getEntleiherName());

        // ==========================================
        // 3. Gerät zurückgeben
        // ==========================================
        mockMvc.perform(post("/ausleihe/zurueckgeben/" + geraetId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));

        // Prüfen, ob der Status wieder auf "true" steht und die Ausleihe gelöscht wurde
        Gereate zurueckgegebenesGeraet = geraeteRepository.findById(geraetId).orElseThrow();
        assertTrue(zurueckgegebenesGeraet.getStatus(), "Gerät sollte wieder verfügbar sein.");
        assertTrue(ausleiheRepository.findByGeraetId(geraetId).isEmpty(), "Ausleihe-Eintrag muss aus DB gelöscht sein.");
    }
}