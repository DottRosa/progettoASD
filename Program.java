
/**
* Classe che rappresenta l'algoritmo per la risoluzione del problema.
*
* @author  Rosa Marco
* @since   2016-09-24 
*/

public class Program{
	
	
	/**
	 * Risolve il grafo passato come parametro
	 *
	 * @param input: il grafo in formato stringa da risolvere
	 * @return il grafo risolto
	 */
	public String resolve(String input){
		DotManipulation manipulator = new DotManipulation();		
		DirectedGraph G = manipulator.getGraphFromFile(input);	//Leggo l'input e lo trasformo in un grafo	
		
		G.DFS(false);	//Effettuo una ricerca in profondità per trovare le componenti fortemente connesse
		
		G.DFS(true);
	
		int root = G.findRadix();	//Trovo il nodo candidato come radice

		G.addMinimumEdges(root);	//Aggiungo la quntità minima di archi al nodo radice

		G.BFS_visit(root);	//Eseguo una visita in ampiezza per settare le distanze e calcolare l'albero dei cammini minimi
			
		return (manipulator.createDotFile(G, root));	//Genero l'output
	}
}