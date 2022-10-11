package dao;

import domein.Adres;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AdresDAOHibernate implements AdresDAO {


    private SessionFactory factory;

    public AdresDAOHibernate(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public Session getSession() {
        return this.factory.openSession();
    }

    @Override
    public boolean save(Adres adres) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.save(adres);
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
    public boolean update(Adres adres) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.update(adres);
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
    public boolean delete(Adres adres) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Reiziger adresToRemove = sess.find(Reiziger.class, adres.getId());
            sess.remove(adresToRemove);
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
    public Adres findById(Adres adres) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Adres foundAdres = sess.get(Adres.class, adres.getId());
            tx.commit();
            return foundAdres;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }
    }
}
