
package com.mycompany.inventario;

import java.sql.*;

public class Modelo {
    
    private static final String URL = "jdbc:sqlite:/home/eliecer/NetBeansProjects/Sistema-de-Inventario/Inventario/src/main/java/com/mycompany/inventario/inventario.db";
    
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    
    //método para guardar información
    
    public void insertarEquipo(int id, String nombre, String tipo, String marca, String estado) {
        String sql = "INSERT INTO equipos(id, nombre, tipo, marca, estado) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, marca);
            ps.setString(5, estado);
            ps.executeUpdate();

            System.out.println("Datos guardados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }
    
    //método para buscar información
    public void buscar(int id_b) {
        String sql = "SELECT * FROM equipos WHERE id = ?";

        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_b);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Tipo: " + rs.getString("tipo"));
                    System.out.println("Marca: " + rs.getString("marca"));
                    System.out.println("Estado: " + rs.getString("estado"));
                } else {
                    System.out.println("No se encontró un equipo con ese ID.");
                }
            }
        } catch (SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
    //método para eliminar información
    public void eliminar(int id_b) {
        String sql = "DELETE FROM equipos WHERE id = ?";

        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_b);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró un equipo con ese ID.");
            }
        } catch (SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
    //método para ver información
    public void mostrar() {
        String sql = "SELECT * FROM equipos";

        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("nombre") + " | " +
                    rs.getString("tipo") + " | " +
                    rs.getString("marca") + " | " +
                    rs.getString("estado")
                );
            }
        } catch (SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
    
}
