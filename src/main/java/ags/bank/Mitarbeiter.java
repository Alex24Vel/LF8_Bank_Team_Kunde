package ags.bank;

import java.util.ArrayList;

public class Mitarbeiter extends Person {
    private int mitarbeiterNummer;
    private Filiale Filiale;
    private ArrayList<Kunde> kundenListe;

    public Mitarbeiter(String vorname, String nachname, String strasse, String hausNummer, String email) {
        super(vorname, nachname, strasse, hausNummer, email);
    }

    // Getters & Setters
    public int getMitarbeiterNummer() {
        return mitarbeiterNummer;
    }
    public void setMitarbeiterNummer(int mitarbeiterNummer) {
        this.mitarbeiterNummer = mitarbeiterNummer;
    }
    public Filiale getFiliale() {
        return Filiale;
    }
    public void setFiliale(Filiale filiale) {
        Filiale = filiale;
    }
    public ArrayList<Kunde> getKundenListe() {
        return kundenListe;
    }
    public void setKundenListe(ArrayList<Kunde> kundenListe) {
        this.kundenListe = kundenListe;
    }
}
