package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

import es.us.isa.FAMA.Benchmarking.PerformanceResult;
import es.us.isa.FAMA.Reasoner.Question;
import es.us.isa.FAMA.Reasoner.Reasoner;
import es.us.isa.FAMA.stagedConfigManager.Configuration;

public class ColaborativeFiltering implements Question {

	private String csv;
	//Usa las input que consideres
	private Configuration userConf;
	private Configuration productConf;
	private boolean asc=false;
	
	public void setInputData(String csv){
		this.csv=csv;
	}
	
	public void setUserBaseConf(Configuration userConf) {
		this.userConf= userConf;		
	}
	
	public void setProductBaseConf(Configuration productConf) {
		this.productConf= productConf;		
	}
	
	@Override
	public Class<? extends Reasoner> getReasonerClass() {
		return null;
	}
	
	public PerformanceResult answer() throws IOException{
		PerformanceResult res = null;
		//Aquí va el codigo del filtrado colaborativo
        
		//leemos las inputs y ejecutamos
		CsvReader products = new CsvReader(csv);
		products.readHeaders();

		while (products.readRecord())
		{
			String productID = products.get("ProductID");
			String productName = products.get("ProductName");
			String supplierID = products.get("SupplierID");
			String categoryID = products.get("CategoryID");
			String quantityPerUnit = products.get("QuantityPerUnit");
			String unitPrice = products.get("UnitPrice");
			String unitsInStock = products.get("UnitsInStock");
			String unitsOnOrder = products.get("UnitsOnOrder");
			String reorderLevel = products.get("ReorderLevel");
			String discontinued = products.get("Discontinued");
			
			// perform program logic here
			System.out.println(productID + ":" + productName);

		
		
		}

		
		
		products.close();
		
		return res;
	}


	
	
	//añadir otros métodos privados si se necesitan e.g. Coef de pearson
	  public Double coeffPearson(List<Double> x, List<Double> y){
	        List<Double> x2 = new ArrayList<Double>();
	        List<Double> xy = new ArrayList<Double>();
	        List<Double> y2 = new ArrayList<Double>();

	        double xSum = 0.0;
	        double ySum = 0.0;
	        double xMed = 0.0;
	        double yMed = 0.0;
	        double y2Sum = 0.0;
	        double xySum = 0.0;
	        double x2Sum = 0.0;
	        double coeff = 0.0;

	        for (int i = 0; i < x.size(); i++) {
	            xSum += x.get(i);
	            ySum += y.get(i);
	        }

	        if (x.size() > 0) {

	            xMed = xSum/x.size();
	            yMed = ySum/y.size();

	            for (int i = 0; i < x.size(); i++) {
	                x2.add(Math.pow(x.get(i)-xMed, 2));         //  X
	                xy.add((x.get(i)-xMed)*(y.get(i)-yMed));    //  XY
	                y2.add(Math.pow(y.get(i)-yMed, 2));         //  Y

	                x2Sum += Math.pow(x.get(i)-xMed, 2);
	                xySum += (x.get(i)-xMed)*(y.get(i)-yMed);
	                y2Sum += Math.pow(y.get(i)-yMed, 2);
	            }
	        }
	        double raiz = 0.0;
	        raiz = Math.sqrt((x2Sum)*(y2Sum));
	        if (raiz > 0) {
	            coeff = xySum / raiz;
	            System.out.println("    El Coeff. Pearson es de : "+coeff);
	        }

	        return coeff;
	    }
	

}
