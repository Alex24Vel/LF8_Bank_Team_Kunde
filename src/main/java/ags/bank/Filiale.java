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

    // ========== PHASE 2 METHODS ==========

    /**
     * Sucht einen Kunden in der Filiale anhand der Kundennummer
     *
     * @param kundennummer Die zu suchende Kundennummer
     * @return Der gefundene Kunde oder null wenn nicht gefunden
     */
    public Kunde findKundeByNummer(int kundennummer) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getKundennummer() == kundennummer) {
                return kunde;
            }
        }
        return null;
    }

    /**
     * Sucht einen Mitarbeiter in der Filiale anhand der Mitarbeiternummer
     *
     * @param mitarbeiterNummer Die zu suchende Mitarbeiternummer
     * @return Der gefundene Mitarbeiter oder null wenn nicht gefunden
     */
    public Mitarbeiter findMitarbeiterByNummer(int mitarbeiterNummer) {
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getMitarbeiterNummer() == mitarbeiterNummer) {
                return mitarbeiter;
            }
        }
        return null;
    }

    /**
     * Berechnet das Gesamtvermögen aller Kunden in dieser Filiale
     *
     * @return Summe aller Kontostände aller Kunden
     */
    public double getGesamtFilialVermoegen() {
        double summe = 0;
        for (Kunde kunde : kundenListe) {
            summe += kunde.getGesamtKontostand();
        }
        return summe;
    }

    /**
     * Findet den Mitarbeiter mit den meisten Kunden
     *
     * @return Mitarbeiter mit den meisten Kunden oder null wenn keine
     *     Mitarbeiter
     */
    public Mitarbeiter getMitarbeiterMitMeistenKunden() {
        if (mitarbeiterListe.isEmpty()) {
            return null;
        }

        Mitarbeiter mitMeisten = mitarbeiterListe.get(0);
        int maxKunden = mitMeisten.getAnzahlKunden();

        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            int anzahl = mitarbeiter.getAnzahlKunden();
            if (anzahl > maxKunden) {
                maxKunden = anzahl;
                mitMeisten = mitarbeiter;
            }
        }

        return mitMeisten;
    }

    /**
     * Findet den Mitarbeiter mit den wenigsten Kunden
     *
     * @return Mitarbeiter mit den wenigsten Kunden oder null wenn keine
     *     Mitarbeiter
     */
    public Mitarbeiter getMitarbeiterMitWenigstenKunden() {
        if (mitarbeiterListe.isEmpty()) {
            return null;
        }

        Mitarbeiter mitWenigsten = mitarbeiterListe.get(0);
        int minKunden = mitWenigsten.getAnzahlKunden();

        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            int anzahl = mitarbeiter.getAnzahlKunden();
            if (anzahl < minKunden) {
                minKunden = anzahl;
                mitWenigsten = mitarbeiter;
            }
        }

        return mitWenigsten;
    }

    /**
     * Verteilt Kunden gleichmäßig unter allen Mitarbeitern der Filiale
     * Kunden werden vom Mitarbeiter mit den meisten zu dem mit den wenigsten
     * verschoben, bis die Verteilung ausgeglichen ist
     */
    public void kundenAusgleichen() {
        if (mitarbeiterListe.size() < 2) {
            return; // Ausgleich nur sinnvoll bei mindestens 2 Mitarbeitern
        }

        boolean ausgeglichen = false;

        while (!ausgeglichen) {
            Mitarbeiter mitMeisten = getMitarbeiterMitMeistenKunden();
            Mitarbeiter mitWenigsten = getMitarbeiterMitWenigstenKunden();

            int differenz =
                    mitMeisten.getAnzahlKunden() - mitWenigsten.getAnzahlKunden();

            // Wenn Differenz <= 1, ist es ausgeglichen genug
            if (differenz <= 1) {
                ausgeglichen = true;
            } else {
                // Verschiebe einen Kunden vom Mitarbeiter mit den meisten zum
                // Mitarbeiter mit den wenigsten
                ArrayList<Kunde> kundenVonMeisten = mitMeisten.getKundenListe();

                if (!kundenVonMeisten.isEmpty()) {
                    Kunde zuVerschieben = kundenVonMeisten.get(0);
                    zuVerschieben.changeBetreuer(mitWenigsten);
                } else {
                    ausgeglichen = true; // Sicherheitsabbruch
                }
            }
        }
    }
}