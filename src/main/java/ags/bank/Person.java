package ags.bank;

public abstract class Person {
    private String vorname;
    private String nachname;
    private String strasse;
    private String hausNummer;
    private String email;
    private Filiale filiale;

    // Constructor
    public Person(String vorname, String nachname, String strasse, String hausNummer, String email, Filiale filiale) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.hausNummer = hausNummer;
        this.email = email;
        this.filiale = filiale;
    }

    // Getters & Setters
    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getNachname() {
        return nachname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public String getStrasse() {
        return strasse;
    }
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }
    public String getHausNummer() {
        return hausNummer;
    }
    public void setHausNummer(String hausNummer) {
        this.hausNummer = hausNummer;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Filiale getFiliale() {
        return filiale;
    }
    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }
}







