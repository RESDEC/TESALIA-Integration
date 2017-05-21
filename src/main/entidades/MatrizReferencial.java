package main.entidades;

/**
 * Created by Rostan on 18/05/2017.
 */
public class MatrizReferencial {

    private String usuario;

    private String caracteristica;

    private Double sumatoria;

    private Double contador;

//    CONSTRUCTOR
    public MatrizReferencial(){

    }

    public MatrizReferencial(String usuario, String caracteristica, Double sumatoria, Double contador) {
        this.usuario = usuario;
        this.caracteristica = caracteristica;
        this.sumatoria = sumatoria;
        this.contador = contador;
    }

//    GETTER Y SETTERS
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Double getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(Double sumatoria) {
        this.sumatoria = sumatoria;
    }

    public Double getContador() {
        return contador;
    }

    public void setContador(Double contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "MatrizReferencial{" +
                "usuario='" + usuario + '\'' +
                ", caracteristica='" + caracteristica + '\'' +
                ", sumatoria=" + sumatoria +
                ", contador=" + contador +
                '}';
    }
}
