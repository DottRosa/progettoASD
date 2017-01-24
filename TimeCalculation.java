

import java.util.*;
import java.io.*;

/**
* Programma per il calcolo delle ripetizioni, dei tempi medi, tempi medi netti e intervalli di
* confidenza del programma risolutivo e di generazione casuale di grafi.
*
* @author  Rosa Marco
* @since   2016-09-24 
*/

public class TimeCalculation{
	/**
	 * Granularità dell'orologio di sistema. Il suo valore è in secondi.
	 */
	private final double DELTA;
	/**
	 * Errore massimo tollerato
	 */
	private static final double MAX_ERROR = 0.05;
	/**
	 * Tempo calcolato per ottenere un errore di al più MAX_ERROR
	 */
	private final double MINIMUM_TIME;
	
	private String randomPath;
	private String fixedPath;
	private int fixedNumber;
	
	
	
	/**
	 * Costruttore che setta le tre variaibili passate in input
	 *
	 * @param randomPath: il path dove verranno salvati i grafi casuali
	 * @param fixedPath: il path dove verranno salvate le risoluzioni dei grafi fissi
	 * @param fixedNumber: il numero di nodi del grafo fisso
	 */
	public TimeCalculation(String randomPath, String fixedPath, int fixedNumber){
		this.randomPath = randomPath;
		this.fixedPath = fixedPath;
		this.fixedNumber = fixedNumber;
		this.DELTA = granularity();
		MINIMUM_TIME = DELTA/MAX_ERROR;
	}
	
	
	/**
	 * Calcola la granularità del sistema in millisecondi.
	 *
	 * @return il valore della granularità.
	 */
	private long granularity(){
		long t0 = getTime();
		long t1 = getTime();
		
		while(t0 == t1){
			t1 = getTime();
		}
		
		return t1-t0;
	}
	
	
	
	
	/**
	 * Restituisce il tempo attuale in millisecondi.
	 *
	 * @return il tempo in millisecondi.
	 */
	private long getTime(){
		return System.currentTimeMillis();
	}
	
	
	
	
	/**
	 * Metodo che riceve il programma risolutivo e un'input per il calcolo delle ripetizioni.
	 *
	 * @param prog: il programma risolutivo
	 * @param input: grafo in formato stringa da risolvere
	 *
	 * @return il valore massimo delle ripetizioni.
	 */
	public int getReps(Program prog, String input){

		long t0 = 0, t1 = 0;
		int rip = 1;
		String output = "";
		
		while(t1-t0 < MINIMUM_TIME){
			rip = rip*2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++){
				output = prog.resolve(input);
				prog = new Program();
			}
			t1 = getTime();
		}
		
		int max = rip;
		int min = rip/2;
		int incorrectCycles = 5;
		
		while(max-min >= incorrectCycles){
			
			rip = (max+min)/2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++){
				output = prog.resolve(input);
				prog = new Program();
			}
				
			t1 = getTime();
			
