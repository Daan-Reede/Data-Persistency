package data.dao;

import model.Adres;
import model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    private ReizigerDAOPsql rdao;

    public AdresDAOPsql(Connection conn){
        this.conn = conn;
        this.rdao = new ReizigerDAOPsql(conn);
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET adres_id = ?, postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());
            statement.setInt(7, adres.getId());

            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.isBeforeFirst()) {
                resultSet.next();
                Adres foundAdres = new Adres(resultSet.getInt("adres_id"), resultSet.getString("postcode"),
                        resultSet.getString("huisnummer"), resultSet.getString("straat"), resultSet.getString("woonplaats"), reiziger);
                return foundAdres;
            }
            return null;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            String query = "SELECT * FROM adres";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Adres> addresses = new ArrayList<>();

            while(resultSet.next()){
                Adres newAdres = new Adres(resultSet.getInt("adres_id"), resultSet.getString("postcode"),
                        resultSet.getString("huisnummer"), resultSet.getString("straat"), resultSet.getString("woonplaats"), rdao.findById(resultSet.getInt("reiziger_id")));
                addresses.add(newAdres);
            }
            return addresses;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }
}
