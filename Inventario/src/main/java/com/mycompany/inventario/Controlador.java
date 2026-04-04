
package com.mycompany.inventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Controlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        //insertar
        vista.setInsertarListener(e -> {
           String mensaje = modelo.insertarEquipo(
                vista.getId(),
                vista.getNombre(),
                vista.getTipo(),
                vista.getMarca(),
                vista.getEstado()
           ); 
           vista.mostrarMensaje(mensaje);
        });
        
        //buscar
        vista.setBuscarListener(e -> {
            int id = vista.getId();

            if (id == -1) {
                vista.mostrarMensaje("ID inválido");
                return;
            }

            try {
                vista.limpiarTabla();

                Connection conn = modelo.conectar();
                String sql = "SELECT * FROM equipos WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("estado")
                    };

                    vista.agregarFilaTabla(fila);
                } else {
                    vista.mostrarMensaje("No se encontró el equipo");
                }

                conn.close();

            } catch (Exception ex) {
                vista.mostrarMensaje("Error: " + ex.getMessage());
            }

        });
        
        //eliminar
        vista.setEliminarListener(e -> {
            String mensaje = modelo.eliminar(vista.getId());
            vista.mostrarMensaje(mensaje);
        });
        
        //mostrar todo
        vista.setMostrarListener(e -> {
            try {
                vista.limpiarTabla();

                Connection conn = modelo.conectar(); 
                ResultSet rs = modelo.mostrar();

                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("estado")
                    };

                    vista.agregarFilaTabla(fila);
                }

                conn.close(); 

            } catch (Exception ex) {
                vista.mostrarMensaje("Error: " + ex.getMessage());
            }
        });
    }
    }
    

