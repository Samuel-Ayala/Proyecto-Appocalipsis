package pe.edu.pucp.proyecto1_appocalipsis.Entity;

public class Reserva {

    private Dispositivo dispositivo;
    private String motivo;
    private String direccion;
    private String ubicacion;
    private boolean enviarCorreo;
    private String estado;
    //Agregar la sesion

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEnviarCorreo() {
        return enviarCorreo;
    }

    public void setEnviarCorreo(boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }
}
