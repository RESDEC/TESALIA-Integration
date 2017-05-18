package main.filtros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.csvreader.CsvReader;

import com.sun.corba.se.spi.orbutil.fsm.Guard;
import es.us.isa.FAMA.Benchmarking.PerformanceResult;
import es.us.isa.FAMA.Reasoner.Question;
import es.us.isa.FAMA.Reasoner.Reasoner;
import es.us.isa.FAMA.stagedConfigManager.Configuration;
import main.entidades.DataSet;
import main.entidades.ResultSet;

public class ColaborativeFiltering implements Question {

	private String csv;

	private Configuration userConf;	//	No se usa, ya que el producto base no proviene de un listado de productos que usaron 1 usuario en especifico.

	private Configuration productConf;	//	Usare String.

	private String producto;

	private boolean asc = false;

	private ArrayList<DataSet> lstDataSetInicial = new ArrayList<>();

	private ArrayList<DataSet> lstDataSetMtx = new ArrayList<>();

	private ArrayList<ResultSet> lstResultSet = new ArrayList<>();

//	CONTRUCTORES

//	METODOS
	public void setInputData(String csv){
		this.csv = csv;
	}

	public void setProductoBase(String producto){
		this.producto = producto;
	}

	public void setUserBaseConf(Configuration userConf) {
		this.userConf= userConf;
	}

	public void setProductBaseConf(Configuration productConf) {
		this.productConf= productConf;
	}

	public PerformanceResult answer() throws IOException{
		PerformanceResult res = null;

		lecturaCsv(csv);
		colavorativeDataSet();

		return res;
	}

	private void lecturaCsv(String csv) throws IOException{

		CsvReader products = new CsvReader(csv);

		products.setDelimiter(';');
		products.readHeaders();

		while (products.readRecord())
		{
			String productId = products.get("productoId");
			String usuarioId = products.get("usuarioId");
			String valoracion = products.get("valoracion");

			DataSet ds = new DataSet(productId, usuarioId, Double.parseDouble(valoracion));

			this.lstDataSetInicial.add(ds);
		}

		products.close();
	}

