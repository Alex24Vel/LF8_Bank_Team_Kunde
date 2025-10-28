package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FilialeTest {
    private Filiale filiale1;
    private Mitarbeiter mitarbeiter1;

    @Before
    public void setUp() {
        filiale1 = new Filiale(100);
        mitarbeiter1 = new Mitarbeiter(
                "Max",
                "Mustermann",
                "Hauptstrasse",
                "1",
                "max@bank.de",
                2001
        );
    }

    // Konstruktor Tests
    @Test
    public void testKonstruktor() {
        assertEquals(100, filiale1.getFilialeNummer());
    }

    @Test
    public void testListenAmAnfangLeer() {
        assertEquals(0, filiale1.getKundenListe().size());
        assertEquals(0, filiale1.getMitarbeiterListe().size());
    }

    // Kunde hinzufügen Tests
    @Test
    public void testAddKunde() {
        Kunde kunde = new Kunde(
                "Anna",
                "Schmidt",
                "Bahnhofstr",
                "5",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        assertTrue(filiale1.addKunde(kunde));
        assertEquals(1, filiale1.getKundenListe().size());
    }

    @Test
    public void testAddKundeNull() {
        assertFalse(filiale1.addKunde(null));
    }

    // Mitarbeiter hinzufügen Tests
    @Test
    public void testAddMitarbeiter() {
        assertTrue(filiale1.addMitarbeiter(mitarbeiter1));
        assertEquals(1, filiale1.getMitarbeiterListe().size());
    }

    @Test
    public void testAddMitarbeiterNull() {
        assertFalse(filiale1.addMitarbeiter(null));
    }

    // Anzahl Tests
    @Test
    public void testGetAnzahlKunden() {
        assertEquals(0, filiale1.getAnzahlKunden());

        Kunde kunde = new Kunde(
                "Anna",
                "Schmidt",
                "Bahnhofstr",
                "5",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        filiale1.addKunde(kunde);

        assertEquals(1, filiale1.getAnzahlKunden());
    }

    @Test
    public void testGetAnzahlMitarbeiter() {
        assertEquals(0, filiale1.getAnzahlMitarbeiter());
        filiale1.addMitarbeiter(mitarbeiter1);
        assertEquals(1, filiale1.getAnzahlMitarbeiter());
    }

    // Alle Kontos Tests
    @Test
    public void testGetAllKontosLeer() {
        assertEquals(0, filiale1.getAllKontos().size());
    }

    @Test
    public void testGetAllKontosMitKunden() {
        Kunde kunde1 = new Kunde(
                "Anna",
                "Schmidt",
                "Bahnhofstr",
                "5",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );

        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);
        filiale1.addKunde(kunde1);

        assertEquals(2, filiale1.getAllKontos().size());
    }
}