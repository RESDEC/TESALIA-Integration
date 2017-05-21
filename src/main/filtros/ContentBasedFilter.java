package main.filtros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.csvreader.CsvReader;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import es.us.isa.FAMA.Benchmarking.PerformanceResult;
import es.us.isa.FAMA.Reasoner.Question;
import es.us.isa.FAMA.Reasoner.Reasoner;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;
import main.entidades.*;

public class ContentBasedFilter implements Question {

	private String csv;	//	CSV lectura

	private String csvPrdCrc;	//	CSV lectura, lstProductos y sus caracteristicas

	private String usuarioBase;	//	Usuario base

	private ArrayList<String> lstCaracteristicasBase = new ArrayList<>();	//	Caracteristicas Base

	private ArrayList<DataSet> lstDataSetInicial = new ArrayList<>();	//	Data Inicial del CSV

	private ArrayList<DataSet> lstDataSetFiltrada = new ArrayList<>();	//	Data Inicial filtrada por Usuario Base

	private ArrayList<MatrizReferencial> lstMatrizReferencial = new ArrayList<>();	//	Matriz de referencia

	private ArrayList<MatrizBooleana> lstMatrizBooleana = new ArrayList<>();	//	Matriz booleana

	private ArrayList<Producto> lstProductos = new ArrayList<>();	//	Lista de producto con sus caracteristicas

	private ArrayList<ResultSet> lstResultSet = new ArrayList<>();	//	Lista de respuesta

//	CONSTRUCTORES

//	METODOS
	public void setInputData(String csv){
		this.csv = csv;
	}

	public void setDataProductos(String csv){
		this.csvPrdCrc = csv;
	}

	public void setUsuarioBase(String usuarioBase){
		this.usuarioBase = usuarioBase;
	}

	public void setLstCaracteristicasBase(ArrayList<String> lstCaracteristicasBase) {
		this.lstCaracteristicasBase = lstCaracteristicasBase;
	}

