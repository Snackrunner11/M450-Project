package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.Arrays;

// Neue Imports für POST und Mockito.any
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(GeraeteController.class)
public class GeraeteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeraeteRepository geraeteRepository;

    // Bisheriger Test für GET
    @Test
    public void testIndex_ReturnsGeraeteList() throws Exception {
        Gereate geraet1 = new Gereate();
        geraet1.setId(1L);
        geraet1.setProdukt("Laptop");
        geraet1.setTyp("Elektronik");
        geraet1.setStatus(true);

        Gereate geraet2 = new Gereate();
        geraet2.setId(2L);
        geraet2.setProdukt("Beamer");
        geraet2.setTyp("Präsentation");
        geraet2.setStatus(false);

        Mockito.when(geraeteRepository.findAll()).thenReturn(Arrays.asList(geraet1, geraet2));

        mockMvc.perform(get("/geraete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produkt").value("Laptop"))
                .andExpect(jsonPath("$[0].typ").value("Elektronik"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].produkt").value("Beamer"));
    }

    // NEUER TEST für POST (Create)
    @Test
    public void testCreateGeraet_ReturnsCreatedGeraet() throws Exception {
        // Arrange: Das erwartete Gerät, so wie es die Datenbank (bzw. das Mock-Repository) zurückgeben soll
        Gereate neuesGeraet = new Gereate();
        neuesGeraet.setId(3L);
        neuesGeraet.setProdukt("Tablet");
        neuesGeraet.setTyp("Elektronik");
        neuesGeraet.setStatus(true);

        // Wir sagen Mockito: Wenn .save() mit irgendeinem Gereate-Objekt aufgerufen wird, gib 'neuesGeraet' zurück
        Mockito.when(geraeteRepository.save(any(Gereate.class))).thenReturn(neuesGeraet);

        // Der JSON-Body, der beim POST-Request mitgeschickt wird
        String neuesGeraetJson = "{\"produkt\":\"Tablet\", \"typ\":\"Elektronik\", \"status\":true}";

        // Act & Assert: POST Request senden und überprüfen
        mockMvc.perform(post("/geraete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(neuesGeraetJson))
                .andExpect(status().isCreated()) // Erwartet HTTP Status 201 Created
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.produkt").value("Tablet"))
                .andExpect(jsonPath("$.typ").value("Elektronik"))
                .andExpect(jsonPath("$.status").value(true));
    }

    // NEUER TEST: Ein einzelnes Gerät erfolgreich abrufen
    @Test
    public void testGetGeraetById_ReturnsGeraet() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        geraet.setProdukt("Smartphone");
        geraet.setTyp("Elektronik");
        geraet.setStatus(true);

        // Mocking: Wenn nach ID 1 gesucht wird, gib das Gerät zurück
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(java.util.Optional.of(geraet));

        mockMvc.perform(get("/geraete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.produkt").value("Smartphone"));
    }

    // NEUER TEST: Wenn das Gerät nicht existiert, gib 404 Not Found zurück
    @Test
    public void testGetGeraetById_ReturnsNotFound() throws Exception {
        // Mocking: Wenn nach ID 99 gesucht wird, gib "leer" zurück
        Mockito.when(geraeteRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/geraete/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // NEUER TEST: Ein bestehendes Gerät aktualisieren (Update)
    @Test
    public void testUpdateGeraet_ReturnsUpdatedGeraet() throws Exception {
        // Arrange: Das Gerät, wie es aktuell in der Datenbank liegt
        Gereate bestehendesGeraet = new Gereate();
        bestehendesGeraet.setId(1L);
        bestehendesGeraet.setProdukt("Laptop");
        bestehendesGeraet.setTyp("Elektronik");
        bestehendesGeraet.setStatus(true);

        // Arrange: Das Gerät, nachdem es aktualisiert wurde
        Gereate aktualisiertesGeraet = new Gereate();
        aktualisiertesGeraet.setId(1L);
        aktualisiertesGeraet.setProdukt("Gaming Laptop"); // Geändert!
        aktualisiertesGeraet.setTyp("Elektronik");
        aktualisiertesGeraet.setStatus(true);

        // Mocking: findById gibt das alte zurück, save gibt das neue zurück
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(java.util.Optional.of(bestehendesGeraet));
        Mockito.when(geraeteRepository.save(any(Gereate.class))).thenReturn(aktualisiertesGeraet);

        // Act & Assert: PUT Request senden mit den neuen Daten
        String updateJson = "{\"produkt\":\"Gaming Laptop\", \"typ\":\"Elektronik\", \"status\":true}";

        mockMvc.perform(put("/geraete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.produkt").value("Gaming Laptop")); // Wir erwarten den neuen Namen
    }

    // NEUER TEST: Update schlägt fehl, weil Gerät nicht existiert
    @Test
    public void testUpdateGeraet_ReturnsNotFound() throws Exception {
        // Mocking: Das Gerät wird nicht gefunden
        Mockito.when(geraeteRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        String updateJson = "{\"produkt\":\"Gaming Laptop\", \"typ\":\"Elektronik\", \"status\":true}";

        mockMvc.perform(put("/geraete/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isNotFound()); // Erwartet HTTP 404
    }

    // NEUER TEST: Ein bestehendes Gerät erfolgreich löschen
    @Test
    public void testDeleteGeraet_ReturnsOk() throws Exception {
        // Arrange: Das Gerät, das gelöscht werden soll
        Gereate zuLoeschendesGeraet = new Gereate();
        zuLoeschendesGeraet.setId(1L);

        // Mocking: Wir tun so, als ob das Gerät in der Datenbank existiert
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(java.util.Optional.of(zuLoeschendesGeraet));
        
        // Act & Assert: DELETE Request senden
        mockMvc.perform(delete("/geraete/1"))
                .andExpect(status().isOk()); // Erwartet HTTP 200 OK
    }

    // NEUER TEST: Löschen schlägt fehl, weil Gerät nicht existiert
    @Test
    public void testDeleteGeraet_ReturnsNotFound() throws Exception {
        // Mocking: Das Gerät wird nicht gefunden
        Mockito.when(geraeteRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/geraete/99"))
                .andExpect(status().isNotFound()); // Erwartet HTTP 404
    }
}