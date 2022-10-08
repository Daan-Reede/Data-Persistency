package data.dao;

import domein.OVChipkaart;
import domein.Product;

import java.util.List;

interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findAll();

    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);

}
