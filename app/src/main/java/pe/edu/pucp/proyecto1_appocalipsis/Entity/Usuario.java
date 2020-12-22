package pe.edu.pucp.proyecto1_appocalipsis.Entity;

public class Usuario {

    private String correo;
    private String nombre;
    private String rol;
    private String codigo;
    private boolean ti = false;

    public boolean isTi() {
        return ti;
    }

    public void setTi(boolean ti) {
        this.ti = ti;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
