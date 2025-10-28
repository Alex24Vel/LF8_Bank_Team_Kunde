package ags.bank;

import java.util.ArrayList;

public class Mitarbeiter extends Person {
    private int mitarbeiterNummer;
    private ArrayList<Kunde> kundenListe;
    private ArrayList<Filiale> filialeListe;

    public Mitarbeiter(
            String vorname,
            String nachname,
            String strasse,
            String hausNummer,
            String email,
            int mitarbeiterNummer
    ) {
        super(vorname, nachname, strasse, hausNummer, email);
        this.mitarbeiterNummer = mitarbeiterNummer;
        this.kundenListe = new ArrayList<>();
        this.filialeListe = new ArrayList<>();
    }

    // Getters
    public int getMitarbeiterNummer() {
        return mitarbeiterNummer;
    }

    public ArrayList<Kunde> getKundenListe() {
        return new ArrayList<>(kundenListe); // defensive copy
    }

    public ArrayList<Filiale> getFilialeListe() {
        return new ArrayList<>(filialeListe); // defensive copy
    }

    // Kunden Management
    public boolean addKunde(Kunde kunde) {
        if (kunde == null) {
            return false;
        }

        // Prüfen ob Kunde bereits vorhanden
        if (kundenListe.contains(kunde)) {
            return false;
        }

        kundenListe.add(kunde);
        return true;
    }

    public boolean removeKunde(Kunde kunde) {
        if (kunde == null) {
            return false;
        }
        return kundenListe.remove(kunde);
    }

    // Filialen Management
    public boolean addFiliale(Filiale filiale) {
        if (filiale == null) {
            return false;
        }

        // Prüfen ob Filiale bereits vorhanden
        if (filialeListe.contains(filiale)) {
            return false;
        }

        filialeListe.add(filiale);

        // Bidirektionale Beziehung: Mitarbeiter auch zur Filiale hinzufügen
        if (!filiale.getMitarbeiterListe().contains(this)) {
            filiale.addMitarbeiter(this);
        }

        return true;
    }

    public boolean removeFiliale(Filiale filiale) {
        if (filiale == null) {
            return false;
        }

        boolean removed = filialeListe.remove(filiale);

        // Bidirektionale Beziehung: Mitarbeiter auch aus Filiale entfernen
        if (removed && filiale.getMitarbeiterListe().contains(this)) {
            filiale.removeMitarbeiter(this);
        }

        return removed;
    }

    // Statistiken
    public int getAnzahlKunden() {
        return kundenListe.size();
    }
}