	private void lecturaDataInicialCsv(String csv) throws IOException {

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

	private void lecturaProductoCrcCsv(String csv) throws IOException {

		CsvReader products = new CsvReader(csv);
		Boolean existe = false;

		products.setDelimiter(';');
		products.readHeaders();

		while (products.readRecord()){

			existe = false;
			String productId = products.get("productoId");
			String caracteristicaId = products.get("caracteristicaId");

//			Almacena el Array de Productos y sus caracteristicas.
			for (Producto p : lstProductos){
				 if (p.getProductoId().equals(productId)){
					 existe = true;
					 p.getCaracteristicas().add(caracteristicaId);
					 break;
				 }
			}
			if (!existe){
				ArrayList<String> caracteristicas = new ArrayList<>();
				caracteristicas.add(caracteristicaId);
				Producto prdNuevo = new Producto(productId, caracteristicas);
				lstProductos.add(prdNuevo);
			}
		}
		products.close();
	}

	public PerformanceResult answer(){
		PerformanceResult res = null;
		System.out.println("Content Based Filter - Base user: " + usuarioBase);
		System.out.println("Content Based Filter - Base features: ");

		for (String features : lstCaracteristicasBase){
			System.out.println("	- " + features);
		}

		try {
			lecturaDataInicialCsv(csv);
			lecturaProductoCrcCsv(csvPrdCrc);
			cargaMatrizReferencial();
			cargaMatrizBooleana();
			cargaMatrizResultado();

		}catch (Exception e){
			System.out.println(e.toString());
		}

		return res;
	}

	private void cargaMatrizReferencial(){
		System.out.println("Content Based Filter: Loading Referencial Matrix...");
		this.lstMatrizReferencial.clear();
		for(String caracteristica : lstCaracteristicasBase){
			MatrizReferencial mr = new MatrizReferencial(usuarioBase, caracteristica, 0.0, 0.0);
			lstMatrizReferencial.add(mr);
		}

		filtrarDataInicial();

		System.out.println("Content Based Filter: Updating Referencial Matrix...");
//		Para cada data set de la lista filtrada por los usuarios.
		for (DataSet ds : lstDataSetFiltrada){
//			Para cada producto en la lista de productos con sus caracteristicas
			for(Producto prd : lstProductos){
//				Si el producto es igual, busco sus caracteristicas.
				if(ds.getProductoId().equals(prd.getProductoId())){
//					Por cada caracteristica en las caracteristicas del producto
					for (String crc : prd.getCaracteristicas()){
//						Para cada caracteristica en la lista de caracteristicas base
						for (String crcBase : lstCaracteristicasBase){
//							Si, la caracteristica es igual a la caracteristica base
							if (crc.equals(crcBase)){
//								Para cada MR en la lista.
								for (MatrizReferencial mr : lstMatrizReferencial){
//									Si la caracteristica es igual a la del MR
									if(mr.getCaracteristica().equals(crcBase)){
										mr.setSumatoria(mr.getSumatoria() + ds.getValoracion());
										mr.setContador(mr.getContador() + 1);
										break;
									}
								}
								break;
							}
						}
					}
					break;
				}
			}
		}
	}

	private void cargaMatrizBooleana(){
		this.lstMatrizBooleana.clear();
		System.out.println("Content Based Filter: Loading Boolean Matrix...");

//		Para cada producto en la lista de productos
		for (Producto prd : lstProductos){

//			Para cada mr en la lista de matriz referencial
			for (MatrizReferencial mr : lstMatrizReferencial){
				Boolean existe = false;

//				Para cada caracteristica en la lista de caracteristicas del producto
				for (String c : prd.getCaracteristicas()){
					if (c.equals(mr.getCaracteristica())){
						existe = true;
						break;
					}else{
						existe = false;
					}
				}
				MatrizBooleana mb = new MatrizBooleana(this.usuarioBase, prd.getProductoId(), mr.getCaracteristica(), existe);
				this.lstMatrizBooleana.add(mb);
			}
		}
	}

	private void cargaMatrizResultado(){
		System.out.println("Content Based Filter: Loading Result Set...");

//		Para cada elemento en la matriz booleana (Producto).
		for (MatrizBooleana mb : lstMatrizBooleana){

//			Existe la caracteristica
			if (mb.getExiste()) {

//				Para cada elemento en la matriz referencial
				for (MatrizReferencial mr : lstMatrizReferencial) {

//					Si la caracteristica es la misma de la matriz booleana.
					if (mb.getCaracteristica().equals(mr.getCaracteristica())){
						ResultSet rs = new ResultSet(mb.getProducto(), usuarioBase, mr.getSumatoria());
						lstResultSet.add(rs);
					}
				}
			}
		}

//		Filtrando unicidad de Producto.
		Double SumValorPrd = 0.0;	//	Cargara la sumatoria de valores por caracteristicas de cada producto.
		ArrayList<ResultSet> lstResultSet2 = new ArrayList<>();	//	Tomara los nuevos valores.
		for (Producto prd : lstProductos){
			SumValorPrd = 0.0;
			for (ResultSet rs : lstResultSet){
				if (prd.getProductoId().equals(rs.getProductoId())){
					SumValorPrd += rs.getValor();
				}
			}

			ResultSet rs = new ResultSet(prd.getProductoId(), SumValorPrd);
			lstResultSet2.add(rs);
		}

//		Asigno el nuevo valor del arreglo de resultados.
		this.lstResultSet.clear();
		this.lstResultSet = lstResultSet2;

//		Ordenando pro valores.
		Collections.sort(this.lstResultSet, new Comparator<ResultSet>() {
			public int compare(ResultSet o1, ResultSet o2) {
				return o2.getValor().compareTo(o1.getValor());
			}
		});


//		Impreison de Resultado
		System.out.println("Content Based Filter - Result Set: ");
		for (ResultSet rs : lstResultSet){
			System.out.println("	- Product: " + rs.getProductoId() + " Value: " + rs.getValor().toString());
		}
	}

//	Obtiene un arreglo con las valoraciones del usuario base
	private void filtrarDataInicial(){
		for (DataSet ds : lstDataSetInicial){
			if (ds.getUsuarioId().equals(usuarioBase)){
				this.lstDataSetFiltrada.add(ds);
				ds.toString();
			}
		}
	}

	@Override
	public Class<? extends Reasoner> getReasonerClass() {
		return null;
	}

}
