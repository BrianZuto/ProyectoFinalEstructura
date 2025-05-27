package model;

import java.util.LinkedList;

public class Usuario {
    protected String nombre;
    protected String correo;
    protected String contraseña;
    protected LinkedList<String> intereses;

    public Usuario(String nombre, String correo, String contraseña, LinkedList<String> intereses) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.intereses = intereses;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContraseña() { return contraseña; }
    public LinkedList<String> getIntereses() { return intereses; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setIntereses(LinkedList<String> intereses) { this.intereses = intereses; }
}
