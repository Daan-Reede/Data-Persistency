package dao;

import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ProductDAOHibernate implements ProductDAO {

    private SessionFactory factory;

    public ProductDAOHibernate(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public Session getSession() {
        return this.factory.openSession();
    }

    @Override
    public boolean save(Product product) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.save(product);
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
    public boolean update(Product product) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();
        try {
            sess.update(product);
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
    public boolean delete(Product product) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Reiziger productToRemove = sess.find(Reiziger.class, product.getId());
            sess.remove(productToRemove);
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
    public Product findById(Product product) {
        Session sess = this.getSession();
        Transaction tx = sess.beginTransaction();

        try {
            Product foundProduct = sess.get(Product.class, product.getId());
            tx.commit();
            return foundProduct;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            sess.close();
        }
    }
}
