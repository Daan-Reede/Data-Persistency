package domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Reiziger {

    @Id
    @Column(name = "reiziger_id")
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    @OneToOne(mappedBy = "reiziger")
    private Adres adres;

    @OneToMany(mappedBy = "reiziger")
    private List<OVChipkaart> OVChipkaarten = new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {

    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

//    public void setAdres(Adres adres) {
//        this.adres = adres;
//    }
//
//    public Adres getAdres() {
//        return adres;
//    }

    public List<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(List<OVChipkaart> OVChipkaarten) {
        this.OVChipkaarten = OVChipkaarten;
    }

    public void addOVChipkaart(OVChipkaart ov){
        this.OVChipkaarten.add(ov);
    }

    @Override
    public String toString() {
        return "Reiziger " + this.getVoorletters() + "." + (this.getTussenvoegsel() != null ? this.getTussenvoegsel() : "") + " " + this.getAchternaam()
                + " straat: " + adres.getStraat();
    }
}
