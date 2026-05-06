

package com.mycompany.inventario;

import java.sql.*;

public class Modelo {
    
    private static final String URL = "jdbc:sqlite:D:\\Documentos\\Programación\\Sistema-de-Inventario\\Inventario\\src\\main\\java\\com\\mycompany\\inventario/inventario.db";
    
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
    
    /*
    Métodos agregados para la función editar
    */
    
    //método para editar registros
    public String editar_registro(int id, String nombre, String tipo, String marca, String estado){
        String sql = "UPDATE equipos SET nombre = ?, tipo = ?, marca = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, tipo);
            ps.setString(3, marca);
            ps.setString(4, estado);
            ps.setInt(5, id);
            ps.executeUpdate();

            return "Datos actualizados correctamente.";
        } catch (SQLException e) {
            return "Error al actualizar: " + e.getMessage();
        }
    }
    
    //Nuevo método de búsqueda necesario para que editar funcione
    public String[] obtenerEquipoPorId(int id) {
        String sql = "SELECT nombre, tipo, marca, estado FROM equipos WHERE id = ?";
        try (Connection conn = conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new String[]{
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getString("marca"),
                    rs.getString("estado")
                };
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    
    /*
    Métodos nuevos para la validación de usuarios
    */
    
    /*Implementación de login para usuarios*/
    // Verificar login — retorna el rol si las credenciales son correctas, null si no
    
    public Usuario login(String username, String password) {
        // Consulta SQL para obtener el rol del usuario si las credenciales coinciden
        String sql = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(username, rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return null;
    }

// Registrar auditoría
    //Método que registra una acción en la tabla de auditoría
    
    public void registrarAuditoria(String username, String accion, int idEquipo) {
         // Consulta SQL para insertar un registro en la tabla auditoria
        String sql = "INSERT INTO auditoria(usuario, accion, id_equipo, fecha_hora) VALUES(?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, accion);
            ps.setInt(3, idEquipo);
            // Se obtiene la fecha y hora actual del sistema
            ps.setString(4, java.time.LocalDateTime.now().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error auditoría: " + e.getMessage());
        }
    }
    
}
