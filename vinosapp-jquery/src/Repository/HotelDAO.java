/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Repository.ConnectionHelper;
import Domain.Hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author alu2015059
 */
public class HotelDAO {

    public List<Hotel> findAll() {
        List<Hotel> hotel_list = new ArrayList<>();
        Connection conection = null;
        String query = "SELECT * FROM hotel ORDER BY hotel_name";
        try {
            conection = ConnectionHelper.getConnection();
            Statement statement = conection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                hotel_list.add(ProcessRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
        return hotel_list;
    }

    public List<Hotel> findByName(String hotel_name) {
        List<Hotel> hotel_list = new ArrayList<>();
        Connection conection = null;
        String query = "SELECT * FROM hotel "
                + "WHERE UPPER(hotel_name) LIKE ? "
                + "ORDER BY hotel_name";
        try {
            conection = ConnectionHelper.getConnection();
            PreparedStatement ps = conection.prepareStatement(query);
            ps.setString(1, "%" + hotel_name.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hotel_list.add(ProcessRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
        return hotel_list;
    }

    public Hotel findById(int id) {
        String query = "SELECT * FROM hotel WHERE id = ?";
        Hotel hotel = null;
        Connection conection = null;
        try {
            conection = ConnectionHelper.getConnection();
            PreparedStatement ps = conection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hotel = ProcessRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
        return hotel;
    }

    public Hotel save(Hotel hotel) {
        return hotel.getId() > 0 ? Update(hotel) : Create(hotel);
    }

    public Hotel Create(Hotel hotel) {
        Connection conection = null;
        PreparedStatement ps = null;
        final String query = "INSERT INTO hotel"
                + "(hotel_name, stars, city, description, price) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            conection = ConnectionHelper.getConnection();
            ps = conection.prepareStatement(query, new String[]{"ID"});
            ps.setString(1, hotel.getHotel_name());
            ps.setInt(2, hotel.getStars());
            ps.setString(3, hotel.getCity());
            ps.setString(4, hotel.getDescription());
            ps.setDouble(5, hotel.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Actualizar el id del objeto que se devuelve. Esto es importante 
            // ya que este valor debe ser devuelto al cliente.
            int id = rs.getInt(1);
            hotel.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
        return hotel;
    }

    private Hotel ProcessRow(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        
        hotel.setId(rs.getInt("id"));
        hotel.setHotel_name(rs.getString("hotel_name"));
        hotel.setStars(rs.getInt("stars"));
        hotel.setCity(rs.getString("city"));
        hotel.setDescription(rs.getString("description"));
        hotel.setPrice(rs.getDouble("price"));

        return hotel;
    }

    private Hotel Update(Hotel hotel) {
        Connection conection = null;
        try {
            conection = ConnectionHelper.getConnection();
            final String query = "UPDATE hotel SET "
                    + "hotel_name=?, stars=?, city=?, description=?, price=? "
                    + "WHERE id=?";
            PreparedStatement ps = conection.prepareStatement(query);
            ps.setString(1, hotel.getHotel_name());
            ps.setInt(2, hotel.getStars());
            ps.setString(3, hotel.getCity());
            ps.setString(4, hotel.getDescription());
            ps.setDouble(5, hotel.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
        return hotel;
    }

    public boolean remove(int id) {
        Connection conection = null;
        try {
            conection = ConnectionHelper.getConnection();
            PreparedStatement ps
                    = conection.prepareStatement("DELETE FROM hotel WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(conection);
        }
    }
}
