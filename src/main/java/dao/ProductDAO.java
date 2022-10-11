package dao;

import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.util.List;

interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);


    Product findById(Product product);
//    List<Product> findAll();
//
//    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);

}
