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
}