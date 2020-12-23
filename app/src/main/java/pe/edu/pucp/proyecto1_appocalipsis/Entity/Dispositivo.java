package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Dispositivo implements Serializable {
    private String id;
    private String tipo;
    private String marca;
    private String caracteristicas;
    private String incluye;
    private StorageReference imagen;
    private int stock;

    public StorageReference getImagen() {
        return imagen;
    }

    public void setImagen(StorageReference imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getIncluye() {
        return incluye;
    }

    public void setIncluye(String incluye) {
        this.incluye = incluye;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
