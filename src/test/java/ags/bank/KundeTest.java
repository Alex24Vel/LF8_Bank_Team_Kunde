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

    // ========== Find Konto Tests ==========
    @Test
    public void testFindKontoByNummer() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        kunde1.addKonto(konto);

        Konto gefunden = kunde1.findKontoByNummer(1001);
        assertNotNull(gefunden);
        assertEquals(konto, gefunden);
    }

    @Test
    public void testFindKontoByNummerNichtGefunden() {
        assertNull(kunde1.findKontoByNummer(9999));
    }

    @Test
    public void testFindKontoByNummerMehrereKontos() {
        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");
        Konto konto3 = new Konto(1003, "DE08700901001234567892");

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);
        kunde1.addKonto(konto3);

        assertEquals(konto2, kunde1.findKontoByNummer(1002));
    }

    @Test
    public void testFindKontoByIBAN() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        kunde1.addKonto(konto);

        Konto gefunden = kunde1.findKontoByIBAN("DE08700901001234567890");
        assertNotNull(gefunden);
        assertEquals(konto, gefunden);
    }

    @Test
    public void testFindKontoByIBANNichtGefunden() {
        assertNull(kunde1.findKontoByIBAN("DE08700901009999999999"));
    }

    @Test
    public void testFindKontoByIBANNull() {
        assertNull(kunde1.findKontoByIBAN(null));
    }

    // ========== Has Guthaben Tests ==========
    @Test
    public void testHatGuthabenOhneKontos() {
        assertFalse(kunde1.hatGuthaben());
    }

    @Test
    public void testHatGuthabenMitLeerenKontos() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        kunde1.addKonto(konto);
        assertFalse(kunde1.hatGuthaben());
    }

    @Test
    public void testHatGuthabenMitGeld() {
        Konto konto = new Konto(1001, "DE08700901001234567890");
        konto.einzahlen(100);
        kunde1.addKonto(konto);
        assertTrue(kunde1.hatGuthaben());
    }

    @Test
    public void testHatGuthabenMehrereKontos() {
        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        konto2.einzahlen(50);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);

        assertTrue(kunde1.hatGuthaben());
    }

    // ========== Get Anzahl Kontos Tests ==========
    @Test
    public void testGetAnzahlKontosLeer() {
        assertEquals(0, kunde1.getAnzahlKontos());
    }

    @Test
    public void testGetAnzahlKontos() {
        kunde1.addKonto(new Konto(1001, "DE08700901001234567890"));
        kunde1.addKonto(new Konto(1002, "DE08700901001234567891"));
        assertEquals(2, kunde1.getAnzahlKontos());
    }

    // ========== Realistic Scenarios ==========
    @Test
    public void testKundeWechseltBetreuerMitKontos() {
        // Kunde hat bereits Kontos
        Konto konto = new Konto(1001, "DE08700901001234567890");
        konto.einzahlen(5000);
        kunde1.addKonto(konto);

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
        assertTrue(neuerBetreuer.getKundenListe().contains(kunde1));
        assertFalse(betreuer1.getKundenListe().contains(kunde1));
        assertEquals(5000, kunde1.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testKundeWechseltFilialeMitKontos() {
        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        konto1.einzahlen(1000);
        konto2.einzahlen(2000);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);

        Filiale neueFiliale = new Filiale(200);
        kunde1.changeFiliale(neueFiliale);

        assertEquals(neueFiliale, kunde1.getFiliale());
        assertTrue(neueFiliale.getKundenListe().contains(kunde1));
        assertFalse(filiale1.getKundenListe().contains(kunde1));
        assertEquals(2, kunde1.getKontos().size());
    }

    @Test
    public void testTransferZwischenEigenenKontos() {
        Konto girokonto = new Konto(1001, "DE08700901001234567890");
        Konto sparkonto = new Konto(1002, "DE08700901001234567891");

        kunde1.addKonto(girokonto);
        kunde1.addKonto(sparkonto);

        girokonto.einzahlen(1000);
        girokonto.ueberweisen(sparkonto, 300);

        assertEquals(700, girokonto.getKontostand(), 0.01);
        assertEquals(300, sparkonto.getKontostand(), 0.01);
        assertEquals(1000, kunde1.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testKundeSchliesstKontoMitGuthaben() {
        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        konto1.einzahlen(500);
        konto2.einzahlen(200);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);

        // Transfer all money from konto1 to konto2
        konto1.ueberweisen(konto2, 500);

        // Now konto1 has 0 balance and can be removed
        assertEquals(0, konto1.getKontostand(), 0.01);
        assertTrue(kunde1.removeKonto(konto1));
        assertEquals(1, kunde1.getAnzahlKontos());
        assertEquals(700, kunde1.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testKundeErreichtKontoLimit() {
        // Add 5 accounts (maximum)
        for (int i = 1; i <= 5; i++) {
            Konto konto = new Konto(1000 + i, "DE0870090100123456789" + i);
            konto.einzahlen(100 * i);
            assertTrue(kunde1.addKonto(konto));
        }

        assertEquals(5, kunde1.getAnzahlKontos());
        assertFalse(kunde1.canAddKonto());

        // Try to add 6th account
        Konto konto6 = new Konto(1006, "DE08700901001234567896");
        assertFalse(kunde1.addKonto(konto6));

        // Total should be 100 + 200 + 300 + 400 + 500 = 1500
        assertEquals(1500, kunde1.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testKundeLoeschtKontoUndFuegtNeuesHinzu() {
        Konto altesKonto = new Konto(1001, "DE08700901001234567890");
        kunde1.addKonto(altesKonto);

        assertTrue(kunde1.removeKonto(altesKonto));

        Konto neuesKonto = new Konto(1002, "DE08700901001234567891");
        assertTrue(kunde1.addKonto(neuesKonto));

        assertEquals(1, kunde1.getAnzahlKontos());
        assertNull(kunde1.findKontoByNummer(1001));
        assertNotNull(kunde1.findKontoByNummer(1002));
    }
}