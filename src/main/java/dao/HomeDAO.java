package dao;

import config.ConnectionBBDD;
import model.Coche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeDAO {
    // 1. Listar todos los coches
    public List<Coche> getListaCoches() {
        List<Coche> lista = new ArrayList<>();
        String sql = "SELECT * FROM coches";

        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Coche(
                        rs.getInt("id_coche"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getDouble("precio"), rs.getString("color"), rs.getInt("anio"),
                        rs.getInt("kilometraje"), rs.getString("ciudad"), rs.getString("combustible"),
                        rs.getString("transmision"), rs.getString("img")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    //     2. Insertar un nuevo coche
    public void insertarCoche(Coche coche) {
        String sql = "INSERT INTO coches (marca, modelo, precio, color, anio, kilometraje, ciudad, combustible, transmision, img) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, coche.getMarca());
            stmt.setString(2, coche.getModelo());
            stmt.setDouble(3, coche.getPrecio());
            stmt.setString(4, coche.getColor());
            stmt.setInt(5, coche.getAnio());
            stmt.setInt(6, coche.getKilometraje());
            stmt.setString(7, coche.getCiudad());
            stmt.setString(8, coche.getCombustible());
            stmt.setString(9, coche.getTransmision());
            stmt.setString(10, coche.getImg());

            stmt.executeUpdate();
            System.out.println("Coche insertado con éxito: " + coche.getMarca());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Buscar por marca (Corregido el error de sintaxis SQL)
    public boolean existeMarca(String marcaBuscada) {
        boolean found = false;
        String sql = "SELECT id_coche FROM coches WHERE marca = ?";

        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marcaBuscada);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("La marca " + marcaBuscada + " ya existe en el inventario.");
                found = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }
}