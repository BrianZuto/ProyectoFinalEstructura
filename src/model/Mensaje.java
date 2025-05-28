package model;

public class Mensaje {
    private String emisor;
    private String receptor;
    private String contenido;
    private String fecha;
    private boolean nuevo = true; // por defecto todo mensaje es nuevo


    public Mensaje(String emisor, String receptor, String contenido, String fecha) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

}
