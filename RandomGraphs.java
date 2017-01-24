

import java.io.*;

/**
* Programma per la creazione di grafi casuali.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class RandomGraphs{
	
	private String path;
	private int nodesNumber;
	private int seed;
	private PseudoRandomGenerator prg;
	
	
	/**
	 * Costruisce un generatore di grafi random
	 *
	 * @param seed: seme di partenza per la generazione di numeri casuali
	 * @param nodesNumber: numero di nodi che dovrà avere il grafo
	 * @param path: percorso dove salvare gli output
	 */
	public RandomGraphs(int seed, int nodesNumber, String path){
		this.seed = seed;
		this.nodesNumber = nodesNumber;
		this.path = path;
		prg = new PseudoRandomGenerator(seed,nodesNumber);
	}
	
	
	
	/**
	 * Crea un file .dot contenente un grafo casuale. Poichè il metodo può
	 * essere lanciato più volte, il parametro num indica l'indice del grafo, 
	 * in modo da poterlo distinguere dagli altri.
	 *
	 * @param num: i-esimo numero del grafo
	 * @return il grafo generato casualmente in formato stringa
	 */
	public String getGraph(int num){
		String graph = prg.getRandomGraph();
		createFile(graph, num);
		return graph;
		
	}
	
	
	
	/**
	 * Genera il file .dot del grafo generato casualmente
	 *
	 * @param s: grafo .dot in formato stringa
	 * @param i: i-esimo numero del grafo
	 */
	public void createFile(String s, int i){
		
		File f = new File(path+i+".dot");
		
		try{
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(s);
			bw.close();
			
		} catch (IOException e){
			System.err.println(e);
		}
	}
}