package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import java.util.ArrayList;

public class Reserva {

    private Dispositivo dispositivo;
    private String motivo;
    private String direccion;
    private ArrayList<Double> ubicacion; //con GPS
    private boolean enviarCorreo;
    private String estado;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Double> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(ArrayList<Double> ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEnviarCorreo() {
        return enviarCorreo;
    }

    public void setEnviarCorreo(boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }
}
