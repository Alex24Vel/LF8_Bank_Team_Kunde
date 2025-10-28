package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class FilialeTest {
    private Filiale filiale1;
    private Mitarbeiter mitarbeiter1;
    private Mitarbeiter mitarbeiter2;

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
        mitarbeiter2 = new Mitarbeiter(
                "Lisa",
                "Müller",
                "Marktplatz",
                "3",
                "lisa@bank.de",
                2002
        );
    }

    // ========== Find Methods Tests ==========
    @Test
    public void testFindKundeByNummer() {
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
        filiale1.addKunde(kunde);

        Kunde gefunden = filiale1.findKundeByNummer(3001);
        assertNotNull(gefunden);
        assertEquals(kunde, gefunden);
    }

    @Test
    public void testFindKundeByNummerNichtGefunden() {
        assertNull(filiale1.findKundeByNummer(9999));
    }

    @Test
    public void testFindMitarbeiterByNummer() {
        filiale1.addMitarbeiter(mitarbeiter1);

        Mitarbeiter gefunden = filiale1.findMitarbeiterByNummer(2001);
        assertNotNull(gefunden);
        assertEquals(mitarbeiter1, gefunden);
    }

    @Test
    public void testFindMitarbeiterByNummerNichtGefunden() {
        assertNull(filiale1.findMitarbeiterByNummer(9999));
    }

    // ========== Gesamt Filial Vermoegen Tests ==========
    @Test
    public void testGetGesamtFilialVermoegenLeer() {
        assertEquals(0, filiale1.getGesamtFilialVermoegen(), 0.01);
    }

    @Test
    public void testGetGesamtFilialVermoegen() {
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
                "Weber",
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

        konto1.einzahlen(10000);
        konto2.einzahlen(5000);
        konto3.einzahlen(8000);

        kunde1.addKonto(konto1);
        kunde1.addKonto(konto2);
        kunde2.addKonto(konto3);

        filiale1.addKunde(kunde1);
        filiale1.addKunde(kunde2);

        assertEquals(23000, filiale1.getGesamtFilialVermoegen(), 0.01);
    }

    // ========== Mitarbeiter Performance Tests ==========
    @Test
    public void testGetMitarbeiterMitMeistenKunden() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

        for (int i = 1; i <= 5; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Schmidt",
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            mitarbeiter1.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        for (int i = 6; i <= 7; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Müller",
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter2,
                    filiale1
            );
            mitarbeiter2.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        assertEquals(mitarbeiter1, filiale1.getMitarbeiterMitMeistenKunden());
    }

    @Test
    public void testGetMitarbeiterMitMeistenKundenOhneMitarbeiter() {
        assertNull(filiale1.getMitarbeiterMitMeistenKunden());
    }

    @Test
    public void testGetMitarbeiterMitWenigstenKunden() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

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
                filiale1
        );
        Kunde kunde3 = new Kunde(
                "Kunde3",
                "Weber",
                "Str3",
                "3",
                "kunde3@mail.de",
                3003,
                mitarbeiter2,
                filiale1
        );

        mitarbeiter1.addKunde(kunde1);
        mitarbeiter1.addKunde(kunde2);
        mitarbeiter2.addKunde(kunde3);

        filiale1.addKunde(kunde1);
        filiale1.addKunde(kunde2);
        filiale1.addKunde(kunde3);

        assertEquals(mitarbeiter2, filiale1.getMitarbeiterMitWenigstenKunden());
    }

    @Test
    public void testGetMitarbeiterMitWenigstenKundenOhneMitarbeiter() {
        assertNull(filiale1.getMitarbeiterMitWenigstenKunden());
    }

    // ========== Kunden Ausgleichen Tests ==========
    @Test
    public void testKundenAusgleichen() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

        // Mitarbeiter1 hat 6 Kunden
        for (int i = 1; i <= 6; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Schmidt",
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            mitarbeiter1.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        // Mitarbeiter2 hat 0 Kunden
        assertEquals(6, mitarbeiter1.getAnzahlKunden());
        assertEquals(0, mitarbeiter2.getAnzahlKunden());

        // Balance customers
        filiale1.kundenAusgleichen();

        // After balancing, both should have 3 customers
        assertEquals(3, mitarbeiter1.getAnzahlKunden());
        assertEquals(3, mitarbeiter2.getAnzahlKunden());
    }

    @Test
    public void testKundenAusgleichenBereitsAusgeglichen() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

        for (int i = 1; i <= 2; i++) {
            Kunde kunde1 = new Kunde(
                    "Kunde" + i,
                    "Schmidt",
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            mitarbeiter1.addKunde(kunde1);
            filiale1.addKunde(kunde1);

            Kunde kunde2 = new Kunde(
                    "Kunde" + (i + 10),
                    "Müller",
                    "Str" + (i + 10),
                    String.valueOf(i + 10),
                    "kunde" + (i + 10) + "@mail.de",
                    3010 + i,
                    mitarbeiter2,
                    filiale1
            );
            mitarbeiter2.addKunde(kunde2);
            filiale1.addKunde(kunde2);
        }

        assertEquals(2, mitarbeiter1.getAnzahlKunden());
        assertEquals(2, mitarbeiter2.getAnzahlKunden());

        filiale1.kundenAusgleichen();

        // Should remain the same
        assertEquals(2, mitarbeiter1.getAnzahlKunden());
        assertEquals(2, mitarbeiter2.getAnzahlKunden());
    }

    // ========== Realistic Scenarios ==========
    @Test
    public void testFilialeUebertragtKundeAnAndereFiliale() {
        Filiale filiale2 = new Filiale(200);

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

        Konto konto = new Konto(1001, "DE08700901001234567890");
        konto.einzahlen(5000);
        kunde.addKonto(konto);

        filiale1.addKunde(kunde);
        mitarbeiter1.addFiliale(filiale1);

        assertEquals(1, filiale1.getAnzahlKunden());
        assertEquals(5000, filiale1.getGesamtFilialVermoegen(), 0.01);

        // Customer moves to new branch
        kunde.changeFiliale(filiale2);

        assertEquals(0, filiale1.getAnzahlKunden());
        assertEquals(1, filiale2.getAnzahlKunden());
        assertEquals(0, filiale1.getGesamtFilialVermoegen(), 0.01);
        assertEquals(5000, filiale2.getGesamtFilialVermoegen(), 0.01);
    }

    @Test
    public void testFilialeVerarbeitetInterneUeberweisungen() {
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

        senderKonto.einzahlen(10000);

        sender.addKonto(senderKonto);
        empfaenger.addKonto(empfaengerKonto);

        filiale1.addKunde(sender);
        filiale1.addKunde(empfaenger);

        assertEquals(10000, filiale1.getGesamtFilialVermoegen(), 0.01);

        // Internal transfer
        senderKonto.ueberweisen(empfaengerKonto, 3000);

        // Total branch assets remain the same
        assertEquals(10000, filiale1.getGesamtFilialVermoegen(), 0.01);
        assertEquals(7000, sender.getGesamtKontostand(), 0.01);
        assertEquals(3000, empfaenger.getGesamtKontostand(), 0.01);
    }

    @Test
    public void testFilialeSchliesstKonto() {
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

        Konto konto1 = new Konto(1001, "DE08700901001234567890");
        Konto konto2 = new Konto(1002, "DE08700901001234567891");

        konto1.einzahlen(500);
        konto2.einzahlen(1000);

        kunde.addKonto(konto1);
        kunde.addKonto(konto2);
        filiale1.addKunde(kunde);

        assertEquals(2, filiale1.getAllKontos().size());
        assertEquals(1500, filiale1.getGesamtFilialVermoegen(), 0.01);

        // Transfer all money from konto1 to konto2
        konto1.ueberweisen(konto2, 500);

        // Remove empty account
        kunde.removeKonto(konto1);

        assertEquals(1, filiale1.getAllKontos().size());
        assertEquals(1500, filiale1.getGesamtFilialVermoegen(), 0.01);
    }

    @Test
    public void testFilialeMitMehrerenMitarbeiternUndKunden() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

        for (int i = 1; i <= 10; i++) {
            Mitarbeiter betreuer = (i <= 5) ? mitarbeiter1 : mitarbeiter2;

            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Nachname" + i,
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    betreuer,
                    filiale1
            );

            Konto konto = new Konto(
                    1000 + i,
                    String.format("DE0870090100%010d", i)
            );
            konto.einzahlen(1000 * i);

            kunde.addKonto(konto);
            betreuer.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        assertEquals(10, filiale1.getAnzahlKunden());
        assertEquals(2, filiale1.getAnzahlMitarbeiter());
        assertEquals(10, filiale1.getAllKontos().size());

        // Total: 1000*1 + 1000*2 + ... + 1000*10 = 1000*(1+2+...+10) = 55000
        assertEquals(55000, filiale1.getGesamtFilialVermoegen(), 0.01);

        assertEquals(5, mitarbeiter1.getAnzahlKunden());
        assertEquals(5, mitarbeiter2.getAnzahlKunden());
    }

    @Test
    public void testFilialeStatistiken() {
        filiale1.addMitarbeiter(mitarbeiter1);
        filiale1.addMitarbeiter(mitarbeiter2);

        // Create diverse customer portfolio
        for (int i = 1; i <= 3; i++) {
            Kunde kunde = new Kunde(
                    "Reich" + i,
                    "Kunde",
                    "Str" + i,
                    String.valueOf(i),
                    "reich" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );

            Konto konto = new Konto(
                    1000 + i,
                    String.format("DE0870090100%010d", i)
            );
            konto.einzahlen(50000);

            kunde.addKonto(konto);
            mitarbeiter1.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        for (int i = 4; i <= 7; i++) {
            Kunde kunde = new Kunde(
                    "Normal" + i,
                    "Kunde",
                    "Str" + i,
                    String.valueOf(i),
                    "normal" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter2,
                    filiale1
            );

            Konto konto = new Konto(
                    1000 + i,
                    String.format("DE0870090100%010d", i)
            );
            konto.einzahlen(5000);

            kunde.addKonto(konto);
            mitarbeiter2.addKunde(kunde);
            filiale1.addKunde(kunde);
        }

        assertEquals(7, filiale1.getAnzahlKunden());
        assertEquals(170000, filiale1.getGesamtFilialVermoegen(), 0.01);
        assertEquals(mitarbeiter2, filiale1.getMitarbeiterMitMeistenKunden());
    }

    @Test
    public void testFilialeOhneKundenUndMitarbeiter() {
        assertEquals(0, filiale1.getAnzahlKunden());
        assertEquals(0, filiale1.getAnzahlMitarbeiter());
        assertEquals(0, filiale1.getGesamtFilialVermoegen(), 0.01);
        assertEquals(0, filiale1.getAllKontos().size());
        assertNull(filiale1.getMitarbeiterMitMeistenKunden());
        assertNull(filiale1.getMitarbeiterMitWenigstenKunden());
    }

    @Test
    public void testFilialeEntferntAlleKunden() {
        for (int i = 1; i <= 5; i++) {
            Kunde kunde = new Kunde(
                    "Kunde" + i,
                    "Schmidt",
                    "Str" + i,
                    String.valueOf(i),
                    "kunde" + i + "@mail.de",
                    3000 + i,
                    mitarbeiter1,
                    filiale1
            );
            filiale1.addKunde(kunde);
        }

        assertEquals(5, filiale1.getAnzahlKunden());

        // Create a copy to avoid ConcurrentModificationException
        ArrayList<Kunde> kundenCopy = new ArrayList<>(filiale1.getKundenListe());

        // Remove all customers
        for (Kunde kunde : kundenCopy) {
            filiale1.removeKunde(kunde);
        }

        assertEquals(0, filiale1.getAnzahlKunden());
        assertEquals(0, filiale1.getAllKontos().size());
    }
}