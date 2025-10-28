package ags.bank;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KontoTest {
    private Konto konto1;
    private Konto konto2;
    private String iban1;
    private String iban2;

    @Before
    public void setUp() {
        iban1 = "DE08700901001234567890";
        iban2 = "DE08700901001234567891";
        konto1 = new Konto(1001, iban1);
        konto2 = new Konto(1002, iban2);
    }

    // ========== Money Transfer Tests ==========
    @Test
    public void testUeberweisenErfolgreich() {
        konto1.einzahlen(1000);
        assertTrue(konto1.ueberweisen(konto2, 300));
        assertEquals(700, konto1.getKontostand(), 0.01);
        assertEquals(300, konto2.getKontostand(), 0.01);
    }

    @Test
    public void testUeberweisenGesamtBetrag() {
        konto1.einzahlen(500);
        assertTrue(konto1.ueberweisen(konto2, 500));
        assertEquals(0, konto1.getKontostand(), 0.01);
        assertEquals(500, konto2.getKontostand(), 0.01);
    }

    @Test
    public void testUeberweisenUnzureichend() {
        konto1.einzahlen(100);
        assertFalse(konto1.ueberweisen(konto2, 200));
        assertEquals(100, konto1.getKontostand(), 0.01);
        assertEquals(0, konto2.getKontostand(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUeberweisenNegativerBetrag() {
        konto1.einzahlen(100);
        konto1.ueberweisen(konto2, -50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUeberweisenZielKontoNull() {
        konto1.einzahlen(100);
        konto1.ueberweisen(null, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUeberweisenAnSichSelbst() {
        konto1.einzahlen(100);
        konto1.ueberweisen(konto1, 50);
    }

    @Test
    public void testMehrfachUeberweisen() {
        konto1.einzahlen(1000);
        assertTrue(konto1.ueberweisen(konto2, 200));
        assertTrue(konto1.ueberweisen(konto2, 300));
        assertEquals(500, konto1.getKontostand(), 0.01);
        assertEquals(500, konto2.getKontostand(), 0.01);
    }

    @Test
    public void testUeberweisenKette() {
        Konto konto3 = new Konto(1003, "DE08700901001234567892");

        konto1.einzahlen(1000);
        konto1.ueberweisen(konto2, 400);
        konto2.ueberweisen(konto3, 150);

        assertEquals(600, konto1.getKontostand(), 0.01);
        assertEquals(250, konto2.getKontostand(), 0.01);
        assertEquals(150, konto3.getKontostand(), 0.01);
    }

    // ========== Account Info Tests ==========
    @Test
    public void testGetKontoInfo() {
        String info = konto1.getKontoInfo();
        assertTrue(info.contains("1001"));
        assertTrue(info.contains(iban1));
        assertTrue(info.contains("0"));
    }

    @Test
    public void testGetKontoInfoMitGuthaben() {
        konto1.einzahlen(1234.56);
        String info = konto1.getKontoInfo();
        assertTrue(info.contains("1234,56") || info.contains("1234.56"));
    }

    // ========== Complex Scenarios ==========
    @Test
    public void testMultipleOperationen() {
        konto1.einzahlen(1000);
        konto1.auszahlen(200);
        konto1.einzahlen(500);
        konto1.ueberweisen(konto2, 300);
        konto2.einzahlen(100);

        assertEquals(1000, konto1.getKontostand(), 0.01);
        assertEquals(400, konto2.getKontostand(), 0.01);
    }

    @Test
    public void testRundungsFehler() {
        konto1.einzahlen(100.10);
        konto1.einzahlen(200.20);
        konto1.auszahlen(50.15);
        assertEquals(250.15, konto1.getKontostand(), 0.01);
    }

    @Test
    public void testGrosseBetraege() {
        konto1.einzahlen(1000000);
        konto1.ueberweisen(konto2, 999999.99);
        assertEquals(0.01, konto1.getKontostand(), 0.01);
        assertEquals(999999.99, konto2.getKontostand(), 0.01);
    }

    @Test
    public void testKleineBetraege() {
        konto1.einzahlen(0.01);
        assertTrue(konto1.ueberweisen(konto2, 0.01));
        assertEquals(0, konto1.getKontostand(), 0.01);
        assertEquals(0.01, konto2.getKontostand(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUeberweisenNullBetrag() {
        konto1.einzahlen(100);
        konto1.ueberweisen(konto2, 0);
    }
}