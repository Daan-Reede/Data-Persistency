package data.dao;

import model.OVChipkaart;
import model.Product;
import model.Reiziger;

import java.util.List;

interface OVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    OVChipkaart findByKaartNummer(int kaartnummer);

    List<OVChipkaart> findByProduct(Product product);
    List<OVChipkaart> findAll();
}
