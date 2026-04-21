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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeraeteController.class)
public class GeraeteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeraeteRepository geraeteRepository;

    @Test
    public void testIndex_ReturnsGeraeteList() throws Exception {
        // Arrange: Mock-Daten vorbereiten
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

        // Act & Assert: Endpunkt aufrufen und Ergebnisse prüfen
        mockMvc.perform(get("/geraete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produkt").value("Laptop"))
                .andExpect(jsonPath("$[0].typ").value("Elektronik"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].produkt").value("Beamer"));
    }
}