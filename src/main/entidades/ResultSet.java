package main.entidades;

/**
 * Created by HP on 16/05/2017.
 */
public class ResultSet {

    private String productoId;

    private Double valor;

    public ResultSet(){

    }

    public ResultSet(String productoId, Double valor) {
        this.productoId = productoId;
        this.valor = valor;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
