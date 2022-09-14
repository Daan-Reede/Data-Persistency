package data.dao;

import model.Adres;
import model.OVChipkaart;
import model.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;

    private ReizigerDAOPsql rdao;

    public OVChipkaartDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            ResultSet resultSet = statement.executeQuery();

            List<OVChipkaart> OVChipkaarten = new ArrayList<>();
            while(resultSet.next()){
                OVChipkaart ovChipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"),
                        resultSet.getInt("klasse"), resultSet.getDouble("saldo"), rdao.findById(resultSet.getInt("reiziger_id")));
                OVChipkaarten.add(ovChipkaart);
            }
            return OVChipkaarten;

        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, ovChipkaart.getGeldig_tot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger().getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.setDate(2, ovChipkaart.getGeldig_tot());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReiziger().getId());
            statement.setInt(6, ovChipkaart.getKaart_nummer());
            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<OVChipkaart> OVChipkaarten = new ArrayList<>();

            while(resultSet.next()){
                OVChipkaart ovChipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"),
                        resultSet.getInt("klasse"), resultSet.getDouble("saldo"), rdao.findById(resultSet.getInt("reiziger_id")));
                OVChipkaarten.add(ovChipkaart);
            }
            return OVChipkaarten;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    @Override
    public OVChipkaart findByKaartNummer(int kaartnummer) {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, kaartnummer);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            OVChipkaart ovChipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"),
                    resultSet.getInt("klasse"), resultSet.getDouble("saldo"), rdao.findById(resultSet.getInt("reiziger_id")));
            return ovChipkaart;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }
}
