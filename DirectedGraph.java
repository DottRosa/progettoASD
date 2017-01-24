

/**
* Classe che implementa la struttura dati di un grafo orientato, permettendone
* la gestione.
*
* @author  Rosa Marco
* @since   2016-09-24 
*/

public class DirectedGraph{
	private int N_NODES; 	//Numero di nodi
	
	private Node[] adj;		//Array che contiene tutti i nodi presenti nel grafo	
	
	private Node[] adjT;	//Array che contiene tutti i nodi presenti nel grafo trasposto

	private String name;	//Nome del grafo

	private int time;		//Variabile che rappresenta il tempo calcolato nel DFS	

	private int newEdges;	//Numero degli archi aggiunti al grafo	

	private Queue queue;	//Coda utile per la visita in ampiezza
	
	private int[] sccIndex;			//Contiene gli indici delle radici delle SCC
	
	private int belongsSccIndex;	//Mostra a quale SCC appartiene un nodo
	
	private int numScc;		//Numero di SCC identificate
	
	
	
	
	/**
	 * Costruttore di DirectedGraph che crea un grafo vuoto senza nome.
	 */
	public DirectedGraph(){
		this("");
	}
	
	
	
	
	
	
	
	/**
	 * Costruttore di DirectedGraph che crea un grafo vuoto con un nome.
	 *
	 * @param name: il nome del grafo
	 */
	public DirectedGraph(String name){
		this.name = name;
		N_NODES = 0;
		newEdges = 0;
		numScc = 0;
	}
	
	
	

	
	
	
	
	/**
	 * Imposta nel grafo tutti i nodi e genera anche il grafo trasposto.
	 *
	 * @param nodes: array di stringhe che contengono i dati relativi ad ogni nodo
	 */
	public void setAdjList(String[] nodes){
		this.N_NODES = nodes.length;
		this.adj = new Node[N_NODES];
		this.adjT = new Node[N_NODES];
		
		for(int i=0; i<N_NODES; i++){
			adj[i] = new Node(nodes[i]);
			adj[i].setIndexInAdj(i);
			
			adjT[i] = new Node(nodes[i]);
			adjT[i].setIndexInAdj(i);
		}
	}
	
	
	
	
	
