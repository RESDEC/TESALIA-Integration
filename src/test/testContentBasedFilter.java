package test;

import main.filtros.ContentBasedFilter;

import java.util.ArrayList;

/**
 * Created by Rostan on 18/05/2017.
 */
public class testContentBasedFilter {

    public static void main(String[] args){
        ContentBasedFilter filter = new ContentBasedFilter();

//        Valoraciones d elos usuarios
        filter.setInputData("E:\\Rostan\\Documentos\\Programming\\Java\\Proyectos\\RESDEC\\Versionamiento\\TESALIA-Integration-master\\src\\test\\input\\androidData.csv");

//        Data de los producto sy sus caracteristicas
        filter.setDataProductos("E:\\Rostan\\Documentos\\Programming\\Java\\Proyectos\\RESDEC\\Versionamiento\\TESALIA-Integration-master\\src\\test\\input\\productoCaracteristicas.csv");

//        Usuario base para la prueba
        filter.setUsuarioBase("32");

//        Aqui van las caracteristicas escogidas por el usuario
        ArrayList<String> lstCaracteristicasBase = new ArrayList<>();
        lstCaracteristicasBase.add("115");
        lstCaracteristicasBase.add("20");
        lstCaracteristicasBase.add("15");
        filter.setLstCaracteristicasBase(lstCaracteristicasBase);

//        Llamado al metodo de respuesta
        filter.answer();
    }
}
