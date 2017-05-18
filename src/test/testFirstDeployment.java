package test;

import main.filtros.FirstDeployment;

/**
 * Created by Rostan on 14/05/2017.
 */
public class testFirstDeployment {

    public static void main(String[] args){
        FirstDeployment filter = new FirstDeployment();
        filter.setInputData("E:\\Rostan\\Documentos\\Programming\\Java\\Proyectos\\RESDEC\\Versionamiento\\TESALIA-Integration-master\\src\\test\\input\\androidData.csv");
        filter.answer();
    }
}
