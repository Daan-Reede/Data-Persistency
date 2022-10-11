package dao;

import domein.Reiziger;

import java.util.List;

interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);


    Reiziger findById(Reiziger reiziger);
//    public Reiziger findById(int id);
//    public List<Reiziger> findByGbdatum(String datum);
//    public List<Reiziger> findAll();
}
