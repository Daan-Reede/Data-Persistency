package data.dao;

import domein.Adres;
import domein.OVChipkaart;
import domein.Product;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;

    private OVChipkaartDAO ovdao;


    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        try {
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());
            statement.executeUpdate();
            System.out.println(product.getOvChipKaarten().size());

            if(product.getOvChipKaarten().size() != 0){
                for (OVChipkaart ovChipkaart : product.getOvChipKaarten()){
                    ovdao.save(ovChipkaart);

                    // daarna koppelen
                    String secondQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) " +
                            "VALUES (?,?,?,?)";
                    PreparedStatement pstatement = conn.prepareStatement(secondQuery);
                    pstatement.setInt(1, ovChipkaart.getKaart_nummer());
                    pstatement.setInt(2, product.getId());
                    pstatement.setString(3, "actief");
                    pstatement.setDate(4, new Date(System.currentTimeMillis()));
                    pstatement.executeUpdate();
                }
            }


            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            String query = "UPDATE product SET product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());
            statement.setDouble(5, product.getId());
            statement.executeUpdate();

                    String secondQuery = "UPDATE ov_chipkaart_product SET status = ?, last_update = ? " +
                            "WHERE product_nummer = ?";
                    PreparedStatement pstatement = conn.prepareStatement(secondQuery);
                    pstatement.setString(1, "actief");
                    pstatement.setDate(2, new Date(System.currentTimeMillis()));
                    pstatement.setInt(3, product.getId());
                    pstatement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            String secondQuery = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement pstatement = conn.prepareStatement(secondQuery);
            pstatement.setInt(1, product.getId());
            pstatement.executeUpdate();

            String query = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            String query = "SELECT * FROM product";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Product> producten = new ArrayList<>();

            while(resultSet.next()){
                Product product = new Product(resultSet.getInt("product_nummer"), resultSet.getString("naam"),
                        resultSet.getString("beschrijving"), resultSet.getDouble("prijs"));
                product.setOvChipKaarten(ovdao.findByProduct(product));
                producten.add(product);
            }
            return producten;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            String query = "SELECT * FROM product p " +
                    "INNER JOIN ov_chipkaart_product ovcp ON ovcp.product_nummer = p.product_nummer" +
                    " INNER JOIN ov_chipkaart ovc ON ovc.kaart_nummer = ovcp.kaart_nummer" +
                    " WHERE ovc.kaart_nummer = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet resultSet = statement.executeQuery();
            List<Product> producten = new ArrayList<>();

            while(resultSet.next()){
                Product product = new Product(resultSet.getInt("product_nummer"), resultSet.getString("naam"),
                        resultSet.getString("beschrijving"), resultSet.getDouble("prijs"));
                producten.add(product);
            }
            return producten;
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
            return null;
        }
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }
}
