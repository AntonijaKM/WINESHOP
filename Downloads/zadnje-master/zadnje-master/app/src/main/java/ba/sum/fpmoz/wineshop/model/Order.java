package ba.sum.fpmoz.wineshop.model;

public class Order {
    private String ime;
    private String prezime;
    private String adresa;
    private String kontakt;
    private String iznos;

    public Order() {
    }

    public Order(String ime, String prezime, String adresa, String kontakt, String iznos) {
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.kontakt = kontakt;
        this.iznos = iznos;
    }
}
