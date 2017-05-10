package test;

import java.io.File;
import java.io.IOException;

import es.us.isa.FAMA.models.FAMAfeatureModel.Feature;
import es.us.isa.FAMA.stagedConfigManager.Configuration;
import main.ColaborativeFiltering;

public class testColaborativo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub


		ColaborativeFiltering filter = new ColaborativeFiltering();
		//Elegimos el dataset 
		filter.setInputData("input/android.csv");
		//filter.setInputData(new File("input/wordpress.csv"));
		
		Configuration userConf=new Configuration(); // Puedes usar un string si lo prefieres
		userConf.addElement(new Feature("Facebook"), 1);
		userConf.addElement(new Feature("Tinder"), 1);

		Configuration productConf=new Configuration();
		productConf.addElement(new Feature("Galaxy S1"), 1);
		
		filter.setUserBaseConf(userConf);
		filter.setProductBaseConf(productConf);
		
		filter.answer();//este metodo ejecuta e imprime por pantalla los resultados. 
	}

}
