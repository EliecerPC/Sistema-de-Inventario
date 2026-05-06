
package com.mycompany.inventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Controlador {
    private Modelo modelo;
    private Vista vista;
    // Nuevo - Usuario autenticado en la sesión actual
    private Usuario usuarioActual;

    public Controlador(Modelo modelo, Vista vista, Usuario usuario) {
        this.modelo = modelo;
        this.vista = vista;
        this.usuarioActual = usuario;
        
        // Aplicar permisos según rol
        aplicarPermisos();
        
        //insertar - Modificado
        vista.setInsertarListener(e -> {
            String mensaje = modelo.insertarEquipo(
                vista.getId(), vista.getNombre(), vista.getTipo(),
                vista.getMarca(), vista.getEstado()
            );
            vista.mostrarMensaje(mensaje);
            // Si la operación fue exitosa, se registra en la auditoría
            if (mensaje.contains("correctamente")) {
                modelo.registrarAuditoria(usuarioActual.getUsername(), "INSERTAR", vista.getId());
            }
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
            
            // Auditoría
            if (mensaje.contains("correctamente")) {
                modelo.registrarAuditoria(usuarioActual.getUsername(), "ELIMINAR", vista.getId());
            }
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
        
        //Editar registros - Modificado
        vista.setEditarListener(e -> {
            int id = vista.getId();
            String[] actual = modelo.obtenerEquipoPorId(id);
            if (actual == null) {
                vista.mostrarMensaje("No se encontró un equipo con ese ID.");
                return;
            }
            String nombre = vista.getNombre().trim().isEmpty() ? actual[0] : vista.getNombre().trim();
            String tipo   = vista.getTipo().trim().isEmpty()   ? actual[1] : vista.getTipo().trim();
            String marca  = vista.getMarca().trim().isEmpty()  ? actual[2] : vista.getMarca().trim();
            String estado = vista.getEstado().trim().isEmpty() ? actual[3] : vista.getEstado().trim();

            String mensaje = modelo.editar_registro(id, nombre, tipo, marca, estado);
            vista.mostrarMensaje(mensaje);
            // Auditoría
            // Si la edición fue exitosa, se registra en la auditoría
            if (mensaje.contains("correctamente")) {
                modelo.registrarAuditoria(usuarioActual.getUsername(), "EDITAR", id);
            }
        });
    }
    //Método que aplica restricciones en la interfaz según el rol del usuario
    private void aplicarPermisos() {
            String rol = usuarioActual.getRol();
            switch (rol) {
                case "solo_lectura":
                    // Deshabilita todas las acciones de modificación
                    vista.habilitarInsertar(false);
                    vista.habilitarEditar(false);
                    vista.habilitarEliminar(false);
                    break;
                case "editor":
                    // Puede insertar y editar, pero no eliminar
                vista.habilitarEliminar(false);
                break;
            // admin: todo habilitado por defecto
            }
        }
    
    }
    

