import data.dao.AdresDAOPsql;
import data.dao.ReizigerDAOPsql;
import model.Adres;
import model.Reiziger;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        String usern = "postgres";
        String passw = "123";

        Connection conn = DriverManager.getConnection(url, usern, passw);
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
        AdresDAOPsql adao = new AdresDAOPsql(conn);
        Reiziger testreiziger = new Reiziger(6, "Test", "den", "testenffff", Date.valueOf("2022-11-11"));

        testReizigerDAO(rdao);
        testAdresDAO(adao, rdao);
        rdao.delete(testreiziger);

        conn.close();
    }

    private static void testAdresDAO(AdresDAOPsql adao, ReizigerDAOPsql rdao) {
        List<Adres> addresses = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() bevat: " + addresses.size() + " addressen");
        Reiziger testReiziger = new Reiziger(6, "Test", "den", "testen", Date.valueOf("2022-11-11"));
        Adres testadres = new Adres(70, "3333XL", "5", "Achterberg", "Ede", testReiziger);
        testReiziger.setAdres(testadres);

        rdao.save(testReiziger);
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
}
