package data.dao;

import model.Adres;
import model.Reiziger;

import java.util.List;

interface AdresDAO {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);

    public Adres findByReiziger(Reiziger reiziger);

    public List<Adres> findAll();
}
