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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeraeteAusleiheControllerTest {

    @Mock
    private GeraeteRepository geraeteRepository;

    @Mock
    private GeraeteAusgelihenRepository ausleiheRepository;

    @InjectMocks
    private GeraeteAusleiheController controller;

    private Gereate testGeraet;
    private GeraeteAusleihe testAusleihe;

    @BeforeEach
    void setUp() {
        testGeraet = new Gereate();
        testGeraet.setId(1L);
        testGeraet.setStatus(true);

        testAusleihe = new GeraeteAusleihe();
        testAusleihe.setId(1L);
        testAusleihe.setGeraet(testGeraet);
        testAusleihe.setEntleiherName("Max");
    }

    // --- Tests für das AUSLEIHEN ---

    @Test
    public void testAusleihen_Erfolgreich() {
        when(geraeteRepository.findById(1L)).thenReturn(Optional.of(testGeraet));
        when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.empty());

        String view = controller.ausleihen(1L, "Anna");

        assertEquals("redirect:/geraete/view", view);
        assertFalse(testGeraet.getStatus());
        verify(ausleiheRepository).save(any(GeraeteAusleihe.class));
        verify(geraeteRepository).save(testGeraet);
    }

    @Test
    public void testAusleihen_GeraetExistiertNicht() {
        when(geraeteRepository.findById(99L)).thenReturn(Optional.empty()); 

        String view = controller.ausleihen(99L, "Anna");

        assertEquals("redirect:/geraete/view", view);
        // FIX: Dem Compiler den genauen Typ sagen
        verify(ausleiheRepository, never()).save(any(GeraeteAusleihe.class)); 
    }

    @Test
    public void testAusleihen_BereitsAusgeliehen() {
        when(geraeteRepository.findById(1L)).thenReturn(Optional.of(testGeraet));
        when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.of(testAusleihe)); 

        String view = controller.ausleihen(1L, "Anna");

        assertEquals("redirect:/geraete/view", view);
        // FIX: Dem Compiler den genauen Typ sagen
        verify(ausleiheRepository, never()).save(any(GeraeteAusleihe.class)); 
    }

    // --- Tests für das ZURÜCKGEBEN ---

    @Test
    public void testZurueckgeben_Erfolgreich() {
        testGeraet.setStatus(false);
        when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.of(testAusleihe));
        when(geraeteRepository.findById(1L)).thenReturn(Optional.of(testGeraet));

        String view = controller.zurueckgeben(1L);

        assertEquals("redirect:/geraete/view", view);
        assertTrue(testGeraet.getStatus());
        verify(ausleiheRepository).delete(testAusleihe);
        verify(geraeteRepository).save(testGeraet);
    }

    @Test
    public void testZurueckgeben_AusleiheExistiertNicht() {
        when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.empty());

        String view = controller.zurueckgeben(1L);

        assertEquals("redirect:/geraete/view", view);
        // FIX: Dem Compiler den genauen Typ sagen
        verify(ausleiheRepository, never()).delete(any(GeraeteAusleihe.class)); 
    }

    @Test
    public void testZurueckgeben_GeraetWurdeInZwischenzeitGeloescht() {
        when(ausleiheRepository.findByGeraetId(1L)).thenReturn(Optional.of(testAusleihe));
        when(geraeteRepository.findById(1L)).thenReturn(Optional.empty()); 

        String view = controller.zurueckgeben(1L);

        assertEquals("redirect:/geraete/view", view);
        verify(ausleiheRepository).delete(testAusleihe); 
        // FIX: Dem Compiler den genauen Typ sagen
        verify(geraeteRepository, never()).save(any(Gereate.class)); 
    }
}