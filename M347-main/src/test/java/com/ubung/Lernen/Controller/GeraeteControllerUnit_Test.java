package com.ubung.Lernen.Controller;

import com.ubung.Lernen.Entity.Gereate;
import com.ubung.Lernen.repository.GeraeteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Aktiviert das Mockito-Framework für diesen Test
@ExtendWith(MockitoExtension.class)
public class GeraeteControllerUnit_Test {

    @Mock // Erstellt eine Attrappe (Mock) des Repositories
    private GeraeteRepository geraeteRepository;

    @InjectMocks // Injiziert den Mock automatisch in den Controller
    private GeraeteController geraeteController;

    @Test
    public void testIndex_ReturnsAllGeraete() {
        // Arrange: Testdaten vorbereiten
        Gereate g1 = new Gereate();
        g1.setProdukt("Laptop");
        Gereate g2 = new Gereate();
        g2.setProdukt("Maus");
        
        // Definiere, was der Mock zurückgeben soll, wenn findAll() aufgerufen wird
        Mockito.when(geraeteRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        // Act: Die Methode im Controller direkt aufrufen
        List<Gereate> result = geraeteController.index();

        // Assert: Prüfen, ob das Ergebnis stimmt
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getProdukt());
        assertEquals("Maus", result.get(1).getProdukt());
        
        // Verifizieren, dass findAll() genau einmal aufgerufen wurde
        Mockito.verify(geraeteRepository, Mockito.times(1)).findAll();
    }
}