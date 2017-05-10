package main;

import java.io.File;

import es.us.isa.FAMA.Benchmarking.PerformanceResult;
import es.us.isa.FAMA.Reasoner.Question;
import es.us.isa.FAMA.Reasoner.Reasoner;

public class FirstDeployment implements Question {

	File csv;
	
	public void setInputData(File csv){
		this.csv=csv;
	}
	
	
	@Override
	public Class<? extends Reasoner> getReasonerClass() {
		return null;
	}
	
	public PerformanceResult answer(){
		PerformanceResult res = null;
		

		//Aquí va el codigo del popularity
		return res;
	}

}
