package com.ubung.Lernen.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntitiesTest {

    @Test
    public void testGereate() {
        Gereate geraet = new Gereate();
        geraet.setId(5L);
        geraet.setProdukt("Maus");
        geraet.setTyp("Hardware");
        geraet.setStatus(true);

        assertEquals(5L, geraet.getId());
        assertEquals("Maus", geraet.getProdukt());
        assertEquals("Hardware", geraet.getTyp());
        assertTrue(geraet.getStatus());
    }

    @Test
    public void testGeraeteAusleihe() {
        GeraeteAusleihe ausleihe = new GeraeteAusleihe();
        Gereate geraet = new Gereate();
        geraet.setId(10L);

        ausleihe.setId(1L);
        ausleihe.setGeraet(geraet);
        ausleihe.setEntleiherName("Max");

        assertEquals(1L, ausleihe.getId());
        assertEquals(geraet, ausleihe.getGeraet());
        assertEquals("Max", ausleihe.getEntleiherName());
    }
}