	private void colavorativeDataSet(){

		System.out.println("Colaborative Filtering : Coeff. Pearson...");

		matrizProductoUsr();

		ArrayList<DataSet> lstDataSetBase = new ArrayList<>();	//	DataSet del producto Base
		ArrayList<String> lstProductosItr = new ArrayList<>();	//	Productos a iterar
		ArrayList<DataSet> lstDataSetIterar = new ArrayList<>();	//	productos a iterar y sus app con valores

		System.out.println("Base product : " + this.producto);
//		Obtengo el DataSet del producto Base
		for (DataSet ds0 : lstDataSetMtx){
			if(ds0.getProductoId().equals(this.producto)){
				lstDataSetBase.add(ds0);
			}
		}

//		Obtengo el DataSet de los productos que sean diferentes al escogido.
		String productoX = "";
		for (DataSet ds0 : this.lstDataSetMtx){
			if (!ds0.getProductoId().equals(this.producto)){
				lstDataSetIterar.add(ds0);
				if (!productoX.equals(ds0.getProductoId())){
					productoX = ds0.getProductoId();
					lstProductosItr.add(productoX);
				}
			}
		}

		//  Conjunto de arrays - Coeficiente de Pearson
		ArrayList<Double> x = new ArrayList<Double>();  //  Array Base
		ArrayList<Double> y = new ArrayList<Double>();  //  Array a comparar
		ArrayList<Double> x2 = new ArrayList<Double>();
		ArrayList<Double> xy = new ArrayList<Double>();
		ArrayList<Double> y2 = new ArrayList<Double>();

		double xSum = 0.0;
		double ySum = 0.0;
		double xMed = 0.0;
		double yMed = 0.0;
		double y2Sum = 0.0;
		double xySum = 0.0;
		double x2Sum = 0.0;
		double coeff = 0.0;

		System.out.println("Comparing...");
		for (String prd0 : lstProductosItr){
			x.clear();
			y.clear();
			x2.clear();
			xy.clear();
			y2.clear();
			xSum = 0.0;
			ySum = 0.0;

//			Verifica que el tamaño del DataSet del producto base sea mayor a 0.
			if (lstDataSetBase.size() > 0){
				System.out.println("	-> " + prd0 + "...");
				for(DataSet ds1 : lstDataSetBase){
					for (DataSet ds2 : lstDataSetIterar){
						if (ds1.getUsuarioId().equals(ds2.getUsuarioId()) && ds2.getProductoId().equals(prd0)){
							x.add(ds1.getValoracion());
							xSum += ds1.getValoracion();
							y.add(ds2.getValoracion());
							ySum += ds2.getValoracion();
						}
					}
				}
			}

			double z = 0.0;
			z = coeffPearson(x, y);
			ResultSet rs = new ResultSet(prd0, z);
			if (z != 0) {
				lstResultSet.add(rs);
			}
		}

		if (lstResultSet.size() > 0) {
//			Orden Descendente.
			Collections.sort(this.lstResultSet, new Comparator<ResultSet>() {
				public int compare(ResultSet o1, ResultSet o2) {
					return o2.getValor().compareTo(o1.getValor());
				}
			});

			Integer i = 0;
			System.out.println("Coeff. Pearson - Result Data...");
			for (ResultSet rs : lstResultSet) {
				i += 1;
				System.out.println("	"+ i.toString() + ".- " + rs.getProductoId() + ": " + rs.getValor().toString());
			}
		}else{
			System.out.println("Coeff. Pearson - ERROR: Not enought data.");
		}
	}

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
		}
		return coeff;
	}

	private void matrizProductoUsr(){

//		Ordeno el conjunto de datos por producto y usuario
		orderProductoUsr();

//		Filtro la data de forma que quede el Producto - Usuario - Valor (Promedio)
		String productId = "";
		String usuarioId = "";
		Double prdUsrValTot = 0.0;
		Integer prdUsrCntTot = 0;
		Double prdUsrValor = 0.0;
		for (DataSet ds1 : lstDataSetInicial){
//			Quiebre de producto
			if (! ds1.getProductoId().equals(productId)){
				productId = ds1.getProductoId();
				usuarioId = "";
				for(DataSet ds2 : lstDataSetInicial){
//					Busca en el data set donde los registros tengan el mismo producto.
					if(productId.equals(ds2.getProductoId())){
//						Quiebre de Usuario
						if(!usuarioId.equals(ds2.getUsuarioId())){
							usuarioId = ds2.getUsuarioId();
							prdUsrCntTot = 0;
							prdUsrValor = 0.0;
							prdUsrValTot = 0.0;
							for (DataSet ds3 : lstDataSetInicial){
//								Producto y Usuario a buscar en el data set inicial.
								if (productId.equals(ds3.getProductoId()) && usuarioId.equals(ds3.getUsuarioId())) {
									prdUsrCntTot += 1;
									prdUsrValTot += ds3.getValoracion();
								}
							}

//							El total de valoraciones de ese usuario en es producto es devidio para el total de veces encontrado (Promedio). Y se asigna al ArrayList
							prdUsrValor = prdUsrValTot / prdUsrCntTot;
							DataSet dsX = new DataSet(productId, usuarioId, prdUsrValor);
							this.lstDataSetMtx.add(dsX);
						}
					}
				}
			}
		}

	}

	private void orderProductoUsr() {

		Collections.sort(lstDataSetInicial, new Comparator() {

			public int compare(Object o1, Object o2) {

				String x1 = ((DataSet) o1).getProductoId();
				String x2 = ((DataSet) o2).getProductoId();
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				} else {
					String y1 = ((DataSet) o1).getUsuarioId();
					String y2 = ((DataSet) o2).getUsuarioId();
					return y1.compareTo(y2);
				}
			}});
	}

	@Override
	public Class<? extends Reasoner> getReasonerClass() {
		return null;
	}

//	GETTER Y SETTERS

}
