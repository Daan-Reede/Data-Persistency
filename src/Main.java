import data.dao.*;
import model.Adres;
import model.OVChipkaart;
import model.Product;
import model.Reiziger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Connection conn;
    public static void main(String[] args) throws SQLException {
        getConnection();
        AdresDAOPsql adao = new AdresDAOPsql(conn);
        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(conn);
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
        ProductDAOPsql pdao = new ProductDAOPsql(conn);

        rdao.setOvdao(ovdao);
        rdao.setAdao(adao);
        ovdao.setRdao(rdao);
        pdao.setOvdao(ovdao);

//        testReizigerDAO(rdao);
//        testAdresDAO(adao, rdao);
//        testOvChipDAO(ovdao, rdao);
        testProductDAO(pdao, rdao, ovdao);
        conn.close();
    }

    private static void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        String usern = "postgres";
        String passw = "123";
        conn = DriverManager.getConnection(url, usern, passw);
    }

    private static void testProductDAO(ProductDAOPsql pdao, ReizigerDAOPsql rdao, OVChipkaartDAOPsql ovdao) {

        /**
         * P2. Product DAO: persistentie van een klasse
         *
         * Deze methode test de CRUD-functionaliteit van de Product DAO
         *
         * @throws SQLException
         */
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle reizigers op uit de database
        List<Product> products = pdao.findAll();
        System.out.println("[Test] producten aantal: "+ products.size() + " voor ProductDAO.save()\n");

        Product testproduct = new Product(10, "test", "test", 10.2);
        OVChipkaart testOV = new OVChipkaart(99999, Date.valueOf("2022-10-26"), 2, 20.3, rdao.findById(1));
        List<OVChipkaart> nieuwOVKaarten = new ArrayList<>();
        nieuwOVKaarten.add(testOV);

        testproduct.setOvChipKaarten(nieuwOVKaarten);
        pdao.save(testproduct);

        System.out.println("[Test] producten aantal: "+ pdao.findAll().size() + " na ProductDAO.save()");

        List<Product> oldProducts = pdao.findAll();
        System.out.println("[Test] voor update: ");
        for (Product r : oldProducts) {
            System.out.println(r.getNaam());
        }

        testproduct.setNaam("test2");
        pdao.update(testproduct);

        System.out.println("\n[Test] na update:");
        List<Product> newProducts = pdao.findAll();
        for (Product r : newProducts) {
            System.out.println(r.getNaam());
        }

        pdao.delete(testproduct);
        ovdao.delete(testOV);
        System.out.println("\n[Test] producten aantal: "+ newProducts.size() + " voor ProductDAO.delete():");
        System.out.println("[Test] producten aantal: "+ pdao.findAll().size() + " na ProductDAO.delete():");


        for (Product product : pdao.findAll()) {
            System.out.println(product);
        }
    }

    private static void testAdresDAO(AdresDAOPsql adao, ReizigerDAOPsql rdao) {
        /**
         * P2. Adres DAO: persistentie van een klasse
         *
         * Deze methode test de CRUD-functionaliteit van de Adres DAO
         *
         * @throws SQLException
         */
        System.out.println("\n---------- Test testAdresDAOPsql -------------");

        List<Adres> addresses = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() bevat: " + addresses.size() + " addressen");
        Reiziger testReiziger = new Reiziger(6, "Test", "den", "testen", Date.valueOf("2022-11-11"));
        rdao.save(testReiziger);
        Adres testadres = new Adres(70, "3333XL", "5", "Achterberg", "Ede", testReiziger);
        adao.save(testadres);


        System.out.print("[Test] Adres tabel heeft nu een nieuw adres met id: " + testadres.getId() + "\n");
        List<Adres> addedAddresses = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() bevat: " + addedAddresses.size() + " addressen na AdresDAO.save()\n");


        System.out.println("[Test] Er is een nieuwe reiziger gemaakt met id: " +testReiziger.getId()+ " en gekoppeld aan het bovenstaande adres");

        Adres foundAdres = adao.findByReiziger(testReiziger);
        System.out.print("[Test] Reizger met id: " + testReiziger.getId() + " heeft als adres_id(AdresDAO.findByReiziger): " + foundAdres.getId() + "\n\n");

        System.out.println("[Test] Postcode voor AdresDAO.update(): " + testadres.getPostcode());
        testadres.setPostcode("4444LS");
        adao.update(testadres);

        Adres adresForPostcodeTest = adao.findByReiziger(testReiziger);
        System.out.println("[Test] Postcode na AdresDAO.update(): " + adresForPostcodeTest.getPostcode() + "\n");

        System.out.print("[Test] Grote van alle addressen voor AdresDAO.delete(): " + adao.findAll().size() + "\n");
        adao.delete(testadres);
        System.out.print("[Test] Grote van alle addressen na AdresDAO.delete(): " + adao.findAll().size() + "\n\n");

        System.out.print("[Test] Adres.findAll(): \n");
        for(Adres adres : adao.findAll()){
            System.out.print(adres + "\n");
        }

        // remove test user
        rdao.delete(testReiziger);
    }

    private static void testReizigerDAO(ReizigerDAOPsql rdao) {

        /**
         * P2. Reiziger DAO: persistentie van een klasse
         *
         * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
         *
         * @throws SQLException
         */
        System.out.println("\n---------- Test ReizigerDAO -------------");


        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r.getAchternaam());
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietskeOud = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        Adres testadres = new Adres(80, "2222XL", "5", "Achterberg", "Ede", sietskeOud);
        sietskeOud.setAdres(testadres);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietskeOud);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // findById & update
        System.out.print("[Test] Achternaam voor update: " + sietskeOud.getAchternaam());
        sietskeOud.setAchternaam("Koers");
        rdao.update(sietskeOud);
        Reiziger sietskeNieuw = rdao.findById(77);
        System.out.print("\n na ReizigerDAO.update() & Reiziger.findById: " + sietskeNieuw.getAchternaam() + "\n\n");

        //findByGbdatum
        System.out.print("[Test] Sietske's achternaam gevonden op basis van geboortedatum: ");
        List<Reiziger> reizigerList = rdao.findByGbdatum(gbdatum);

        for (Reiziger reiziger : reizigerList){
            System.out.print(reiziger.getAchternaam());
        }

        // delete
        System.out.print("\n[Test] Alle reizigers voor een delete query:\n");
        List<Reiziger> reizigersOud = rdao.findAll();
        for (Reiziger r : reizigersOud) {
            System.out.println(r.getAchternaam());
        }
        rdao.delete(sietskeNieuw);

        System.out.print("[Test] Alle reizigers na een delete query:\n");
        List<Reiziger> reizigersNieuw = rdao.findAll();
        for (Reiziger r : reizigersNieuw) {
            System.out.println(r.getAchternaam());
        }
        System.out.print("\n");
    }


    private static void testOvChipDAO(OVChipkaartDAOPsql OVdao, ReizigerDAOPsql rdao) {
        /**
         * P2. OVChipkaart DAO: persistentie van een klasse
         *
         * Deze methode test de CRUD-functionaliteit van de OVChipkaart DAO
         *
         * @throws SQLException
         */
        System.out.println("\n---------- Test OVChipkaartDAOPsql -------------");

        //test OV
        OVChipkaart testOV = new OVChipkaart(43243, Date.valueOf("2022-02-03"), 2, 30.34, rdao.findById(5));
        List<OVChipkaart> ovChipkaartenOud = OVdao.findAll();
        OVdao.save(testOV);
        List<OVChipkaart> ovChipkaartenNieuw = OVdao.findAll();

        // OVChipkaart.save()
        System.out.print("[Test] ov_chipkaart tabel voor OVChipkaart.save(): " + ovChipkaartenOud.size() + "\n\n");
        System.out.print("[Test] Nieuw ovChipkaart aangemaakt met kaartnummer: " + testOV.getKaart_nummer() + " en saldo: " + testOV.getSaldo() + "\n\n");
        System.out.print("[Test] ov_chipkaart tabel na OVChipkaart.save(): " + ovChipkaartenNieuw.size() + "\n\n");

        //OVChipkaart.update() && OVdao.findByKaartNummer
        System.out.println("[Test] Saldo voor OVChipkaart.update(): " + testOV.getSaldo());
        testOV.setSaldo(90.55);
        OVdao.update(testOV);
        OVChipkaart foundOV = OVdao.findByKaartNummer(testOV.getKaart_nummer());
        System.out.println("[Test] Saldo na OVChipkaart.update(): " + foundOV.getSaldo() + "\n");

        //OVChipkaart.findAll() && OVdoa.delete()
        List<OVChipkaart> OovChipKaartenBeforeDelete = OVdao.findAll();
        System.out.print("[Test] Alle ovchip kaarten voor OVChip.delete(): \n");
        for (OVChipkaart kaart : OovChipKaartenBeforeDelete) {
            System.out.println("kaart_nummer: " + kaart.getKaart_nummer());
        }
        System.out.print("\n");

        OVdao.delete(testOV);
        List<OVChipkaart> OovChipKaartenAfterDelete = OVdao.findAll();
        System.out.print("[Test] Alle ovchip kaarten na OVChip.delete(): \n");
        for (OVChipkaart kaart : OovChipKaartenAfterDelete) {
            System.out.println("kaart_nummer: " + kaart.getKaart_nummer());
        }

        System.out.print("\n[Test] Koppeling OVChipkaartDAOPsql en reizigerDAOPsql(): \n");
        for (OVChipkaart kaart : OovChipKaartenAfterDelete) {
            System.out.println(kaart.getReiziger().getVoorletters() + ". " + kaart.getReiziger().getAchternaam()+
                    "heeft een ov chipkaart met kaartnummer: " + kaart.getKaart_nummer());
        }
    }
}
