package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class GeraeteViewControllerUnit_Test {

    @Mock
    private GeraeteRepository geraeteRepository;

    @Mock
    private GeraeteAusgelihenRepository ausleiheRepository;

    @Mock // Wir mocken das Spring UI-Model, da wir keinen echten Web-Kontext haben
    private Model model;

    @InjectMocks
    private GeraeteViewController controller;

    @Test
    public void testViewGeraete() {
        // Arrange
        Gereate geraet = new Gereate();
        geraet.setId(10L);
        geraet.setProdukt("Scanner");

        GeraeteAusleihe ausleihe = new GeraeteAusleihe();
        ausleihe.setGeraet(geraet);
        ausleihe.setEntleiherName("Peter Pan");

        Mockito.when(geraeteRepository.findAll()).thenReturn(Collections.singletonList(geraet));
        Mockito.when(ausleiheRepository.findAll()).thenReturn(Collections.singletonList(ausleihe));

        // Act
        String viewName = controller.viewGeraete(model);

        // Assert
        assertEquals("geraete", viewName); // Überprüft den Namen des HTML-Templates
        
        // Prüft, ob dem Model das Attribut "geraete" hinzugefügt wurde
        Mockito.verify(model).addAttribute(eq("geraete"), Mockito.anyList());
        
        // Fängt die Map ab, die dem Model hinzugefügt wurde, um sie genauer zu prüfen
        ArgumentCaptor<Map> mapCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(model).addAttribute(eq("ausleiheMap"), mapCaptor.capture());
        
        Map<Long, String> capturedMap = mapCaptor.getValue();
        assertEquals("Peter Pan", capturedMap.get(10L));
    }

    @Test
    public void testAddGeraetForm() {
        // Act
        String viewName = controller.addGeraetForm(model);

        // Assert
        assertEquals("addGeraet", viewName);
        // Prüft, ob ein neues, leeres Geräte-Objekt für das Formular bereitgestellt wurde
        Mockito.verify(model).addAttribute(eq("geraet"), Mockito.any(Gereate.class));
    }

    @Test
    public void testAddGeraetSubmit() {
        // Arrange
        Gereate neuesGeraet = new Gereate();
        neuesGeraet.setProdukt("Tastatur");

        // Act
        String viewName = controller.addGeraetSubmit(neuesGeraet);

        // Assert
        assertEquals("redirect:/geraete/view", viewName);
        assertTrue(neuesGeraet.getStatus()); // Prüft die Logik: Status muss auf 'true' gesetzt worden sein
        Mockito.verify(geraeteRepository).save(neuesGeraet); // Prüft, ob save() aufgerufen wurde
    }
}