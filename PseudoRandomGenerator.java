

/**
* Programma per la creazione di numeri casuali impiegati nella costruzione
* di grafi random.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class PseudoRandomGenerator{
	/**
	 * Numeri costanti
	 */
	private final static int A = 16087;
    private final static int M = 2147483647;
    private final static int Q = 127773;
    private final static int R = 2836;
    
    private String[] nodesValues;
    private int n;
    private final static int A_LETTER = 97;
    
    /**
	 * Seme passato come parametro al costruttore
	 */
    private double seed;
    /**
	 * Array di double che conterrà i valori casuali generati
	 */
    private double[] val;
    
    
    /**
	 * Costruisce un generatore di numeri casuali
	 *
	 * @param seed: seme di partenza per la generazione di numeri casuali
	 * @param n: numero di nodi che dovrà avere il grafo
	 */
    public PseudoRandomGenerator(double seed, int n){
	    
	    this.seed = seed;
	    this.n = n;
	    val = new double[n*3];
	    nodesValues = new String[n];
	    
	    for(int i=0; i<n; i++){ 
		    nodesValues[i] = ""+i;
	    }
    }



	/**
	 * Crea un grafo casuale e lo restituisce.
	 *
	 * @return un grafo con sintassi .dot in formato stringa
	 */
    public String getRandomGraph(){
	    
	    //Riempio l'array di numeri casuali
	    for(int i=0; i<val.length; i++){
		    val[i] = getRandom()*1000;
	    }
	    //Imposto il nuovo seme
	    this.seed = val[1];
	    
	    return createDot();
    }
    
    
    
    
	/**
	 * Metodo che genera un numero pseudo-random a partire dal seed.
	 *
	 * @return numero pseudo-casuale compreso tra 0 e 1
	 */
	private double getRandom(){
		double hi = Math.ceil(seed/Q);
		double lo = seed - hi*Q;
		double test = A * lo - R * hi;
		
		if(test<0)
			seed = test + M;
		else 
			seed = test;
		
		return seed/M;
	}
	
	
	
	
	/**
	 * Genera una rappresentazione in formato stringa con sintassi .dot di un grafo 
	 * in base alla quantità dei nodi e ai numeri casuali estratti.
	 *
	 * @return una rappsentazione in formato stringa di un grafo
	 */
	private String createDot(){
		String dot = "digraph RandomGraph{\n";	//Nome del grafo
		
		for(int i=0; i<val.length-1; i++){
			
			//Prendo due indici casuali compresi tra 0 e n
			int indexA = (int)val[i] % n;		
			int indexB = (int)val[i+1] % n;
			
			//Se true allora viene creato un arco tra due nodi
			if(indexA - indexB > 0){
				
				if((indexA-indexB)%2 == 0){
					dot += nodesValues[indexA]+"->"+nodesValues[indexB]+";\n";
					
				} else {
					dot += nodesValues[indexB]+"->"+nodesValues[indexA]+";\n";
				}
				
			} else {
				
				dot += nodesValues[indexA]+";\n";
			}
		}
		dot += "}";
		return dot;
	}
}