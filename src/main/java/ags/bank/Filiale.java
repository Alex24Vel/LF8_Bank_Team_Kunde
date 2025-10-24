package ags.bank;

import java.util.ArrayList;

public class Filiale {
    private int filialeNummer;
    private ArrayList<Kunde> kundenListe;
    private ArrayList<Mitarbeiter> mitarbeiterListe;

    public Filiale(int filialeNummer, ArrayList<Kunde> kundenListe, ArrayList<Mitarbeiter> mitarbeiterListe) {
        this.filialeNummer = filialeNummer;
        this.kundenListe = kundenListe;
        this.mitarbeiterListe = mitarbeiterListe;
    }

    public int getFilialeNummer() {
        return filialeNummer;
    }
    public void setFilialeNummer(int filialeNummer) {
        this.filialeNummer = filialeNummer;
    }
    public ArrayList<Kunde> getKundenListe() {
        return kundenListe;
    }
    public void setKundenListe(ArrayList<Kunde> kundenListe) {
        this.kundenListe = kundenListe;
    }
    public ArrayList<Mitarbeiter> getMitarbeiterListe() {
        return mitarbeiterListe;
    }
    public void setMitarbeiterListe(ArrayList<Mitarbeiter> mitarbeiterListe) {
        this.mitarbeiterListe = mitarbeiterListe;
    }
}
