package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import java.util.ArrayList;

public class Dispositivo {
    private String tipo;
    private String foto;
    private String marca;
    private ArrayList<String> caracteristicas=new ArrayList<>();
    private ArrayList<String> incluye= new ArrayList<>();
    private int stock;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public ArrayList<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ArrayList<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public ArrayList<String> getIncluye() {
        return incluye;
    }

    public void setIncluye(ArrayList<String> incluye) {
        this.incluye = incluye;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
