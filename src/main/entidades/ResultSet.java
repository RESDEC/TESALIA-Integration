package main.entidades;

/**
 * Created by HP on 16/05/2017.
 */
public class ResultSet {

    private String productoId;

    private String usuarioId;

    private Double valor;

//    CONSTRUCTORES
    public ResultSet(){

    }

    public ResultSet(String productoId, Double valor) {
        this.productoId = productoId;
        this.valor = valor;
    }

    public ResultSet(String productoId, String usuarioId, Double valor) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.valor = valor;
    }

//    GETTER Y SETTER
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "productoId='" + productoId + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", valor=" + valor +
                '}';
    }
}
