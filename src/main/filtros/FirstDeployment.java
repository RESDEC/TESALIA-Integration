package main.filtros;

import java.io.IOException;
import java.util.*;

import com.csvreader.CsvReader;
import es.us.isa.FAMA.Benchmarking.PerformanceResult;
import es.us.isa.FAMA.Reasoner.Question;
import es.us.isa.FAMA.Reasoner.Reasoner;
import main.entidades.DataSet;
import main.entidades.ResultSet;

public class FirstDeployment implements Question {

	private String csv;

	private List<DataSet> lstDataSet = new ArrayList<>();

	private List<ResultSet> lstResultSet = new ArrayList<>();

//	CONSTRUCTORES


//	METODOS
	public void setInputData(String csv){
		this.csv=csv;
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

			this.lstDataSet.add(ds);
		}

		products.close();
	}

	private void calculoFirstDeployment(){

        Collections.sort(this.lstDataSet , Comparator.comparing(DataSet::getProductoId));

        DataSet ds0 = new DataSet();
        Double prdValTot = 0.0;
        Integer prdCntTot = 0;
        Double prdValor = 0.0;

        for (DataSet ds1 : lstDataSet){

            if(! ds1.getProductoId().equals(ds0.getProductoId())){
                ds0 = ds1;
                prdValTot = 0.0;

                for(DataSet ds2 : lstDataSet){
                    if(ds2.getProductoId().equals(ds0.getProductoId())){
                        prdValTot += ds2.getValoracion();
                        prdCntTot += 1;
                    }
                }

                prdValor = prdValTot.doubleValue() / prdCntTot.doubleValue();
                ResultSet rs = new ResultSet(ds0.getProductoId(), prdValor);
                this.lstResultSet.add(rs);
            }
        }

        Collections.sort(this.lstResultSet, new Comparator<ResultSet>() {
            public int compare(ResultSet o1, ResultSet o2) {
                return o2.getValor().compareTo(o1.getValor());
            }
        });

        System.out.println("FirstDeployment result...");
        Integer i = 0 ;
        for (ResultSet rs : lstResultSet){
            i += 1;
            System.out.println(i.toString() + ".- " + rs.getProductoId() + " " + rs.getValor());
        }
    }

    public PerformanceResult answer(){
        PerformanceResult res = null;
        try {
            lecturaCsv(csv);
            calculoFirstDeployment();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return res;
    }

    @Override
    public Class<? extends Reasoner> getReasonerClass() {
        return null;
    }

//	GETTER Y SETTTERS

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public List<DataSet> getLstDataSet() {
        return lstDataSet;
    }

    public void setLstDataSet(List<DataSet> lstDataSet) {
        this.lstDataSet = lstDataSet;
    }
}
