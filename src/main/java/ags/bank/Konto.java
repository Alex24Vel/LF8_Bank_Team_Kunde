package ags.bank;

public class Konto {
    private int kontoNummer;
    private double kontostand;
    private final String IBAN;

    public Konto(int kontoNummer, String IBAN) {
        this.kontoNummer = kontoNummer;
        this.IBAN = IBAN;
        kontostand = 0;
    }

    public int getKontoNummer() {
        return kontoNummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void einzahlen(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein");
        }
        kontostand += betrag;
    }

    public boolean auszahlen(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein");
        }
        if (betrag <= kontostand) {
            kontostand -= betrag;
            return true;
        }
        return false;
    }

    // ========== PHASE 2 METHODS ==========

    /**
     * Überweist Geld von diesem Konto auf ein Zielkonto
     *
     * @param zielKonto Das Zielkonto
     * @param betrag Der zu überweisende Betrag
     * @return true wenn erfolgreich, false wenn unzureichende Deckung
     * @throws IllegalArgumentException wenn zielKonto null, betrag <= 0, oder
     *     Überweisung an sich selbst
     */
    public boolean ueberweisen(Konto zielKonto, double betrag) {
        if (zielKonto == null) {
            throw new IllegalArgumentException("Zielkonto darf nicht null sein");
        }
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein");
        }
        if (zielKonto == this) {
            throw new IllegalArgumentException(
                    "Überweisung an sich selbst nicht möglich"
            );
        }

        // Prüfen ob genug Geld vorhanden
        if (this.auszahlen(betrag)) {
            zielKonto.einzahlen(betrag);
            return true;
        }

        return false;
    }

    /**
     * Gibt formatierte Kontoinformationen zurück
     *
     * @return String mit Kontonummer, IBAN und aktuellem Kontostand
     */
    public String getKontoInfo() {
        return String.format(
                "Konto %d | IBAN: %s | Kontostand: %.2f EUR",
                kontoNummer,
                IBAN,
                kontostand
        );
    }
}