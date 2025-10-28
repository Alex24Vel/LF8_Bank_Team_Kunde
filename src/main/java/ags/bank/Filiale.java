package ags.bank;

import java.util.ArrayList;

public class Filiale {
    private int filialeNummer;
    private ArrayList<Kunde> kundenListe;
    private ArrayList<Mitarbeiter> mitarbeiterListe;

    public Filiale(int filialeNummer) {
        this.filialeNummer = filialeNummer;
        this.kundenListe = new ArrayList<>();
        this.mitarbeiterListe = new ArrayList<>();
    }

    // Getters
    public int getFilialeNummer() {
        return filialeNummer;
    }

    public ArrayList<Kunde> getKundenListe() {
        return new ArrayList<>(kundenListe); // defensive copy
    }

    public ArrayList<Mitarbeiter> getMitarbeiterListe() {
        return new ArrayList<>(mitarbeiterListe); // defensive copy
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

    // Mitarbeiter Management
    public boolean addMitarbeiter(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) {
            return false;
        }

        // Prüfen ob Mitarbeiter bereits vorhanden
        if (mitarbeiterListe.contains(mitarbeiter)) {
            return false;
        }

        mitarbeiterListe.add(mitarbeiter);

        // Bidirektionale Beziehung: Filiale auch zum Mitarbeiter hinzufügen
        if (!mitarbeiter.getFilialeListe().contains(this)) {
            mitarbeiter.addFiliale(this);
        }

        return true;
    }

    public boolean removeMitarbeiter(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) {
            return false;
        }

        boolean removed = mitarbeiterListe.remove(mitarbeiter);

        // Bidirektionale Beziehung: Filiale auch beim Mitarbeiter entfernen
        if (removed && mitarbeiter.getFilialeListe().contains(this)) {
            mitarbeiter.removeFiliale(this);
        }

        return removed;
    }

    // Statistiken
    public int getAnzahlKunden() {
        return kundenListe.size();
    }

    public int getAnzahlMitarbeiter() {
        return mitarbeiterListe.size();
    }

    public ArrayList<Konto> getAllKontos() {
        ArrayList<Konto> alleKontos = new ArrayList<>();

        for (Kunde kunde : kundenListe) {
            alleKontos.addAll(kunde.getKontos());
        }

        return alleKontos;
    }
}