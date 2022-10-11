package dao;

import domein.Adres;
import domein.Reiziger;

import java.util.List;

interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);

    Adres findById(Adres adres);
//
//    Adres findByReiziger(Reiziger reiziger);
//
//    List<Adres> findAll();
}
