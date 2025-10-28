package ags.bank;

import java.util.ArrayList;

public class Mitarbeiter extends Person {
    private int mitarbeiterNummer;
    private ArrayList<Kunde> kundenListe;

    public Mitarbeiter(String vorname, String nachname, String strasse, String hausNummer, String email, Filiale filiale) {
        super(vorname, nachname, strasse, hausNummer, email, filiale);
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
}
