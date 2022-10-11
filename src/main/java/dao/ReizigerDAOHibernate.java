package dao;

import  org.hibernate.Session;
import domein.Reiziger;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ReizigerDAOHibernate implements ReizigerDAO {


    private SessionFactory factory;

    public ReizigerDAOHibernate(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public Session getSession() {
        return this.factory.openSession();
    }

    @Override
    public boolean save(Reiziger reiziger) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.save(reiziger);
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
    public boolean update(Reiziger reiziger) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.update(reiziger);
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
    public boolean delete(Reiziger reiziger) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();
        try {
            Reiziger reizigerToRemove = sess.find(Reiziger.class, reiziger.getId());
            sess.remove(reizigerToRemove);
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
    public Reiziger findById(Reiziger reiziger) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Reiziger foundReiziger = sess.get(Reiziger.class, reiziger.getId());
            tx.commit();
            return foundReiziger;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }
    }
}
