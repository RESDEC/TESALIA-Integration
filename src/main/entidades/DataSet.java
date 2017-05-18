package main.entidades;

/**
 * Created by Rostan on 14/05/2017.
 */
public class DataSet {

    private String productoId;

    private String usuarioId;

    private Double valoracion;

    public DataSet(){

    }

    public DataSet(String productoId, String usuarioId, Double valoracion) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.valoracion = valoracion;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "productoId='" + productoId + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
