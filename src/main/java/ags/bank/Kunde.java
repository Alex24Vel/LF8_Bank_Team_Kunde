package ags.bank;

import java.util.ArrayList;

public class Kunde extends Person {
    private int kundennummer;
    private Mitarbeiter betreuer;
    private ArrayList<Konto> kontos;
    private Filiale filiale;

    public final int MAX_KONTEN_ANZAHL = 5;

    public Kunde(
            String vorname,
            String nachname,
            String strasse,
            String hausNummer,
            String email,
            int kundennummer,
            Mitarbeiter betreuer,
            Filiale filiale
    ) {
        super(vorname, nachname, strasse, hausNummer, email);
        this.kundennummer = kundennummer;
        this.betreuer = betreuer;
        this.kontos = new ArrayList<>();
        this.filiale = filiale;
    }

    // Getters
    public int getKundennummer() {
        return kundennummer;
    }

    public ArrayList<Konto> getKontos() {
        return new ArrayList<>(kontos); // defensive copy
    }

    public Mitarbeiter getBetreuer() {
        return betreuer;
    }

    public Filiale getFiliale() {
        return filiale;
    }

    // Konto Management
    public boolean addKonto(Konto konto) {
        if (konto == null) {
            return false;
        }

        // Prüfen ob Konto bereits vorhanden
        if (kontos.contains(konto)) {
            return false;
        }

        // Prüfen ob Maximum erreicht
        if (kontos.size() >= MAX_KONTEN_ANZAHL) {
            return false;
        }

        kontos.add(konto);
        return true;
    }

    public boolean removeKonto(Konto konto) {
        if (konto == null) {
            return false;
        }
        return kontos.remove(konto);
    }

    public boolean canAddKonto() {
        return kontos.size() < MAX_KONTEN_ANZAHL;
    }

    // Betreuer und Filiale Management
    public void changeBetreuer(Mitarbeiter neuerBetreuer) {
        if (neuerBetreuer == null) {
            throw new IllegalArgumentException("Betreuer darf nicht null sein");
        }

        // Alte Beziehung auflösen
        if (this.betreuer != null) {
            this.betreuer.removeKunde(this);
        }

        // Neue Beziehung aufbauen
        this.betreuer = neuerBetreuer;
        neuerBetreuer.addKunde(this);
    }

    public void changeFiliale(Filiale neueFiliale) {
        if (neueFiliale == null) {
            throw new IllegalArgumentException("Filiale darf nicht null sein");
        }

        // Alte Beziehung auflösen
        if (this.filiale != null) {
            this.filiale.removeKunde(this);
        }

        // Neue Beziehung aufbauen
        this.filiale = neueFiliale;
        neueFiliale.addKunde(this);
    }

    // Berechnungen
    public double getGesamtKontostand() {
        double summe = 0;
        for (Konto konto : kontos) {
            summe += konto.getKontostand();
        }
        return summe;
    }

    // ========== PHASE 2 METHODS ==========

    /**
     * Sucht ein Konto anhand der Kontonummer
     *
     * @param kontoNummer Die zu suchende Kontonummer
     * @return Das gefundene Konto oder null wenn nicht gefunden
     */
    public Konto findKontoByNummer(int kontoNummer) {
        for (Konto konto : kontos) {
            if (konto.getKontoNummer() == kontoNummer) {
                return konto;
            }
        }
        return null;
    }

    /**
     * Sucht ein Konto anhand der IBAN
     *
     * @param iban Die zu suchende IBAN
     * @return Das gefundene Konto oder null wenn nicht gefunden
     */
    public Konto findKontoByIBAN(String iban) {
        if (iban == null) {
            return null;
        }

        for (Konto konto : kontos) {
            if (iban.equals(konto.getIBAN())) {
                return konto;
            }
        }
        return null;
    }

    /**
     * Prüft ob der Kunde Guthaben auf mindestens einem Konto hat
     *
     * @return true wenn mindestens ein Konto Guthaben > 0 hat, sonst false
     */
    public boolean hatGuthaben() {
        for (Konto konto : kontos) {
            if (konto.getKontostand() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt die Anzahl der Kontos des Kunden zurück
     *
     * @return Anzahl der Kontos
     */
    public int getAnzahlKontos() {
        return kontos.size();
    }
}