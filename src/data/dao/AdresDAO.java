package data.dao;

import model.Adres;
import model.Reiziger;

import java.util.List;

interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);

    Adres findByReiziger(Reiziger reiziger);

    List<Adres> findAll();
}
