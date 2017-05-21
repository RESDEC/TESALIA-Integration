package main.entidades;

import java.util.ArrayList;

/**
 * Created by Rostan on 20/05/2017.
 */
public class Producto {

    private String productoId;

    private ArrayList<String> caracteristicas;

//    CONSTRUCTORES
    public Producto(){

    }

    public Producto(String productoId, ArrayList<String> caracteristicas) {
        this.productoId = productoId;
        this.caracteristicas = caracteristicas;
    }

//    GETTER Y SETTERS
    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public ArrayList<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ArrayList<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "productoId='" + productoId + '\'' +
                ", caracteristicas=" + caracteristicas +
                '}';
    }
}
