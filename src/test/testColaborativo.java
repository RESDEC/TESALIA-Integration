package test;

import java.io.IOException;

import es.us.isa.FAMA.models.FAMAfeatureModel.Feature;
import es.us.isa.FAMA.stagedConfigManager.Configuration;
import main.filtros.ColaborativeFiltering;

public class testColaborativo {

	public static void main(String[] args) throws IOException {

		ColaborativeFiltering filter = new ColaborativeFiltering();
		//Elegimos el dataset 
		filter.setInputData("E:\\Rostan\\Documentos\\Programming\\Java\\Proyectos\\RESDEC\\Versionamiento\\TESALIA-Integration-master\\src\\test\\input\\androidData.csv");
		filter.setProductoBase("4947838651");

//		Configuration userConf=new Configuration(); // Puedes usar un string si lo prefieres
//		userConf.addElement(new Feature("Facebook"), 1);
//		userConf.addElement(new Feature("Tinder"), 1);
//
//		Configuration productConf=new Configuration();
//		productConf.addElement(new Feature("Galaxy S1"), 1);
//
//		filter.setUserBaseConf(userConf);
//		filter.setProductBaseConf(productConf);
		
		filter.answer();
	}
}
