package domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @Column(name = "product_nummer")
    private int id;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = { @JoinColumn(name = "product_nummer") },
            inverseJoinColumns = { @JoinColumn(name = "kaart_nummer ") }
    )
    private List<OVChipkaart> ov_chipkaart = new ArrayList<>();

    public Product(int id, String naam, String beschrijving, double prijs){
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

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

    public void setOv_chipkaart(List<OVChipkaart> ovChipKaarten) {
        this.ov_chipkaart = ovChipKaarten;
    }

    public void addOv(OVChipkaart ovChipkaart) {
        this.ov_chipkaart.add(ovChipkaart);
    }

    public List<OVChipkaart> getOv_chipkaart() {
        return ov_chipkaart;
    }


    @Override
    public String toString() {
        return "product id: " + this.id + " en naam: " + this.getNaam() + " heeft " +
                " gekoppelde ovchipkaarten";
    }

}
