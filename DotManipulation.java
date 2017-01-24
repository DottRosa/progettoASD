

import java.io.*;

/**
* Classe per gestire l'input e l'output di file in formato .dot.
*
* @author  Rosa Marco
* @since   2016-09-24 
*/

public class DotManipulation{
	
	/**
	 * Metodo che, data una stringa che rappresenta un grafo in formato .dot, ne estrapola le 
	 * informazioni e crea una struttra dati di un grafo orientato.
	 *
	 * @param file: il contenuto del file in input letto da standard input
	 *
	 * @return un grafo orientato ottenuto dal file .dot
	 */
	public DirectedGraph getGraphFromFile(String file){		
			
		String[] lines = file.split("\n");
		
		String name = lines[0].substring(lines[0].indexOf(" ")+1, lines[0].indexOf("{"));
		
		DirectedGraph G = new DirectedGraph(name);
		
		G.setAdjList(getAllNodes(lines));	//Indico al grafo tutti i nodi presenti per istanziare la lista di adiancenza
		
		for(int i=1; i<lines.length-1; i++){	
			G.addNode(splitNodes(lines[i]));	//Aggiungo tutti gli archi tranne quelli ripetuti
		}
			
		return G;
	}
	
	
	
	
	
	
	
	
	/**
	 * Restituisce un array di lunghezza 2 contenente i due nodi presenti in una singola istruzione.
	 * Se Un nodo non punta ad un altro nodo, la seconda cella rimane vuota.
	 *
	 * @param line: linea di istruzione in .dot
	 *
	 * @return una coppia di nodi in formato stringa
	 */
	public String[] splitNodes(String line){
		String[] nodes = new String[2];
		nodes[0] = nodes[1] = "";
		line = line.replaceAll("\\s","");
		
		//Se non è presenta la freccia -> allora il nodo non punta ad un altro nodo
		if(line.indexOf(">") == -1){
			nodes[0] = line.substring(0, line.length()-1);
			return nodes;
		}
		
		nodes[0] = line.substring(0, line.indexOf("-"));	//Prelevo il primo nodo dell'istruzione
		nodes[1] = line.substring(line.indexOf(">")+1, line.length()-1);	//Prelevo il secondo nodo dell'istruzione
		
		return nodes;
	}
	
	
	
	
	
	
	
	
	/**
	 * Restituisce un array contentente tutti i nodi del grafo
	 *
	 * @param lines: array di istruzioni .dot
	 *
	 * @return un array di stringhe contentente tutti i nodi
	 */
	private String[] getAllNodes(String[] lines){
		String[] nodes = new String[lines.length*2-2];	//Il numero totale di nodi sarà al massimo due volte il numero delle istruzioni
		
		for(int i=0; i<nodes.length; i++){
			nodes[i] = "";
		}

		int k = 0;
		
		/*Percorro tutte le istruzioni interpretandone la sintassi e aggiungendo i nodi trovati all'array "nodes".
			Parto da 1 perchè la prima riga contiene il nome del grafo.*/
		for(int i=1; i<lines.length-1; i++){
			String[] pair = splitNodes(lines[i]);
			nodes[k] = pair[0];
			nodes[k+1] = pair[1];
			k = k+2;
		}
		
		for(int i=0; i<nodes.length; i++){	//Comparo tutte le celle dell'array per eliminare i nodi presenti più volte
			
			if(nodes[i] != ""){
				
				for(int j=i+1; j<nodes.length; j++){
					
					if(nodes[i].equals(nodes[j])){
						nodes[j] = "";
					}
				}
			}
		}
		
		int count = 0;
		for(int i=0; i<nodes.length; i++){		//Conto i nodi presenti
			count += nodes[i]=="" ? 0 : 1;
		}
		
		String[] allNodes = new String[count];
		
		k = 0;
		for(int i=0; i<nodes.length; i++){	//Riempio "allNodes" con tutti i nodi diversi da "" presenti in "nodes"
			if(nodes[i]!=""){
				allNodes[k] = nodes[i];
				k++;
			}
		}
		
		return allNodes;
	}	
	
	
	
	
	
	
	
	
	/**
	 * Metodo che fornisce lo standard output del grafo risolto.
	 *
	 * @param G: grafo orientato da trasformare in istruzioni .dot
	 * @param index: indice della radice del grafo
	 *
	 * @return il grafo in formato stringa
	 */
	public String createDotFile(DirectedGraph G, int index){
		String dotString = getDotFromGraph(G, index);
		return dotString;
	
	}
	
	
	
	/**
	 * Dato un grafo e l'indice del nodo radice viene creata una stringa che rappresenta il grafo in 
	 * formato .dot, con l'aggiunta di opportuni stili e label.
	 *
	 * @param G: grafo orientato da trasformare in istruzioni .dot
	 * @param index: indice della radice del grafo
	 *
	 * @return la stringa che rappresenta il grafo in formato .dot
	 */
	private String getDotFromGraph(DirectedGraph G, int index){
		Node[] adj = G.getAdj();
		
		//Imposto il nome del grafo
		String name = G.getName()+"_output "+"{";
		name = name.replaceAll("\\s","");
		String s = "digraph "+name+"\n";
		
		String root = adj[index].getDatum();	//Prendo il dato contenuto nel nodo radice
		
		s += root+" [label=\"root = "+root+" ; |E'|-|E| = "+G.getNewEdges()+"\"];\n";
			
		for(int i=0; i<adj.length; i++){	//Imposto i nomi di ogni nodo tramite label
			if(i != index){
				s += adj[i].getDatum()+" [label=\"d("+adj[index].getDatum()+","+adj[i].getDatum()+")="+adj[i].getDistance()+"\"];\n";
			}
		}
		
		for(int i=0; i<adj.length; i++){	//Rappresento la lista di adiancenza di ogni nodo come istruzioni .dot
			s += adj[i].toString();
		}
		s+="}";
		
		return s;
	}
}