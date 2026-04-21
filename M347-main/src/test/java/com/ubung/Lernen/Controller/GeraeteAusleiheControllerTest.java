package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.GeraeteAusleihe;
import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteAusgelihenRepository;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Aktiviert Mockito (ohne Spring!)
public class GeraeteAusleiheControllerTest {

    @Mock
    private GeraeteRepository geraeteRepository;

    @Mock
    private GeraeteAusgelihenRepository ausleiheRepository;

    @InjectMocks // Erstellt den Controller und injiziert die oben gemockten Repositories
    private GeraeteAusleiheController controller;

    private Gereate testGeraet;

    @BeforeEach
    void setUp() {
        testGeraet = new Gereate();
        testGeraet.setId(1L);
        testGeraet.setStatus(true); // Gerät ist am Anfang verfügbar
    }

    @Test
    public void testAusleihen_Success() {
        // Arrange (Vorbereitung)
        String entleiherName = "Anna Muster";
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(testGeraet));
        Mockito.when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.empty()); // Noch nicht ausgeliehen

        // Act (Ausführung)
        String viewName = controller.ausleihen(1L, entleiherName);

        // Assert (Überprüfung)
        assertEquals("redirect:/geraete/view", viewName); // Korrekte Weiterleitung
        assertFalse(testGeraet.getStatus()); // Status muss auf 'false' gewechselt sein

        // Prüfen, ob die save-Methoden der Repositories aufgerufen wurden
        verify(ausleiheRepository).save(any(GeraeteAusleihe.class));
        verify(geraeteRepository).save(testGeraet);
    }

    @Test
    public void testZurueckgeben_Success() {
        // Arrange (Vorbereitung)
        testGeraet.setStatus(false); // Gerät ist aktuell ausgeliehen
        GeraeteAusleihe ausleihe = new GeraeteAusleihe();
        ausleihe.setGeraet(testGeraet);

        Mockito.when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.of(ausleihe));
        Mockito.when(geraeteRepository.findById(1L)).thenReturn(Optional.of(testGeraet));

        // Act (Ausführung)
        String viewName = controller.zurueckgeben(1L);

        // Assert (Überprüfung)
        assertEquals("redirect:/geraete/view", viewName); // Korrekte Weiterleitung
        assertTrue(testGeraet.getStatus()); // Status muss wieder auf 'true' (verfügbar) sein

        // Prüfen, ob der Eintrag gelöscht und das Gerät aktualisiert wurde
        verify(ausleiheRepository).delete(ausleihe);
        verify(geraeteRepository).save(testGeraet);
    }
}