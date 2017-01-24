

import java.util.Scanner;

/**
* Classe principale che dato in input un grafo in formato .dot restituisce un secondo
* grafo in .dot aggiungendo il minor numero di archi possibili ad un nodo radice ed 
* individuando l'albero dei cammini minimi.
*
* @author  Rosa Marco
* @since   2016-09-24 
*/

public class Project{
	public static void main(String[] args){
		
		//Scannerizzo l'input
		Scanner scan = new Scanner(System.in);
		String line = "";
		
		//Leggo tutte le linee presenti
		while(scan.hasNext()){
			line += scan.nextLine()+"\n";	
		}
		
		if(line.equals("")){
			System.err.println("ERRORE NELLA LETTURA DEL FILE: il file è vuoto");
			System.exit(1);
		}
		
		DotManipulation manipulator = new DotManipulation();		
		DirectedGraph G = manipulator.getGraphFromFile(line);	//Leggo l'input e lo trasformo in un grafo
		
		
		G.DFS(false);	//Effettuo una ricerca in profondità per calcolare il tempo di fine visita di tutti i nodi
		
		G.DFS(true);	//Eseguo una ricerca in profondità del grafo trasposto per trovare le SCC
	
		int root = G.findRadix();	//Trovo il nodo radice

		G.addMinimumEdges(root);	//Aggiungo la quntità minima di archi al nodo radice

		G.BFS_visit(root);	//Eseguo una visita in ampiezza per settare le distanze e calcolare l'albero dei cammini minimi
		
		System.out.println(manipulator.createDotFile(G, root));	//Redirigo in output il risultato
	}
}