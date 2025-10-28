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

    // ========== Find Kunde Tests ==========
    @Test
    public void testFindKundeByNummer() {
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

        Kunde gefunden = mitarbeiter1.findKundeByNummer(3001);
        assertNotNull(gefunden);
        assertEquals(kunde, gefunden);
    }

    @Test
    public void testFindKundeByNummerNichtGefunden() {
        assertNull(mitarbeiter1.findKundeByNummer(9999));
    }

    @Test
    public void testFindKundeByNummerMehrereKunden() {
        Kunde kunde1 = new Kunde(
                "Anna",
                "Schmidt",
                "Str1",
                "1",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        Kunde kunde2 = new Kunde(
                "Max",
                "Müller",
                "Str2",
                "2",
                "max@mail.de",
                3002,
                mitarbeiter1,
                filiale1
        );

        mitarbeiter1.addKunde(kunde1);
        mitarbeiter1.addKunde(kunde2);

        assertEquals(kunde2, mitarbeiter1.findKundeByNummer(3002));
    }

    // ========== Get Gesamt Verwaltetes Summe Tests ==========
    @Test
    public void testGetGesamtVerwalteteSummeOhneKunden() {
        assertEquals(0, mitarbeiter1.getGesamtVerwaltetesSumme(), 0.01);
    }

    @Test
    public void testGetGesamtVerwaltetesSumme() {
        Kunde kunde1 = new Kunde(
                "Anna",
                "Schmidt",
                "Str1",
                "1",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        Kunde kunde2 = new Kunde(
                "Max",
                "Müller",
                "Str2",
                "2",
                "max@mail.de",
                3002,
                mitarbeiter1,
                filiale1
        );

        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");
        Konto konto3 = new Konto(1003, "DE08700901001234567892");

        konto1.einzahlen(1000);
        konto2.einzahlen(2000);
        konto3.einzahlen(1500);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);
        kunde2.addKonto(konto3);

        mitarbeiter1.addKunde(kunde1);
        mitarbeiter1.addKunde(kunde2);

        assertEquals(4500, mitarbeiter1.getGesamtVerwaltetesSumme(), 0.01);
    }

    // ========== Kann Neue Kunden Aufnehmen Tests ==========
    @Test
    public void testKannNeueKundenAufnehmenLeer() {
        assertTrue(mitarbeiter1.kannNeueKundenAufnehmen(10));
    }

    @Test
    public void testKannNeueKundenAufnehmenUnterLimit() {
        for (int i = 1; i <= 5; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Nachname" + i,
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            mitarbeiter1.addKunde(kunde);
        }

        assertTrue(mitarbeiter1.kannNeueKundenAufnehmen(10));
        assertFalse(mitarbeiter1.kannNeueKundenAufnehmen(5));
    }

    @Test
    public void testKannNeueKundenAufnehmenAmLimit() {
        for (int i = 1; i <= 10; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Nachname" + i,
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            mitarbeiter1.addKunde(kunde);
        }

        assertFalse(mitarbeiter1.kannNeueKundenAufnehmen(10));
    }

    // ========== Realistic Scenarios ==========
    @Test
    public void testMitarbeiterUeberniммtKundenVonAnderem() {
        Mitarbeiter mitarbeiter2 = new Mitarbeiter(
                "Lisa",
                "Müller",
                "Marktplatz",
                "3",
                "lisa@bank.de",
                2002
        );

        Kunde kunde = new Kunde(
                "Anna",
                "Schmidt",
                "Str1",
                "1",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );

        mitarbeiter1.addKunde(kunde);
        assertEquals(1, mitarbeiter1.getAnzahlKunden());

        // Customer changes betreuer
        kunde.changeBetreuer(mitarbeiter2);

        assertEquals(0, mitarbeiter1.getAnzahlKunden());
        assertEquals(1, mitarbeiter2.getAnzahlKunden());
        assertEquals(mitarbeiter2, kunde.getBetreuer());
    }

    @Test
    public void testMitarbeiterWechseltFiliale() {
        Filiale filiale2 = new Filiale(200);

        mitarbeiter1.addFiliale(filiale1);
        assertEquals(1, mitarbeiter1.getFilialeListe().size());
        assertTrue(filiale1.getMitarbeiterListe().contains(mitarbeiter1));

        // Add to second branch
        mitarbeiter1.addFiliale(filiale2);
        assertEquals(2, mitarbeiter1.getFilialeListe().size());
        assertTrue(filiale2.getMitarbeiterListe().contains(mitarbeiter1));
    }

    @Test
    public void testMitarbeiterVerlasstFiliale() {
        mitarbeiter1.addFiliale(filiale1);

        Kunde kunde = new Kunde(
                "Anna",
                "Schmidt",
                "Str1",
                "1",
                "anna@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        mitarbeiter1.addKunde(kunde);
        filiale1.addKunde(kunde);

        assertTrue(mitarbeiter1.removeFiliale(filiale1));
        assertEquals(0, mitarbeiter1.getFilialeListe().size());
        assertFalse(filiale1.getMitarbeiterListe().contains(mitarbeiter1));

        // Customer should still be assigned to mitarbeiter
        assertEquals(1, mitarbeiter1.getAnzahlKunden());
    }

    @Test
    public void testMitarbeiterMitVielenKundenUndHohemVermoegen() {
        for (int i = 1; i <= 20; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Nachname" + i,
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );

            Konto konto = new Konto(
                    1000 + i,
                    String.format("DE0870090100%010d", i)
            );
            konto.einzahlen(10000 + i * 100);

            kunde.addKonto(konto);
            mitarbeiter1.addKunde(kunde);
        }

        assertEquals(20, mitarbeiter1.getAnzahlKunden());

        // Total: sum from i=1 to 20 of (10000 + i*100)
        // = 20*10000 + 100*(1+2+...+20) = 200000 + 100*210 = 221000
        assertEquals(221000, mitarbeiter1.getGesamtVerwaltetesSumme(), 0.01);
    }

    @Test
    public void testMitarbeiterBearbeitetUeberweisung() {
        Kunde sender = new Kunde(
                "Sender",
                "Schmidt",
                "Str1",
                "1",
                "sender@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        Kunde empfaenger = new Kunde(
                "Empfaenger",
                "Müller",
                "Str2",
                "2",
                "empfaenger@mail.de",
                3002,
                mitarbeiter1,
                filiale1
        );

        Konto senderKonto = new Konto(1001, "DE08700901001234567890");
        Konto empfaengerKonto = new Konto(1002, "DE08700901001234567891");

        senderKonto.einzahlen(5000);

        sender.addKonto(senderKonto);
        empfaenger.addKonto(empfaengerKonto);

        mitarbeiter1.addKunde(sender);
        mitarbeiter1.addKunde(empfaenger);

        // Transfer
        assertTrue(senderKonto.ueberweisen(empfaengerKonto, 2000));

        // Total managed money stays the same
        assertEquals(5000, mitarbeiter1.getGesamtVerwaltetesSumme(), 0.01);

        // But distribution changed
        assertEquals(3000, sender.getGesamtKontostand(), 0.01);
        assertEquals(2000, empfaenger.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testMitarbeiterHatKundenInMehrerenFilialen() {
        Filiale filiale2 = new Filiale(200);

        mitarbeiter1.addFiliale(filiale1);
        mitarbeiter1.addFiliale(filiale2);

        Kunde kunde1 = new Kunde(
                "Kunde1",
                "Schmidt",
                "Str1",
                "1",
                "kunde1@mail.de",
                3001,
                mitarbeiter1,
                filiale1
        );
        Kunde kunde2 = new Kunde(
                "Kunde2",
                "Müller",
                "Str2",
                "2",
                "kunde2@mail.de",
                3002,
                mitarbeiter1,
                filiale2
        );

        mitarbeiter1.addKunde(kunde1);
        mitarbeiter1.addKunde(kunde2);
        filiale1.addKunde(kunde1);
        filiale2.addKunde(kunde2);

        assertEquals(2, mitarbeiter1.getAnzahlKunden());
        assertEquals(2, mitarbeiter1.getFilialeListe().size());
        assertEquals(1, filiale1.getAnzahlKunden());
        assertEquals(1, filiale2.getAnzahlKunden());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKannNeueKundenAufnehmenNegativesLimit() {
        mitarbeiter1.kannNeueKundenAufnehmen(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKannNeueKundenAufnehmenNullLimit() {
        mitarbeiter1.kannNeueKundenAufnehmen(0);
    }
}