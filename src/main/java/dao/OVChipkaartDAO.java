package dao;

import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.util.List;

interface OVChipkaartDAO {
//    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);

    OVChipkaart findByKaartNummer(int kaartnummer);
//
//    List<OVChipkaart> findByProduct(Product product);
//    List<OVChipkaart> findAll();
}
