

package com.mycompany.inventario;


public class Inventario {

    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema de Inventario");
        
        // Crear las instancias de MVC
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
        
        // Hacer visible la ventana
        vista.setVisible(true);
        
                   
   }  
    
    
}


