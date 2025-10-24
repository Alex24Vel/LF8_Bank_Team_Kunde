import ags.bank.Konto;
import org.junit.Test;

import static org.junit.Assert.*;

public class KontoTest {
    String iban1 = "DE08700901001234567890"; //korrekte IBAN
    Konto konto1 = new Konto(1, iban1);

    // IBAN richtig übernommen
    @Test
    public void testIBANUebernommen() {
        assertEquals("DE08700901001234567890", konto1.getIBAN());
    }

    // Kontostand am Anfang 0
    @Test
    public void testKontostandAmAnfang() {
        assertEquals(0, konto1.getKontostand(), 0.001);
    }

    // einzahlen
    @Test
    public void testEinzahlen() {
        konto1.einzahlen(200);
        assertEquals(200.00, konto1.getKontostand(), 0.001);
    }

    // auszahlen
    @Test
    public void testAuszahlen() {
        konto1.einzahlen(200);
        konto1.auszahlen(150);
        assertEquals(50.00, konto1.getKontostand(), 0.001);
    }

    // auszahlen Rückgabe true
    @Test
    public void testAuszahlenTrue() {
        konto1.einzahlen(200);
        assertTrue(konto1.auszahlen(150));
    }

    // auszahlen Rückgabe false
    @Test
    public void testAuszahlenFalse() {
        konto1.einzahlen(200);
        assertFalse(konto1.auszahlen(250));
    }

    // auszahlen, wenn Betrag auf Konto zu gering
    @Test
    public void testUeberziehe() {
        konto1.einzahlen(200);
        konto1.auszahlen(250);
        assertEquals(200, konto1.getKontostand(), 0.001);
    }
} 