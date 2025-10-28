package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MitarbeiterTest {
    private Mitarbeiter mitarbeiter1;
    private Filiale filiale1;

    @Before
    public void setUp() {
        mitarbeiter1 = new Mitarbeiter(
                "Max",
                "Mustermann",
                "Hauptstrasse",
                "1",
                "max@bank.de",
                2001
        );
        filiale1 = new Filiale(100);
    }

    // Konstruktor Tests
    @Test
    public void testKonstruktor() {
        assertEquals(2001, mitarbeiter1.getMitarbeiterNummer());
        assertEquals("Max", mitarbeiter1.getVorname());
        assertEquals("Mustermann", mitarbeiter1.getNachname());
    }

    @Test
    public void testListenAmAnfangLeer() {
        assertEquals(0, mitarbeiter1.getKundenListe().size());
        assertEquals(0, mitarbeiter1.getFilialeListe().size());
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
        assertTrue(mitarbeiter1.addKunde(kunde));
        assertEquals(1, mitarbeiter1.getKundenListe().size());
    }

    @Test
    public void testAddKundeNull() {
        assertFalse(mitarbeiter1.addKunde(null));
    }

    @Test
    public void testAddKundeDoppelt() {
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
        assertTrue(mitarbeiter1.addKunde(kunde));
        assertFalse(mitarbeiter1.addKunde(kunde));
        assertEquals(1, mitarbeiter1.getKundenListe().size());
    }

    // Kunde entfernen Tests
    @Test
    public void testRemoveKunde() {
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
        mitarbeiter1.addKunde(kunde);
        assertTrue(mitarbeiter1.removeKunde(kunde));
        assertEquals(0, mitarbeiter1.getKundenListe().size());
    }

    @Test
    public void testRemoveKundeNichtVorhanden() {
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
        assertFalse(mitarbeiter1.removeKunde(kunde));
    }

    // Filiale hinzufügen Tests
    @Test
    public void testAddFiliale() {
        assertTrue(mitarbeiter1.addFiliale(filiale1));
        assertEquals(1, mitarbeiter1.getFilialeListe().size());
    }

    @Test
    public void testAddFilialeNull() {
        assertFalse(mitarbeiter1.addFiliale(null));
    }

    @Test
    public void testAddFilialeMehrere() {
        Filiale filiale2 = new Filiale(200);
        assertTrue(mitarbeiter1.addFiliale(filiale1));
        assertTrue(mitarbeiter1.addFiliale(filiale2));
        assertEquals(2, mitarbeiter1.getFilialeListe().size());
    }

    // Anzahl Kunden Test
    @Test
    public void testGetAnzahlKunden() {
        assertEquals(0, mitarbeiter1.getAnzahlKunden());

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
        mitarbeiter1.addKunde(kunde1);

        assertEquals(1, mitarbeiter1.getAnzahlKunden());
    }
}