
package com.mycompany.inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class Vista extends JFrame {
    // Componentes de la interfaz
    private JTextField txtId, txtNombre, txtTipo, txtMarca, txtEstado;
    private JButton btnInsertar, btnBuscar, btnEliminar, btnMostrar;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaResultados;
    
    public Vista() {
        setTitle("Sistema de Inventario");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(6, 2, 10, 10));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos del Equipo"));
        
        panelEntrada.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelEntrada.add(txtId);
        
        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);
        
        panelEntrada.add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        panelEntrada.add(txtTipo);
        
        panelEntrada.add(new JLabel("Marca:"));
        txtMarca = new JTextField();
        panelEntrada.add(txtMarca);
        
        panelEntrada.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panelEntrada.add(txtEstado);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        btnInsertar = new JButton("Insertar");
        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnMostrar = new JButton("Mostrar Todos");
        
        panelBotones.add(btnInsertar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        
        // Panel de resultados con tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Equipos"));
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Marca", "Estado"}, 0);
        tablaEquipos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEquipos);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        
        // Panel de resultados de búsqueda
        areaResultados = new JTextArea(5, 50);
        areaResultados.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaResultados);
        scrollTexto.setBorder(BorderFactory.createTitledBorder("Resultado de Búsqueda"));
        
        // Organizar paneles
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.add(panelEntrada, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTabla, scrollTexto);
        splitPane.setDividerLocation(350);
        
        add(panelNorte, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    
    // Métodos para obtener datos de los campos
    public int getId() {
        try {
            return Integer.parseInt(txtId.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public String getNombre() {
        return txtNombre.getText();
    }
    
    public String getTipo() {
        return txtTipo.getText();
    }
    
    public String getMarca() {
        return txtMarca.getText();
    }
    
    public String getEstado() {
        return txtEstado.getText();
    }
    
    // Métodos para limpiar campos
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTipo.setText("");
        txtMarca.setText("");
        txtEstado.setText("");
    }
    
    // Métodos para mostrar mensajes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public void mostrarResultado(String resultado) {
        areaResultados.setText(resultado);
    }
    
    public void agregarFilaTabla(Object[] fila) {
        modeloTabla.addRow(fila);
    }
    
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
    
    // Métodos para agregar eventos
    public void setInsertarListener(ActionListener listener) {
        btnInsertar.addActionListener(listener);
    }
    
    public void setBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }
    
    public void setEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }
    
    public void setMostrarListener(ActionListener listener) {
        btnMostrar.addActionListener(listener);
    }

}
