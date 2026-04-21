package com.ubung.Lernen.Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GereateTest {

    @Test
    public void testGereateGettersAndSetters() {
        // Arrange
        Gereate geraet = new Gereate();
        Long id = 5L;
        String produkt = "Maus";
        String typ = "Hardware";
        boolean status = true;

        // Act
        geraet.setId(id);
        geraet.setProdukt(produkt);
        geraet.setTyp(typ);
        geraet.setStatus(status);

        // Assert
        assertEquals(id, geraet.getId());
        assertEquals(produkt, geraet.getProdukt());
        assertEquals(typ, geraet.getTyp());
        assertTrue(geraet.getStatus());
    }
}