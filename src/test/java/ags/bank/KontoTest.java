package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KontoTest {
    private Konto konto1;
    private String iban1;

    @Before
    public void setUp() {
        iban1 = "DE08700901001234567890";
        konto1 = new Konto(1001, iban1);
    }

    // IBAN und Initialisierung
    @Test
    public void testIBANUebernommen() {
        assertEquals("DE08700901001234567890", konto1.getIBAN());
    }

    @Test
    public void testKontoNummer() {
        assertEquals(1001, konto1.getKontoNummer());
    }

    @Test
    public void testKontostandAmAnfang() {
        assertEquals(0, konto1.getKontostand(), 0.01);
    }

    // Einzahlen - Positive Tests
    @Test
    public void testEinzahlenPositiverBetrag() {
        konto1.einzahlen(200);
        assertEquals(200.00, konto1.getKontostand(), 0.01);
    }

    @Test
    public void testMehrfachEinzahlen() {
        konto1.einzahlen(100);
        konto1.einzahlen(50);
        konto1.einzahlen(25);
        assertEquals(175.00, konto1.getKontostand(), 0.01);
    }

    // Einzahlen - Negative Tests
    @Test(expected = IllegalArgumentException.class)
    public void testEinzahlenNegativerBetrag() {
        konto1.einzahlen(-50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEinzahlenNull() {
        konto1.einzahlen(0);
    }

    // Auszahlen - Positive Tests
    @Test
    public void testAuszahlen() {
        konto1.einzahlen(200);
        assertTrue(konto1.auszahlen(150));
        assertEquals(50.00, konto1.getKontostand(), 0.01);
    }

    @Test
    public void testAuszahlenGesamtbetrag() {
        konto1.einzahlen(200);
        assertTrue(konto1.auszahlen(200));
        assertEquals(0, konto1.getKontostand(), 0.01);
    }

    @Test
    public void testAuszahlenReturnTrue() {
        konto1.einzahlen(200);
        assertTrue(konto1.auszahlen(150));
    }

    // Auszahlen - Negative Tests
    @Test
    public void testAuszahlenReturnFalse() {
        konto1.einzahlen(200);
        assertFalse(konto1.auszahlen(250));
    }

    @Test
    public void testAuszahlenUeberziehen() {
        konto1.einzahlen(200);
        konto1.auszahlen(250);
        assertEquals(200, konto1.getKontostand(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuszahlenNegativerBetrag() {
        konto1.einzahlen(100);
        konto1.auszahlen(-50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuszahlenNull() {
        konto1.einzahlen(100);
        konto1.auszahlen(0);
    }
}