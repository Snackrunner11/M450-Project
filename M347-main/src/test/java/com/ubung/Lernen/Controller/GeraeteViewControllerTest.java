package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// Verwende das neue MockitoBean anstelle von MockBean, um Warnungen zu vermeiden
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

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

    @Test
    public void testViewGeraete_ReturnsViewWithModels() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        geraet.setProdukt("Tablet");
        geraet.setTyp("Elektronik");
        geraet.setStatus(true);

        Mockito.when(geraeteRepository.findAll()).thenReturn(Collections.singletonList(geraet));
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/geraete/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("geraete"))
                .andExpect(model().attributeExists("geraete"))
                .andExpect(model().attributeExists("ausleiheMap"));
    }

    @Test
    public void testAddGeraetForm_ReturnsAddView() throws Exception {
        mockMvc.perform(get("/geraete/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addGeraet"))
                .andExpect(model().attributeExists("geraet"));
    }

    @Test
    public void testAddGeraetSubmit_RedirectsToView() throws Exception {
        mockMvc.perform(post("/geraete/add")
                .param("produkt", "Monitor")
                .param("typ", "Hardware"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));
                
        Mockito.verify(geraeteRepository, Mockito.times(1)).save(Mockito.any(Gereate.class));
    }

    // --- NEUE TESTS FÜR BEARBEITEN UND LÖSCHEN ---

    @Test
    public void testShowEditForm_ReturnsEditView() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        geraet.setProdukt("Alter Monitor");

        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet));

        mockMvc.perform(get("/geraete/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGeraet"))
                .andExpect(model().attributeExists("geraet"));
    }

    @Test
    public void testUpdateGeraet_RedirectsToView() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);

        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet));

        mockMvc.perform(post("/geraete/update/1")
                .param("produkt", "Neuer Monitor")
                .param("typ", "Hardware")
                .param("status", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));

        // Überprüft, ob das geänderte Gerät in der Datenbank gespeichert wurde
        Mockito.verify(geraeteRepository, Mockito.times(1)).save(Mockito.any(Gereate.class));
    }

    @Test
    public void testDeleteGeraet_RedirectsToView() throws Exception {
        Gereate geraet = new Gereate();
        geraet.setId(1L);

        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(geraet));
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/geraete/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));

        // Überprüft, ob das Gerät aus der Datenbank gelöscht wurde
        Mockito.verify(geraeteRepository, Mockito.times(1)).deleteById(1L);
    }
}