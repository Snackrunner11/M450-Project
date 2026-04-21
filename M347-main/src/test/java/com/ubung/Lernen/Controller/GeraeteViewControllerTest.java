package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeraeteViewController.class)
public class GeraeteViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeraeteRepository geraeteRepository;

    @MockBean
    private GeraeteAusgelihenRepository ausleiheRepository;

    @Test
    public void testViewGeraete_ReturnsViewWithModels() throws Exception {
        // Arrange
        Gereate geraet = new Gereate();
        geraet.setId(1L);
        geraet.setProdukt("Tablet");
        geraet.setTyp("Elektronik");
        geraet.setStatus(true);

        Mockito.when(geraeteRepository.findAll()).thenReturn(Collections.singletonList(geraet));
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/geraete/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("geraete")) // Prüft ob "geraete.html" aufgerufen wird
                .andExpect(model().attributeExists("geraete")) // Prüft ob die Liste im Model ist
                .andExpect(model().attributeExists("ausleiheMap"));
    }

    @Test
    public void testAddGeraetForm_ReturnsAddView() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/geraete/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addGeraet"))
                .andExpect(model().attributeExists("geraet"));
    }

    @Test
    public void testAddGeraetSubmit_RedirectsToView() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/geraete/add")
                .param("produkt", "Monitor")
                .param("typ", "Hardware"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/geraete/view"));
                
        // Verifiziert, dass die save-Methode des Repositories aufgerufen wurde
        Mockito.verify(geraeteRepository, Mockito.times(1)).save(Mockito.any(Gereate.class));
    }
}