			if(t1-t0 <= MINIMUM_TIME)
				min = rip;
			else
				max = rip;
		}
		
		generateOutput(output);
		
		return max;
	}
	
	
	
	
	/**
	 * Metodo che riceve un generatore casuale di grafi e un'input per il calcolo delle ripetizioni.
	 *
	 * @param random: programma per la creazione di grafi casuali
	 *
	 * @return il valore massimo delle ripetizioni.
	 */
	public int getReps(RandomGraphs random){

		long t0 = 0, t1 = 0;
		int rip = 1;
		
		while(t1-t0 < MINIMUM_TIME){
			rip = rip*2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++){
				random.getGraph(0);
			}
			t1 = getTime();
		}
		
		int max = rip;
		int min = rip/2;
		int incorrectCycles = 5;
		
		while(max-min >= incorrectCycles){
			
			rip = (max+min)/2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++)
				random.getGraph(0);
				
			t1 = getTime();
			
			if(t1-t0 <= MINIMUM_TIME)
				min = rip;
			else
				max = rip;
		}
		
		return max;
	}
	
	
	
	
	/**
	 * Metodo che riceve il programma risolutivo, un generatore casuale di grafi e un'input per il calcolo delle ripetizioni.
	 *
	 * @param prog: il programma risolutivo
	 * @param random: programma per la creazione di grafi casuali
	 * @param input: grafo in formato stringa da risolvere
	 *
	 * @return il valore massimo delle ripetizioni.
	 */
	public int getReps(Program prog, RandomGraphs random, String input){

		long t0 = 0, t1 = 0;
		int rip = 1;
		String output = "";
		
		while(t1-t0 < MINIMUM_TIME){
			rip = rip*2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++){
				output = prog.resolve(input);
				random.getGraph(0);
			}
			t1 = getTime();
		}
		
		int max = rip;
		int min = rip/2;
		int incorrectCycles = 5;
		
		while(max-min >= incorrectCycles){
			
			rip = (max+min)/2;
			t0 = getTime();
			
			for(int i=0; i<rip; i++){
				output = prog.resolve(input);
				random.getGraph(0);
			}
			
			t1 = getTime();
			
			if(t1-t0 <= MINIMUM_TIME)
				min = rip;
			else
				max = rip;
		}
		
		generateOutput(output);
		return max;
	}
	
	
	
	
		
	
	/**
	 * Calcola il tempo medio per la risoluzione del problema
	 *
	 * @param prog: il programma risolutivo
	 * @param input: grafo in formato stringa da risolvere
	 * @param rip: ripetizioni associate al programma risolutivo
	 *
	 * @return il tempo medio per la risoluzione del problema
	 */
	public double getAverageTime(Program prog, String input, int rip){
	
		String output = "";
		long t0 = getTime();
		
		for(int i=0; i<rip; i++){
			output = prog.resolve(input);	
		}
		
		long t1 = getTime();
		
		generateOutput(output);
		return (t1-t0)/(double)rip;
	}
	
	
	
	
	
	

	
	/**
	 * Calcola il tempo medio netto per la risoluzione del problema, tenendo conto anche della generazione
	 * casuale di grafi.
	 *
	 * @param prog: il programma risolutivo
	 * @param random: programma per la creazione di grafi casuali
	 * @param input: grafo in formato stringa da risolvere
	 *
	 * @return il tempo medio netto per la risoluzione del problema
	 */
	public double getAverageNetTime(Program prog, RandomGraphs random, String input){
		String randomInput = "", output = "";
		long ripTare = getReps(random); //Numero di ripetizioni per il generatore casuale di grafi
		long ripGross = getReps(prog, random, input); //Numero di ripetizioni di entrambi i programmi
		
		
		long t0 = getTime();
		
		for(int i=0; i<ripTare; i++)							//Calcolo il tempo necessario a creare numeri casuali
			random.getGraph((int)getTime());
		
		long t1 = getTime();
		long tTare = t1-t0;
		
		
		t0 = getTime();
		
		for(int i=0; i<ripGross; i++){			//Calcolo il tempo necessario per eseguire l'algoritmo con input differenti
			randomInput = random.getGraph(i);
			output = prog.resolve(randomInput);
			generateOutput(output);
		}
		
		t1 = getTime();
		
		long tGross = t1-t0;
		double averageNetTime = ((double)tGross/(double)ripGross) - ((double)tTare/(double)ripTare);
		
		return averageNetTime;
	}
	
	
	
	
	
	
	/**
	 * Calcola l'intervallo di confidenza per la risoluzione del problema.
	 *
	 * @param prog: il programma risolutivo
	 * @param random: programma per la creazione di grafi casuali
	 * @param input: grafo in formato stringa da risolvere
	 * @param number: numero di cicli da fare
	 * @param za: funzione di distribuzione normale
	 * @param delta: delta 
	 *
	 * @return l'intervallo di confidenza
	 */
	public ConfidenceRange getConfidenceRange(Program prog, RandomGraphs random, String input, int number, double za, double delta){
		double t = 0, sum2 = 0, cn = 0, m, e = 0, s, newDelta;
		
		do{
			for(int i=0; i<number; i++){
				m = getAverageNetTime(prog, random, input);
				t += m;
				sum2 += Math.pow(m, 2);
			}
			cn += number;
			
			e = t/cn;
			
			s = Math.sqrt(sum2/cn - Math.pow(e,2));
			
			newDelta = (1/Math.sqrt(cn))*za*s;
			
		} while(newDelta < delta);
		
		return (new ConfidenceRange(e, newDelta));
	}

	
	
	
	
	/**
	 * Crea i file generati dal programma risolutivo
	 *
	 * @param output: grafo risolto in formato stringa
	 */
	private void generateOutput(String output){
		File f = new File(fixedPath+""+fixedNumber+"_solved.dot");
		
		try{
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(output);
			bw.close();
		} catch (IOException e){
			System.err.println("ERRORE NELLA CREAZIONE DEL FILE "+fixedPath+""+fixedNumber+".dot");
			System.exit(1);
		}
	}
	
}