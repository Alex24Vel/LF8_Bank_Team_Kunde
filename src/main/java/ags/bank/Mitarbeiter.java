package ags.bank;

import java.util.ArrayList;

public class Mitarbeiter extends Person {
    private int mitarbeiterNummer;
    private ArrayList<Kunde> kundenListe;
    private ArrayList<Filiale> filialeListe;

    public Mitarbeiter(String vorname, String nachname, String strasse, String hausNummer, String email, ArrayList<Filiale> filialeListe) {
        super(vorname, nachname, strasse, hausNummer, email);
        this.filialeListe = filialeListe;
    }

    // Getters & Setters
    public int getMitarbeiterNummer() {
        return mitarbeiterNummer;
    }
    public void setMitarbeiterNummer(int mitarbeiterNummer) {
        this.mitarbeiterNummer = mitarbeiterNummer;
    }
    public ArrayList<Kunde> getKundenListe() {
        return kundenListe;
    }
    public void setKundenListe(ArrayList<Kunde> kundenListe) {
        this.kundenListe = kundenListe;
    }
    public ArrayList<Filiale> getFilialeListe() {
        return filialeListe;
    }
    public void setFilialeListe(ArrayList<Filiale> filialeListe) {
        this.filialeListe = filialeListe;
    }
}
