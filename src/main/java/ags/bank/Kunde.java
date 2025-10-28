package ags.bank;

import java.util.ArrayList;

public class Kunde extends Person {
    private int kundennummer;
    private Mitarbeiter betreuer;
    private ArrayList<Konto> kontos;
    private Filiale filiale;

    public final int MAX_KONTEN_ANZAHL = 5;

    public Kunde(String vorname, String nachname, String strasse, String hausNummer, String email,
                 int kundennummer, Mitarbeiter betreuer, ArrayList<Konto> kontos, Filiale filiale) {
        super(vorname, nachname, strasse, hausNummer, email);
        this.kundennummer = kundennummer;
        this.betreuer = betreuer;
        this.kontos = kontos;
        this.filiale = filiale;
    }

    // Getters & Setters
    public int getKundennummer() {
        return kundennummer;
    }
    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }
    public ArrayList<Konto> getKontos() {
        return kontos;
    }
    public void setKontos(ArrayList<Konto> kontos) {
        this.kontos = kontos;
    }
    public Mitarbeiter getBetreuer() {
        return betreuer;
    }
    public void setBetreuer(Mitarbeiter betreuer) {
        this.betreuer = betreuer;
    }
    public Filiale getFiliale() {
        return filiale;
    }
    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }
}
