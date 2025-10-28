package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KundeTest {
    private Kunde kunde1;
    private Mitarbeiter betreuer1;
    private Filiale filiale1;

    @Before
    public void setUp() {
        filiale1 = new Filiale(100);
        betreuer1 = new Mitarbeiter(
                "Max",
                "Mustermann",
                "Hauptstrasse",
                "1",
                "max@bank.de",
                2001
        );
        kunde1 = new Kunde(
                "Anna",
                "Schmidt",
                "Bahnhofstr",
                "5",
                "anna@mail.de",
                3001,
                betreuer1,
                filiale1
        );
    }

    // Konstruktor und Getter Tests
    @Test
    public void testKonstruktor() {
        assertEquals(3001, kunde1.getKundennummer());
        assertEquals("Anna", kunde1.getVorname());
        assertEquals("Schmidt", kunde1.getNachname());
        assertEquals(betreuer1, kunde1.getBetreuer());
        assertEquals(filiale1, kunde1.getFiliale());
    }

    @Test
    public void testKontosListeAmAnfangLeer() {
        assertEquals(0, kunde1.getKontos().size());
    }

    // Konto hinzufügen - Positive Tests
    @Test
    public void testAddKontoErfolgreich() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        assertTrue(kunde1.addKonto(konto));
        assertEquals(1, kunde1.getKontos().size());
    }

    @Test
    public void testAddMehrereKontos() {
        for (int i = 1; i <= 3; i++) {
            Konto konto = new Konto(1000 + i, "DE0870090100123456789" + i);
            assertTrue(kunde1.addKonto(konto));
        }
        assertEquals(3, kunde1.getKontos().size());
    }

    @Test
    public void testAddMaximalAnzahlKontos() {
        for (int i = 1; i <= 5; i++) {
            Konto konto = new Konto(1000 + i, "DE0870090100123456789" + i);
            assertTrue(kunde1.addKonto(konto));
        }
        assertEquals(5, kunde1.getKontos().size());
    }

    // Konto hinzufügen - Negative Tests
    @Test
    public void testAddKontoUeberMaximum() {
        for (int i = 1; i <= 5; i++) {
            kunde1.addKonto(new Konto(1000 + i, "DE0870090100123456789" + i));
        }
        Konto konto6 = new Konto(1006, "DE08700901001234567896");
        assertFalse(kunde1.addKonto(konto6));
        assertEquals(5, kunde1.getKontos().size());
    }

    @Test
    public void testAddKontoNull() {
        assertFalse(kunde1.addKonto(null));
    }

    @Test
    public void testAddKontoDoppelt() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        assertTrue(kunde1.addKonto(konto));
        assertFalse(kunde1.addKonto(konto));
        assertEquals(1, kunde1.getKontos().size());
    }

    // CanAddKonto Tests
    @Test
    public void testCanAddKontoWennLeer() {
        assertTrue(kunde1.canAddKonto());
    }

    @Test
    public void testCanAddKontoWennVoll() {
        for (int i = 1; i <= 5; i++) {
            kunde1.addKonto(new Konto(1000 + i, "DE0870090100123456789" + i));
        }
        assertFalse(kunde1.canAddKonto());
    }

    // Konto entfernen Tests
    @Test
    public void testRemoveKontoErfolgreich() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        kunde1.addKonto(konto);
        assertTrue(kunde1.removeKonto(konto));
        assertEquals(0, kunde1.getKontos().size());
    }

    @Test
    public void testRemoveKontoNichtVorhanden() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        assertFalse(kunde1.removeKonto(konto));
    }

    // Betreuer wechseln Tests
    @Test
    public void testChangeBetreuer() {
        Mitarbeiter neuerBetreuer = new Mitarbeiter(
                "Lisa",
                "Müller",
                "Marktplatz",
                "3",
                "lisa@bank.de",
                2002
        );
        kunde1.changeBetreuer(neuerBetreuer);
        assertEquals(neuerBetreuer, kunde1.getBetreuer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeBetreuerNull() {
        kunde1.changeBetreuer(null);
    }

    // Filiale wechseln Tests
    @Test
    public void testChangeFiliale() {
        Filiale neueFiliale = new Filiale(200);
        kunde1.changeFiliale(neueFiliale);
        assertEquals(neueFiliale, kunde1.getFiliale());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeFilialeNull() {
        kunde1.changeFiliale(null);
    }

    // Gesamtkontostand Tests
    @Test
    public void testGesamtKontostandOhneKontos() {
        assertEquals(0, kunde1.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testGesamtKontostandMitKontos() {
        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        konto1.einzahlen(1000);
        konto2.einzahlen(500);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);

        assertEquals(1500, kunde1.getGesamtKontostand(), 0.01);
    }
}