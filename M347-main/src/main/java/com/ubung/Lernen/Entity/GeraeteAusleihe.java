package com.ubung.Lernen.Entity;

import jakarta.persistence.*;

@Entity // Entity Annotation für die Klasse GeraeteAusleihe
public class GeraeteAusleihe {

    // Attribute für die Klasse GeraeteAusleihe mit den entsprechenden Annotationen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // OneToOne Annotation für die Beziehung zwischen GeraeteAusleihe und Geraete
    @JoinColumn(name = "geraet_id", unique = true) // JoinColumn Annotation für die Spalte geraet_id
    private Gereate geraet; // Gereate Attribut für die Klasse GeraeteAusleihe

    private String entleiherName; // entleiherName Attribut für die Klasse GeraeteAusleihe

    public Long getId() { // Getter Methode für das Attribut id
        return id;
    }

    public void setId(Long id) { // Setter Methode für das Attribut id
        this.id = id;
    }

    public Gereate getGeraet() { // Getter Methode für das Attribut geraet
        return geraet;
    }

    public void setGeraet(Gereate geraet) { // Setter Methode für das Attribut geraet
        this.geraet = geraet;
    }

    public String getEntleiherName() { // Getter Methode für das Attribut entleiherName
        return entleiherName;
    }

    public void setEntleiherName(String entleiherName) { // Setter Methode für das Attribut entleiherName
        this.entleiherName = entleiherName;
    }
}