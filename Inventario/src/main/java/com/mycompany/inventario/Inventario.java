

package com.mycompany.inventario;


public class Inventario {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        // Se crea la vista de login (primera ventana que verá el usuario)
        VistaLogin vistaLogin = new VistaLogin();

        //Define el comportamiento del botón "Ingresar"
        vistaLogin.setLoginListener(e -> {
            // Se intenta autenticar al usuario con las credenciales ingresadas
            Usuario usuario = modelo.login(vistaLogin.getUsername(), vistaLogin.getPassword());
            // Si el login es exitoso
            if (usuario != null) {
                vistaLogin.dispose(); // cierra el login
                Vista vista = new Vista(); // Se crea la vista principal del sistema
                Controlador controlador = new Controlador(modelo, vista, usuario); 
                
                // Se muestra la ventana principal
                vista.setVisible(true);
            } else {
                vistaLogin.mostrarError("Usuario o contraseña incorrectos.");
            }
        });

        // Se hace visible la ventana de login al iniciar el programa
        vistaLogin.setVisible(true);
    }
}


