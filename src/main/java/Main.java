import dao.AdresDAOHibernate;
import dao.OVChipkaartDAOHibernate;
import dao.ProductDAOHibernate;
import dao.ReizigerDAOHibernate;
import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
import factory.DAOFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        testDaoHibernate();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testDaoHibernate(){
        DAOFactory factory = DAOFactory.getInstance();
        ReizigerDAOHibernate rdao = factory.getReizigerDAO();
//
//
//        // reiziger CRUD \\
        Reiziger reiziger = new Reiziger(10, "DJ", "ff", "Reede", Date.valueOf("2002-03-03"));
        rdao.save(reiziger);

        Reiziger reigerBeforeUpdate = rdao.findById(reiziger);
        System.out.println("Achternaam opgeslagen reiziger voor update: " + reigerBeforeUpdate.getAchternaam());
        reiziger.setAchternaam("Test");
        rdao.update(reiziger);

        Reiziger reizigerAfterUpdate = rdao.findById(reiziger);
        System.out.println("Achternaam opgeslagen reiziger na update: " + reizigerAfterUpdate.getAchternaam());

        // adres CRUD \\
        AdresDAOHibernate adao = factory.getAdresDAO();
        Adres adres = new Adres(10, "3030XL", "5", "Berglaan", "Ede", reiziger);
        adao.save(adres);

        Adres adresBeforeUpdate = adao.findById(adres);
        System.out.println("Postcode voor update: " + adresBeforeUpdate.getPostcode());
        adres.setPostcode("9999XL");
        adao.update(adres);

        Adres AdresAfterUpdate = adao.findById(adres);
        System.out.println("Postcode na update: " + AdresAfterUpdate.getPostcode());

        // OVchipkaart CRUD \\
        OVChipkaartDAOHibernate ovdao = factory.getOvDAO();
        OVChipkaart ovchip = new OVChipkaart(10, Date.valueOf("2022-10-10"), 2, 20.0, reiziger);
        ovdao.save(ovchip);

        OVChipkaart ovBeforeUpdate = ovdao.findByKaartNummer(ovchip.getKaart_nummer());
        System.out.println("Saldo voor update: " + ovBeforeUpdate.getSaldo());
        ovchip.setSaldo(99.0);
        ovdao.update(ovchip);

        OVChipkaart ovAfterUpdate = ovdao.findByKaartNummer(ovchip.getKaart_nummer());
        System.out.println("Saldo na update: " + ovAfterUpdate.getSaldo());

        // Product CRUD \\
        ProductDAOHibernate pdao = factory.getProductDAO();
        Product product = new Product(50, "test product", "test", 20.0);
        product.addOv(ovchip);
        pdao.save(product);

        Product productBeforeUpdate = pdao.findById(product);
        System.out.println("Beschrijving voor update: " + productBeforeUpdate.getBeschrijving());
        product.setBeschrijving("nieuwe beschrijving");

        pdao.update(product);

        Product productAfterUpdate = pdao.findById(product);
        System.out.println("Beschrijving na update: " + productAfterUpdate.getBeschrijving());


        rdao.delete(reiziger);
    }
}