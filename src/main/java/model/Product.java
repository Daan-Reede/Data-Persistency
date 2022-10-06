package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String naam;
    private String beschrijving;
    private double prijs;

    private List<OVChipkaart> ovChipKaarten = new ArrayList<>();

    public Product(int id, String naam, String beschrijving, double prijs){
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setOvChipKaarten(List<OVChipkaart> ovChipKaarten) {
        this.ovChipKaarten = ovChipKaarten;
    }

    public List<OVChipkaart> getOvChipKaarten() {
        return ovChipKaarten;
    }


    @Override
    public String toString() {
        return "product id: " + this.id +" en naam: "+this.getNaam() + " heeft " + ovChipKaarten.size() +
                " gekoppelde ovchipkaarten";
    }
}
