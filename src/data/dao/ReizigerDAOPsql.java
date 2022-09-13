package data.dao;

import model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {

        try {
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());
            statement.executeUpdate();
            if(reiziger.getAdres() != null){
                AdresDAOPsql adao = new AdresDAOPsql(conn);
                adao.save(reiziger.getAdres());
            }

            return true;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger SET reiziger_id = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?," +
                    "geboortedatum = ? WHERE reiziger_id = ? AND voorletters = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());
            statement.setInt(6, reiziger.getId());
            statement.setString(7, reiziger.getVoorletters());
            statement.executeUpdate();
            return true;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Reiziger newReiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"),
                    resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
            return newReiziger;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDate(1, Date.valueOf(datum));
            ResultSet resultSet = statement.executeQuery();
            List<Reiziger> reizigers = new ArrayList<>();

            while(resultSet.next()){
                Reiziger newReiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
                reizigers.add(newReiziger);
            }
            return reizigers;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            String query = "SELECT * FROM reiziger";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Reiziger> reizigers = new ArrayList<>();

            while(resultSet.next()){
                Reiziger newReiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
                reizigers.add(newReiziger);
            }
            return reizigers;
        } catch (SQLException exception){
            System.out.print(exception.getMessage());
            return null;
        }
    }
}
