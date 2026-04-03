/*
Se adaptaron los metodos para que retornaran los valores a la interfaz y no usara prints. 
atte. Eliecer
*/


package com.mycompany.inventario;

import java.sql.*;

public class Modelo {
    
    private static final String URL = "jdbc:sqlite:/home/eliecer/NetBeansProjects/Sistema-de-Inventario/Inventario/src/main/java/com/mycompany/inventario/inventario.db";
    
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    
    //método para guardar información
    public String insertarEquipo(int id, String nombre, String tipo, String marca, String estado) {
        String sql = "INSERT INTO equipos(id, nombre, tipo, marca, estado) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, marca);
            ps.setString(5, estado);
            ps.executeUpdate();

            return "Datos guardados correctamente.";
        } catch (SQLException e) {
            return "Error al insertar: " + e.getMessage();
        }
        
    }
    
    //método para buscar información
    public String buscar(int id_b) {
        String sql = "SELECT * FROM equipos WHERE id = ?";
        String resultado = "";

        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_b);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                resultado =
                        "ID: " + rs.getInt("id") + "\n" +
                        "Nombre: " + rs.getString("nombre") + "\n" +
                        "Tipo: " + rs.getString("tipo") + "\n" +
                        "Marca: " + rs.getString("marca") + "\n" +
                        "Estado: " + rs.getString("estado");
            }else{
                resultado = "No se encontró el equipo.";
            }

        } catch (SQLException e){
            System.out.println("Error: "+ e.getMessage());
        }
        
        return resultado;
    }
    
    //método para eliminar información
    public String eliminar(int id_b) {
        String sql = "DELETE FROM equipos WHERE id = ?";

        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_b);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return "Registro eliminado correctamente.";
            } else {
                return "No se encontró un equipo con ese ID.";
            }
        } catch (SQLException e){
            return "Error: "+ e.getMessage();
        }
    }
    
    //método para ver información
    public ResultSet mostrar() {
        String sql = "SELECT * FROM equipos";

        try{
            Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        } catch (SQLException e) {
            return null;
    }
    
    
}
}
