package ags.bank;

import java.util.ArrayList;

public class Kunde extends Person {
    private int kundennummer;
    private ArrayList<Konto> kontos;
    private Filiale filiale;
    private Mitarbeiter betreuer;

    public final int MAX_KONTEN_ANZAHL = 5;

    public Kunde(String vorname, String nachname, String strasse, String hausNummer, String email,
                 int kundennummer, ArrayList<Konto> kontos, Filiale filiale, Mitarbeiter betreuer) {
        super(vorname, nachname, strasse, hausNummer, email);
        this.kundennummer = kundennummer;
        this.kontos = kontos;
        this.filiale = filiale;
        this.betreuer = betreuer;
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
    public Filiale getFiliale() {
        return filiale;
    }
    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }
    public Mitarbeiter getBetreuer() {
        return betreuer;
    }
    public void setBetreuer(Mitarbeiter betreuer) {
        this.betreuer = betreuer;
    }
}
