package com.mycompany.inventario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//Ventana de autenticación de los usuarios

public class VistaLogin extends JFrame {
    // Campo de texto para ingresar el nombre de usuario
    private JTextField txtUsername;
    // Campo de contraseña
    private JPasswordField txtPassword;
    // Botón para iniciar el proceso de autenticación
    private JButton btnLogin;

    
    //Constructor de la ventana de login
    public VistaLogin() {
        setTitle("Iniciar Sesión");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Ingresar");
        panel.add(new JLabel()); // espacio vacío
        panel.add(btnLogin);

        add(panel);
    }

    //Retorna el username ingresado por el usuario
    //El controlador lo usa para enviarlo al modelo
    public String getUsername() { return txtUsername.getText(); }
    //Retorna la contraseña ingresada.
    //Se convierte de char[] a String para facilitar su uso
    public String getPassword() { return new String(txtPassword.getPassword()); }

    public void setLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}