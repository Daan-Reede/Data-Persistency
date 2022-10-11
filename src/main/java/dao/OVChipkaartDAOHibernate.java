package dao;

import domein.Adres;
import domein.OVChipkaart;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private SessionFactory factory;

    public OVChipkaartDAOHibernate(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public Session getSession() {
        return this.factory.openSession();
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.save(ovChipkaart);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }

        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.update(ovChipkaart);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }

        return true;

    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Reiziger ovChipKaartToRemove = sess.find(Reiziger.class, ovChipkaart.getKaart_nummer());
            sess.remove(ovChipKaartToRemove);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }

        return true;

    }

    @Override
    public OVChipkaart findByKaartNummer(int kaartnummer) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            OVChipkaart foundOV = sess.get(OVChipkaart.class, kaartnummer);
            tx.commit();
            return foundOV;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }
    }
}
