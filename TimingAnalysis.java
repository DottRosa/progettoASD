

import java.io.*;

/**
* Programma che utilizza la classe TimeCalculation calcolando i tempi per la
* risoluzione del problema.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class TimingAnalysis{
	private final static String ANALYSIS_FOLDER = "Analysis_folder/";
	private final static String FIXED_IN = ANALYSIS_FOLDER+"fixedInput/";
	private final static String FIXED_OUT = ANALYSIS_FOLDER+"fixedOutput/";
	private final static String RANDOM_RESOLUTION = ANALYSIS_FOLDER+"randomGraphsResolution/";
	private final static String REPORT_NAME = ANALYSIS_FOLDER+"TimeResults.log";
	private final static int NUMBER = 5;
	
	
	public static void main(String[] args){
		
		//Vengono create le directory che conterranno i grafi e gli esiti
		(new File(ANALYSIS_FOLDER)).mkdir();
		(new File(FIXED_IN)).mkdir();
		(new File(FIXED_OUT)).mkdir();
		(new File(RANDOM_RESOLUTION)).mkdir();
		
		//Creo gli input fissi
		int[] nodesNumber = {50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1500, 2000, 2500, 3000};
		final int N = nodesNumber.length;
		RandomGraphs fixedInput;
		String[] inputFiles = new String[N];
		
		for(int i=0; i<N; i++){
			fixedInput = new RandomGraphs((int)System.currentTimeMillis(), nodesNumber[i], FIXED_IN);
			inputFiles[i] = fixedInput.getGraph(nodesNumber[i]);
		}
		
		
		TimeCalculation c;
		Program prog;
		Results[] res = new Results[N];
		int j = 0;
		
		
		while(j < N){	//Eseguo il calcolo dei tempi per tutte le dimensioni dei grafi
			
			System.out.println("Sto calcolando i tempi su grafi da "+nodesNumber[j]+" nodi...");
			c = new TimeCalculation(RANDOM_RESOLUTION, FIXED_OUT, nodesNumber[j]);
		
			int rip = c.getReps(new Program(), inputFiles[j]);	//Calcolo le ripetizioni
			
			double at = c.getAverageTime(new Program(), inputFiles[j], rip);	//Calcolo il tempo medio
			
			double ant = c.getAverageNetTime(new Program(), 
											 new RandomGraphs((int)System.currentTimeMillis(),
											 nodesNumber[j], 
											 RANDOM_RESOLUTION), 
											 inputFiles[j]);		//Calcolo il tempo medio netto
			
			if(ant < 0){	//se il tempo medio netto è negativo ripete i calcoli
				continue;
			}
			
			ConfidenceRange cr = c.getConfidenceRange(new Program(), 
													  new RandomGraphs((int)System.currentTimeMillis(), 
													  nodesNumber[j], RANDOM_RESOLUTION), 
													  inputFiles[j], 
													  NUMBER, 
													  1.96d, 
													  0.05d);	//Calcolo l'intervallo di confidenza
			
										
			res[j] = new Results(nodesNumber[j]+".dot", rip, at, ant, cr);	//Salvo tutti i risultati
			
			System.out.println("| CALCOLO TERMINATO |");
			
			j++;
		}
		
		//Genero il file di .log che conterrà tutti i dati acquisiti
		File f = new File(REPORT_NAME);
		
		try{
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			String timeResults = "";
			
			for(int i=0; i<res.length; i++){
				timeResults += res[i].toString();
			}
			
			bw.write(timeResults);
			bw.close();
			
		} catch(IOException e){
			System.err.println("Errore nella scrittura del file");
		}
	}
}