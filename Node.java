

/**
* Classe che rappresenta un nodo di un grafo orientato.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class Node{

	private String datum;	//Il dato contenuto nel nodo

	public Node son;		//Nodo successivo, cioè una "lista" di nodi	

	private int edges;		//Numero di figli del nodo

 	private int parent;		//Indice del nodo genitore
 	
	/*
	* Valori booleani che indicano di quale colore (true) è il nodo
 	*/
	private boolean WHITE;	
	private boolean GRAY;
	private boolean BLACK;
	
	/*
	* Discovery time e finish time del nodo
 	*/
	private int Dtime;
	private int Ftime;
	
	public boolean newEdge;			//Flag per capire se il nodo possiede un arco aggiunto

	public String parentInTree;		//Dato appartenente al padre nell'albero minimo

 	private int distance;			//Distanza del nodo dalla radice

 	private boolean scc;			//Campo booleano che indica se il nodo appartiene ad una componente fortemente connessa

 	private int indexInAdj;			//Indice del nodo nella lista adj di un grafo
	
	
	/**
	* Costruttore di Nodo che crea un nodo a partire da un dato. Il nodo successivo viene posto a null e
	* vengono istanziate le altre variabili.
	*
	* @param datum: il dato contenuto nel nodo
 	*/
	public Node(String datum){
		this.datum = datum;
		this.son = null;
		this.WHITE = true;
		this.GRAY = false;
		this.BLACK = false;
		this.edges = 0;
		this.Dtime = 0;
		this.Ftime = 0;
		this.newEdge = false;
		this.distance = 0;
		this.parent = -1;
		this.scc = false;
		this.indexInAdj = -1;
		this.parentInTree = "";
	}
	
	
	
	/**
	* Setta l'indice che il nodo possiede nella lista adj di un grafo
	*
	* @param indexInAdj: l'indice del nodo nella lista adj
 	*/
	public void setIndexInAdj(int indexInAdj){
		this.indexInAdj = indexInAdj;
	}
	
	
	/**
	* Restitiusce l'indice del nodo nella lista adj di un grafo
	*
	* @return l'indice del nodo nella lista adj
 	*/
	public int getIndexInAdj(){
		return this.indexInAdj;
	}
	
	
	
	/**
	* Metodo che restituisce il dato del nodo.
	*
	* @return il dato del nodo
 	*/
	public String getDatum(){
		return datum;
	}
	
	
	
	/**
	* Metodo che compara due nodi (il chiamante e quello come argomento). Se i due nodi hanno
	* lo stesso dato, allora sono lo stesso nodo e viene restituito true, false altrimenti.
	* 
	* @param v: il nodo con cui comparare il nodo chiamante
	* @return true se i nodi sono uguali, false altrimenti
 	*/
	public boolean equals(Node v){
		if(datum.equals(v.getDatum())){
			return true;
		}
		return false;
	}
	
	
	
	/**
	* Metodo che permette la ricerca nella struttura dati del nodo v. Se è presente restituisce true,
	* false altrimenti.
	* 
	* @param v: il nodo da cercare
	* @return true se il nodo viene trovato, false altrimenti
 	*/
	public boolean findNode(Node v){
		if(son == null){
			return false;
		}
		Node temp = son;
		while(temp != null){
			if(temp.equals(v)){
				return true;
			}
			temp = temp.son;
		}
		return false;
	}
	
	
	
	
	
	/**
	* Metodo che restituisce il numero di nodi figli del nodo chiamante.
	*
	* @return il numero di archi uscenti del nodo
 	*/
	public int getEdges(){
		return edges;
	}
		
		
		
		
	
	/**
	* Metodo che permette l'aggiunta di un nodo. Il metodo verifica se ci sono già nodi o meno.
	* Se non ce ne sono il nodo "v" viene aggiunto, se invece "son" non è null, allora 
	* il nuovo nodo "v" viene aggiunto in prima posizione e "v" punta al nodo che precedentemente
	* era in son.
	*
	* @param v: il nodo da aggiungere alla lista di adiancenza
 	*/
	public void addEdge(Node v){
		if(son == null){
			son = v;
		} else {
			Node temp = son;
			son = v;
			son.son = temp;
		}
		edges++;
	}
	
	
	
	
	
	
	/**
	* Metodo per settare il colore di un nodo. I possibili input sono w = bianco, g = grigio
	* e b = nero. Se vengono inseriti altri caratteri il colore non viene modificato.
	*
	* @param s: il carattere che indica di che colore colorare il nodo (w = white, g = gray, b = black)
 	*/
	public void setColor(char s){
		if(s != 'w' && s != 'g' && s != 'b'){
			return;
		}
		WHITE = (s == 'w' ? true : false);
		GRAY = (s == 'g' ? true : false);
		BLACK = (s == 'b' ? true : false);
	}
	
	
	
	
	
	
	/**
	* Restituisce true se il nodo è bianco
	*
	* @return true se il nodo è bianco, false altrimenti
 	*/
	public boolean isWhite(){
		return WHITE;
	}
	
	
	
	
	
	
	
	/**
	* Restituisce true se il nodo è grigio
	*
	* @return true se il nodo è grigio, false altrimenti
 	*/
	public boolean isGray(){
		return GRAY;
	}
	
	
	
	
	
	
	
	/**
	* Restituisce true se il nodo è nero
	*
	* @return true se il nodo è nero, false altrimenti
 	*/
	public boolean isBlack(){
		return BLACK;
	}
	
	
	
	
	
	
	
	
	
	/**
	* Prende tutti i figli del nodo chiamante e li restituisce in un array di nodi.
	*
	* @return la lista di adiacenza in formato array di nodi
 	*/
	public Node getAdj(){
		return son;
	}
	
	
	
	
	
	
	
	/**
	* Setta la variabile discovery time al valore "Dtime" passato come argomento
	*
	* @param Dtime: il tempo di scoperta
 	*/
	public void setDiscovery(int Dtime){
		if(this.Dtime == 0){
			this.Dtime = Dtime;
		}
	}
	
	
	
	
	
	
	
	/**
	* Setta la variabile finish time al valore "Ftime" passato come argomento
	*
	* @param Ftime: il tempo di fine
 	*/
	public void setFinish(int Ftime){
		if(this.Ftime == 0){
			this.Ftime = Ftime;
		}
	}
	
	
	
	
	
	
	
	/**
	* Restituisce il tempo di fine visita
	*
	* @return il tempo di fine visita
 	*/
	public int getFinish(){
		return Ftime;
	}
	
	
	
	
	
	
	
	/**
	* Setta a 0 Dtime e Ftime
	*/
	public void resetTime(){
		this.Dtime = 0;
		this.Ftime = 0;
	}
	
	



	/**
	* Setta la distanza del nodo dalla radice.
	*
	* @param n: la distanza dalla radice
	*/
	public void setDistance(int n){
		this.distance = n;
	}
	
	
	
	
	
	
	/**
	* Restituisce la distanza del nodo dalla radice.
	* 
	* @return la distanza dalla radice
	*/
	public int getDistance(){
		return distance;
	}





	
	/**
	* Setta a true il flag "newEdge" del nodo, segnalando la presenza di un arco aggiunto.
	*/
	public void setNewEdge(){
		this.newEdge = true;
	}
	
	
	
	
	
	/**
	* Setta la variabile parent che rappresenta l'indice, nel grafo, del genitore
	*
	* @param p: indice della lista adj del nodo padre
	*/
	public void setParent(int p){
		this.parent = p;
	}
	
	
	
	
	/**
	* Restituisce l'indice del genitore. Se vale -1 il nodo non ha un genitore
	*
	* @return l'indice nella lista adj del nodo padre. Vale -1 se non ci sono genitori
	*/
	public int getParent(){
		return parent;
	}
	
	
	
	
	/**
	* Metodo toString che permette di ottenere una visualizzazione di un nodo e dei nodi a cui punta.
	* Il tutto viene restituito in sintassi dot.
	*
	* @return la rappresentazione in .dot del nodo e della sua lista di adiacenza
 	*/
	public String toString(){
		String s = "";
		//Se son è null, vuol dire che il nodo non punta a nessuno e quindi non ha archi
		if(son == null){
			return "";
		}
		
		Node temp = son;
		String line, color, style;
		
		while(temp != null){
			color = " ["+(temp.newEdge ? "color=red, " : "");
			
			style = (datum.equals(temp.belongsTree()) ? "style=dashed" : "")+"];\n";
			
			line = color+style;
			
			s += datum+"->"+temp.datum+line;
			temp = temp.son;
		}
		return s;
	}
	
	
	
	
	/**
	* Cerca il nodo v del nodo chiamante e imposta il suo 'parentInTree' uguale a p
	*
	* @param v: il nodo da cercare tra i figli del nodo chiamante
	* @param p: il dato contenuto nel nodo chiamante
 	*/
	public void addToTree(String p, Node v){
		if(son == null){
			return;
		}
		Node temp = son;
		while(temp != null){
			if(temp.equals(v)){
				temp.parentInTree = p;
			}
			temp = temp.son;
		}
	}
	
	
	
	
	/**
	* Restituisce il valore di 'parentInTree'
	*
	* @return il valore di 'parentInTree'
 	*/
	public String belongsTree(){
		return parentInTree;
	}
	
	
	
	
	
	/**
	* Setta a true il valore boolean StronglyConnectedComponent (scc)
 	*/
	public void setStronglyConnected(){
		scc = true;
	}
	
	
	
	
	/**
	* Restituisce il valore booleano della variabile StronglyConnectedComponent (scc)
	*
	* @return il valore booleano di scc
 	*/
	public boolean isStronglyConnected(){
		return scc;
	}
	
	
	
}