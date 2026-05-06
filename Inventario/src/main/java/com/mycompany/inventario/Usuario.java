
package com.mycompany.inventario;

//Clase que representa al usuario autenticado en el sistema
public class Usuario {
    
    private String username;
    private String rol;

    
    //Constructor de la clase Usuario, utilizado cuando el login es exitoso y se obtiene la información
    public Usuario(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }

    //Retorna el nombre de usuario
    public String getUsername() { return username; }
    //Retorna el rol del usuario
    public String getRol() { return rol; }

    //Verifica si el usuario tiene rol de administrador
    public boolean esAdmin() { 
        return rol.equals("admin"); 
    }
    
    //Verifica si el usuario puede editar información
    public boolean esEditor() { 
        return rol.equals("editor") || rol.equals("admin"); 
    }
    
}