	/**
	 * Restituisce l'array di nodi del grafo
	 *
	 * @return l'array di nodi del grafo
	 */
	public Node[] getAdj(){
		return adj;
	}
	
	
	
	
	
	
	/**
	 * Data una coppia di nodi viene creato l'arco e viene formata la lista di adiacenza.
	 * Allo stesso modo viene creata la lista di adiacenza del grafo trasposto.
	 *
	 * @param pair: array di lunghezza 2 che contiene la coppia di nodi presenti in un'unica istruzione
	 *
	 */
	public void addNode(String[] pair){
		//Cerco il nodo nodoA nell'array adj
		Node nodeA = new Node(pair[0]);
		int indexA = listIndexNode(nodeA);
		nodeA.setIndexInAdj(indexA);
		
		/*Se nell'istruzione vi era un secondo nodo, allora lo cerco nella lista di adiacenza di nodoA
			e se non è presente lo aggiungo.*/
		if(pair[1]!=""){
			Node nodeB = new Node(pair[1]);
			
			int indexB = listIndexNode(nodeB);
			nodeB.setIndexInAdj(indexB);
			
			//Se il nodo è presente non devo fare altro con la coppia
			if(adj[indexA].findNode(nodeB)){
				return;
			}
			
			adj[indexA].addEdge(nodeB);
			adj[indexB].setParent(indexA);
			
			adjT[indexB].addEdge(nodeA);
			adjT[indexA].setParent(indexB);
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Metodo che riceve un nodo nodoA e cerca in quale posizione dell'array "adj" si trova quel nodo.
	 *
	 * @param nodeA: il nodo da cercare nell'array "adj"
	 * @return l'indice dell'array in cui si trova nodoA, altrimenti -1
	 *
	 */
	private int listIndexNode(Node nodeA){
		for(int i=0; i<N_NODES; i++){
			if(adj[i].equals(nodeA)){
				return i;
			}	
		}
		return -1;
	}
	
	
	
	
	
	
	
	/**
	 * Metodo che setta il colore di tutti i nodi a bianco
	 */
	private void allWhite(){
		for(int i=0; i<N_NODES; i++){
			adj[i].setColor('w');
		}
	}
	
	
	
	
	
	
	/**
	 * Metodo che lavora sul grafo chiamante e crea il numero minimo di archi in modo che, a partire da una radice r,
	 * tutti gli altri nodi siano raggiungibili da r.
	 *
	 * @param index: l'indice del nodo che funge da radice
	 */
	public void addMinimumEdges(int index){

		for(int i=0; i<N_NODES; i++){
			adj[i].resetTime();	//Setto a 0 il tempo di discovery e di finish
		}
		
		//Rendo nuovamente bianchi tutti i nodi
		allWhite();
		
		//Effettuo una visita in profondità per scoprire tutti i figli del nodo radice
		DFS_visit(adj[index], adj);
		
		Node root =  adj[index];
		
		for(int i=0; i<N_NODES; i++){
			
			if(i!=index && adj[i].isWhite() && (adj[i].getParent() == -1 || adj[i].isStronglyConnected())){
				Node newChild = new Node(adj[i].getDatum());
				newChild.setNewEdge();
				newChild.setIndexInAdj(i);
				root.addEdge(newChild);
				newEdges++;
				allBlack(i);	//Setto tutti i figli del nodo con il colore nero
			}
		}
	}
	
	
	
	
	/**
	 * Metodo ricorsivo che dato un indice ne prende il nodo e i relativi figli e li pone di colore nero.
	 *
	 * @param index: l'indice del nodo a cui impostare a black i figli
	 */
	private void allBlack(int index){
		//Se il nodo è nero allora sono dentro un ciclo
		if(adj[index].isBlack()){
			return;
		}
		
		Node listAdj = adj[index].getAdj();
		adj[index].setColor('b');
		
		while(listAdj != null){
			allBlack(listAdj.getIndexInAdj());
			listAdj = listAdj.getAdj();
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Richiama per ogni nodo la visita in profondità DFS_visit. In base al valore in input
	 * effettua la chiamata sul grafo o sul grafo trasposto.
	 *
	 * @param transposed: se true verrà effettuato il DFS sul grafo trasposto, altrimenti su quello in input
	 */
	public void DFS(boolean transposed){
		time = 0;
		belongsSccIndex = -1;
		sccIndex = new int[N_NODES];
		
		for(int i=0; i<N_NODES; i++){	//Resetto gli indici dell'scc
			sccIndex[i] = -1;
		}
		
		if(transposed){
			int[] sortedIndex = getTiming();	//Prendo l'array degli indici ordinati in base al tempo di fine visita
			
			for(int i=0; i<N_NODES; i++){
				
				if(adjT[sortedIndex[i]].isWhite()){
					belongsSccIndex = sortedIndex[i];
					time = DFS_visit(adjT[sortedIndex[i]], adjT);
					numScc++;
				}
			}
			
		} else {
			
			for(int i=0; i<N_NODES; i++){
				if(adj[i].isWhite()){
					belongsSccIndex = i;
					time = DFS_visit(adj[i], adj);
				}
			}
		}		
	}
	
	
	
	
	
	/**
	 * Metodo ricorsivo che visita in profondità il grafo da un nodo di partenza.
	 *
	 * @param u: il nodo da cui partire per la visita in profondità
	 * @param list: l'array di liste di adiacenza da considerare
	 */
	private int DFS_visit(Node u, Node[] list){
		time++;
		Node adjU = u.getAdj();	//Prendo la lista di adiacenza del nodo
		u.setColor('g');
		u.setDiscovery(time);
		Node v = null;
		
		//Per ogni figlio di u richiamo ricorsivamente il metodo
		while(adjU != null){
			v = list[adjU.getIndexInAdj()];
			//Se è bianco lo visito
			if(v.isWhite()){
				time = DFS_visit(v, list);
			}
			//Se è grigio vuol dire che c'è un ciclo
			if(v.isGray()){
				list[u.getIndexInAdj()].setStronglyConnected();	//Marco il nodo come componente del ciclo
				
			}
			adjU = adjU.getAdj();

		}
		//Imposto il colore a nero
		u.setColor('b');
		time++;
		u.setFinish(time);
		sccIndex[u.getIndexInAdj()] = belongsSccIndex;	//Setto l'appartenenza del nodo ad una SCC
		return time;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Algoritmo per la visita in ampiezza che, partendo da una radice, scorre tutti i suoi figli.
	 * Una volta visitati tutti passa ai figli dei figli e così via. Durante la procedura vengono
	 * settate le distanze dei nodi dalla radice.
	 *
	 * @param root: l'indice del nodo che funge da radice da cui partire per la visita in ampiezza
	 */
	public void BFS_visit(int root){
		//Rendo bianchi tutti i nodi
		allWhite();
		
		//Setto il colore della radice a grigio e la sua distanza a 0
		adj[root].setColor('g');
		adj[root].setDistance(0);
		
		//Incodo la radice
		queue = new Queue(root);
		
		int dist, parent, son;
		
		while(queue != null){
			//Prendo la testa della coda
			parent = queue.headQueue();

			//Se il nodo è nero vuol dire che sono tornato indietro, quindi rimuovo u dalla coda e ignoro i passaggi successivi
			if(adj[parent].isBlack()){
				queue = queue.getNext();
				continue;
			}
			
			//Prendo la lista di adiancenza di quel dato nodo parent
			Node adjU = adj[parent].getAdj();
			
			while(adjU != null){

				son = adjU.getIndexInAdj();	//Indice del figlio del nodo "parent"
				
				if(adj[son].isWhite()){	//Se il nodo è bianco non è stato visitato, quindi non rischio di sovrascrivere la sua distanza
					dist = adj[parent].getDistance()+1;	//Distanza del nodo parent + 1
					adj[son].setDistance(dist);	//Setto la distanza del nodo figlio
					adj[son].setColor('g');	//Rendo grigio il nodo figlio
					adj[parent].addToTree(adj[parent].getDatum(), adj[son]); //Modifico il figlio impostando il genitore
					
					//Per evitare che nella coda vi sia ridondanza del nodo radice, effettuo un controllo
					if(son != root && adj[son].getEdges() > 0){
						queue.enqueue(son);	//Incodo il nodo figlio, ma solo se è diverso dal nodo radice
					}
				}
				
				adjU = adjU.getAdj();
			}
			//Rendo nero il nodo parent
			adj[parent].setColor('b');
			//rimuovo l'elemento in testa alla coda
			queue = queue.getNext();
		}
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Trova, tra una lista di nodi candidati, il nodo radice.
	 *
	 * @return l'indice del nodo scelto come radice
	 *
	 */
	public int findRadix(){
		int[] roots = new int[N_NODES];	//Array che conterrà gli indici dei nodi candidati a radice
		
		for(int i=0; i<N_NODES; i++){		//Riempio solo le celle corrisponendi all'indice, il resto -1
			roots[i] = -1;
			roots[sccIndex[i]] = sccIndex[i];
		}
		
		Node sccBoss, v;
		int sccRadix;
		
		for(int i=0; i<N_NODES; i++){
			sccBoss = adj[sccIndex[i]];		//Prendo ogni nodo del grafo e prendo la radice della sua scc
			Node adjU = adj[i].getAdj();	//Prendo per ogni nodo la sua lista di adiacenza
			
			while(adjU != null){
				
				if(!adjU.equals(sccBoss)){
					
					sccRadix = sccIndex[adjU.getIndexInAdj()];		//Per ogni figlio del nodo ne prendo la radice della sua scc
					
					roots[sccRadix] = -1;		//Imposto la radice trovata a -1
				}
				
				adjU = adjU.getAdj();
			}
				
		}
		int radix = 0;
		int radixLength = -1;
		
		for(int i=0; i<N_NODES; i++){	//Scorro tutti i nodi radice rimasti e scelgo quello che mi permette di raggiungerne di più
			
			if(roots[i] >= 0 && adj[roots[i]].getEdges() > radixLength){
				radix = roots[i];
				radixLength = adj[radix].getEdges();
			}
		}
		
		return radix;
	}
	
	
	
	
	
	/**
	 * Restituisce il nome del grafo
	 *
	 * @return il nome del grafo
	 */
	public String getName(){
		return name;
	}
	
	
	
	/**
	 * Restituisce il numero di nuovi archi aggiunti al grafo
	 * 
	 * @return il numero di nuovi archi
	 */
	public int getNewEdges(){
		return newEdges;
	}

	
	
	/**
	 * Ordina i tempi di fine visita dal più grande al più piccolo e
	 * restituisce un array contenente gli indici dei nodi ordinati secondo il 
	 * criterio sopracitato.
	 *
	 * @return l'indice del nodo scelto come radice
	 *
	 */
	public int[] getTiming(){
		Node[] list = new Node[N_NODES];
		int[] t = new int[N_NODES];
		int[] indices = new int[N_NODES];
		
		for(int i=0; i<N_NODES; i++){
			t[i] = adj[i].getFinish();
			list[i] = adj[i];
			indices[i] = i;
		}
		
		int temp, j;
		Node temp2;
		int tempInt;
		
		for(int i=1; i<N_NODES; i++){
			temp = t[i];
			j = i-1;
			temp2 = list[i];
			tempInt = indices[i];
			
			while(j >= 0 && t[j] < temp){
				t[j+1] = t[j];
				list[j+1] = list[j];
				indices[j+1] = indices[j];
				j--;
			}
			indices[j+1] = tempInt;
			t[j+1] =  temp;
			list[j+1] = temp2;
		}
		
		return indices;
	}
	
	
	
	
	
	
	
	
	

}