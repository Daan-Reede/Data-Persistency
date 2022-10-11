package factory;

import dao.AdresDAOHibernate;
import dao.OVChipkaartDAOHibernate;
import dao.ProductDAOHibernate;
import dao.ReizigerDAOHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAOFactory {
    private static SessionFactory sessionFactory;
    private static DAOFactory daoFactory;

    public static DAOFactory getInstance(){
        if(DAOFactory.sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        if(daoFactory == null) {
            daoFactory = new DAOFactory();
        }

        return daoFactory;
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public ReizigerDAOHibernate getReizigerDAO() {
        return new ReizigerDAOHibernate(sessionFactory);
    }

    public AdresDAOHibernate getAdresDAO() {
        return new AdresDAOHibernate(sessionFactory);
    }

    public ProductDAOHibernate getProductDAO() {
        return new ProductDAOHibernate(sessionFactory);
    }

    public OVChipkaartDAOHibernate getOvDAO() {
        return new OVChipkaartDAOHibernate(sessionFactory);
    }
}
