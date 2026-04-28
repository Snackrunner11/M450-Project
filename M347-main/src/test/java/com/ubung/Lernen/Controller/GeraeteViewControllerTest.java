package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeraeteViewController.class)
public class GeraeteViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GeraeteRepository geraeteRepository;

    @MockitoBean
    private GeraeteAusgelihenRepository ausleiheRepository;

    // --- TESTS FÜR DIE ANZEIGE UND DAS HINZUFÜGEN ---

    @Test
    public void testViewGeraete_ReturnsViewWithModels() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);

        // Ausleihe MIT Gerät (deckt if != null ab)
        GeraeteAusleihe ausleiheMit = new GeraeteAusleihe();
        ausleiheMit.setGeraet(geraet);
        ausleiheMit.setEntleiherName("Max");

        // Ausleihe OHNE Gerät (deckt den if == null Zweig in der viewGeraete Methode ab)
        GeraeteAusleihe ausleiheOhne = new GeraeteAusleihe();
        ausleiheOhne.setGeraet(null);

        Mockito.when(geraeteRepository.findAll()).thenReturn(Collections.singletonList(geraet));
        
        // Beide Ausleihen zurückgeben, damit JaCoCo beide Wege der if-Abfrage protokolliert
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Arrays.asList(ausleiheMit, ausleiheOhne));

        mockMvc.perform(get("/geraete/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("geraete"));
    }

    @Test
    public void testAddGeraetForm_ReturnsAddView() throws Exception {
        mockMvc.perform(get("/geraete/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addGeraet"));
    }

    @Test
    public void testAddGeraetSubmit_RedirectsToView() throws Exception {
        mockMvc.perform(post("/geraete/add").param("produkt", "Monitor").param("typ", "Hardware"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));
    }

    // --- TESTS FÜR BEARBEITEN (inklusive Exceptions) ---

    @Test
    public void testShowEditForm_ReturnsEditView() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet));

        mockMvc.perform(get("/geraete/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGeraet"));
    }

    @Test
    public void testShowEditForm_InvalidId_ThrowsException() {
        // Deckt das .orElseThrow() in showEditForm ab
        Mockito.when(geraeteRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/geraete/edit/99"));
        });
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
    }

    @Test
    public void testUpdateGeraet_RedirectsToView() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet));

        mockMvc.perform(post("/geraete/update/1").param("produkt", "Neuer Monitor"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));
    }

    @Test
    public void testUpdateGeraet_InvalidId_ThrowsException() {
        // Deckt das .orElseThrow() in updateGeraet ab
        Mockito.when(geraeteRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(post("/geraete/update/99").param("produkt", "Test"));
        });
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
    }

    // --- TESTS FÜR LÖSCHEN (Deckung aller Zweige) ---

    @Test
    public void testDeleteGeraet_RedirectsToView() throws Exception {
        Gereate geraet1 = new Gereate();
        geraet1.setId(1L); // Das Gerät, das gelöscht werden soll
        
        Gereate geraet2 = new Gereate();
        geraet2.setId(2L); // Ein anderes Gerät

        // Bedingung 1: Ausleihe OHNE Gerät (getGeraet() == null)
        GeraeteAusleihe ausleiheOhne = new GeraeteAusleihe();
        ausleiheOhne.setGeraet(null);

        // Bedingung 2: Ausleihe MIT falschem Gerät (getId() != id)
        GeraeteAusleihe ausleiheFalsch = new GeraeteAusleihe();
        ausleiheFalsch.setGeraet(geraet2);

        // Bedingung 3: Ausleihe MIT richtigem Gerät (getId().equals(id)) -> diese MUSS gelöscht werden
        GeraeteAusleihe ausleiheRichtig = new GeraeteAusleihe();
        ausleiheRichtig.setGeraet(geraet1);

        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet1));
        
        // Wir übergeben alle drei Fälle, damit JaCoCo sieht, dass das if-Statement für jeden Fall richtig reagiert
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Arrays.asList(ausleiheOhne, ausleiheFalsch, ausleiheRichtig));

        mockMvc.perform(get("/geraete/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));
                
        // Verifiziere, dass nur die richtige Ausleihe gelöscht wurde
        Mockito.verify(ausleiheRepository, Mockito.times(1)).delete(ausleiheRichtig);
    }
}