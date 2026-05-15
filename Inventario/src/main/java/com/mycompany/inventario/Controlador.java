
package com.mycompany.inventario;

import java.util.List;


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
        //Se modifica para que no tenga responsabilidades del modelo
        //Se modifica para buscar por estado
        vista.setBuscarListener(e -> {
            vista.limpiarTabla();
            int id = vista.getId();
            String estado = vista.getEstado().trim();

            if (id != -1) {
            // Busca por ID si hay un ID válido
            String[] equipo = modelo.buscar(id);
                if (equipo != null) {
                    vista.agregarFilaTabla(equipo);
                } else {
                    vista.mostrarMensaje("No se encontró el equipo");
                }
            } else if (!estado.isEmpty()) {
                // Busca por estado si el campo estado tiene algo
                List<String[]> resultados = modelo.buscarPorEstado(estado);
                    if (resultados.isEmpty()) {
                        vista.mostrarMensaje("No se encontraron equipos con ese estado");
                    } else {
                        for (String[] fila : resultados) {
                            vista.agregarFilaTabla(fila);
                        }
                    }
            } else {
                vista.mostrarMensaje("Ingresa un ID o un Estado para buscar");
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
            vista.limpiarTabla();
            List<String[]> equipos = modelo.mostrar();
            for (String[] fila : equipos) {
                vista.agregarFilaTabla(fila);
            }
        });

        
        //Editar registros - Modificado
        vista.setEditarListener(e -> {
            int id = vista.getId();
            String[] actual = modelo.buscar(id);
            if (actual == null) {
                vista.mostrarMensaje("No se encontró un equipo con ese ID.");
                return;
            }
            String nombre = vista.getNombre().trim().isEmpty() ? actual[1] : vista.getNombre().trim();
            String tipo   = vista.getTipo().trim().isEmpty()   ? actual[2] : vista.getTipo().trim();
            String marca  = vista.getMarca().trim().isEmpty()  ? actual[3] : vista.getMarca().trim();
            String estado = vista.getEstado().trim().isEmpty() ? actual[4] : vista.getEstado().trim();

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
    

