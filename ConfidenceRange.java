

/**
* Programma che raccoglie i dati relativi agli intervalli di confidenza
* e ne fornisce una rappresentazione.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class ConfidenceRange{
	private double e;
	private double delta;
	
	/**
	 * Costruisce un intervallo di confidenza
	 *
	 * @param e: la media
	 * @param delta: il delta dell'intervallo
	 */
	public ConfidenceRange(double e, double delta){
		this.e = e;
		this.delta = delta;
	}
	
	
	/**
	 * Restituisce una rappresentazione dell'intervallo di confidenza in base al parametro passato.
	 * Se true viene effettuata la somma (e-delta, e+delta), altrimenti viene restituita la notazione
	 * (e, +- delta).
	 *
	 * @param somma: valore booleano per indicare la somma o meno tra "e" e "delta"
	 *
	 * @return una rappresentazione dell'intervallo di confidenza
	 */
	public String toString(boolean somma){
		if(somma){
			return "("+(e-delta < 0 ? 0 : e-delta)+" ms, "+(e+delta)+" ms)";
		} else {
			return "("+e+" ms \u00B1 "+delta+" ms)";
		}
	}
}