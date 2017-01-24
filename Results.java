

/**
* Programma per la gestione dei dati raccolti tramite la classe TimingAnalysis
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class Results{
	
	private String name;
	private int reps;
	private double averageTime;
	private double averageNetTime;
	private ConfidenceRange range;
	
	
	/**
	 * Costruttore di Results che crea a partire dai dati passati come parametri i risultati della
	 * risoluzione di un grafo "name"
	 *
	 * @param name: il nome del grafo
	 * @param reps: ripetizioni
	 * @param averageTime: tempo medio per la risoluzione del grafo
	 * @param averageNetTime: tempo medio netto per la risoluzione del grafo
	 * @param range: intervallo di confidenza
	 */
	public Results(String name, int reps, double averageTime, double averageNetTime, ConfidenceRange range){
		this.name = name;
		this.reps = reps;
		this.averageTime = averageTime;
		this.averageNetTime = averageNetTime;
		this.range = range;
	}
	
	
	
	/**
	 * Restituisce una rappresentazione in formato stringa dei dati raccolti
	 *
	 * @return una stringa contenente i dati
	 */
	public String toString(){
		String s = "%%%%%"+name+"%%%%%"
					+"\nRipetizioni: "+reps
					+"\nTempo medio: "+averageTime+" ms"
					+"\nTempo medio netto: "+averageNetTime+" ms"
					+"\nIntervallo (E \u00B1 \u0394): "+range.toString(false)
					+"\nIntervallo (E-\u0394 ; E+\u0394): "+range.toString(true)+"\n\n";
		
		return s;
	}
}