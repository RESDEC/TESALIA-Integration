package main.entidades;

/**
 * Created by Rostan on 18/05/2017.
 */
public class MatrizBooleana {

    private String usuario;

    private String producto;

    private String caracteristica;

    private Boolean existe;

//    CONSTRUCTORES
    public MatrizBooleana(){

    }

    public MatrizBooleana(String usuario, String producto, String caracteristica, Boolean existe) {
        this.usuario = usuario;
        this.producto = producto;
        this.caracteristica = caracteristica;
        this.existe = existe;
    }

//    GETTER Y SETTERS
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Boolean getExiste() {
        return existe;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    @Override
    public String toString() {
        return "MatrizBooleana{" +
                "usuario='" + usuario + '\'' +
                ", producto='" + producto + '\'' +
                ", caracteristica='" + caracteristica + '\'' +
                ", existe=" + existe +
                '}';
    }
}
