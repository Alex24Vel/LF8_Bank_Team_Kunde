package ags.bank;

import java.util.Random;

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

    public void setKontoNummer(int kontoNummer) {
        this.kontoNummer = kontoNummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void einzahlen(double betrag) {
        kontostand += betrag;
    }

    public boolean auszahlen(double betrag) {
        if (betrag <= kontostand) {
            kontostand -= betrag;
            return true;
        } else {
            return false;
        }
    }
}