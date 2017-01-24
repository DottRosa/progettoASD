

/**
* Classe che implementa la struttura dati di una coda.
*
* @author  Rosa Marco
* @since   2016-09-24
*/

public class Queue{
	
	private int index;	//L'indice del nodo nella lista adj di un grafo

	private Queue next;	//Puntatore alla coda successiva
	
	
	
	
	/**
	* Costruttore di Coda che, dato l'indice di un nodo lo inserisce nella coda e setta il successore
	* a null.
	*
	* @param index: indice del nodo nella lista adj
	*/
	public Queue(int index){
		this.index = index;
		this.next = null;
	}
	
	
	
	
	/**
	* Restituisce la coda successiva alla coda chiamante.
	*
	* @return la coda successiva;
	*/
	public Queue getNext(){
		return next;
	}
	
	
	
	
	/**
	* Setta la coda successiva data una coda "c".
	*
	* @param c: la coda da aggiungere come coda successiva alla coda chiamante
	*/
	public void setNext(Queue c){
		this.next = c;
	}
	
	
	
	
	
	/**
	* Restituisce il dato presente nella coda chiamante.
	*
	* @return il dato della coda
	*/
	public int getIndex(){
		return index;
	}
	
	
	
	
	/**
	 * Restituisce il nodo presente nella testa della coda. Se la coda è vuota restituisce null.
	 *
	 * @return il dato presente in testa alla coda
	 */
	public int headQueue(){
		return index;
	}
	
	
	
	/**
	 * Permette di inserire nella coda un nodo.
	 *
	 * @param son: il nodo da aggiungere alla coda
	 */
	public void enqueue(int son){
		Queue temp = this;
		
		while(temp.next != null){
			if(temp.index == son){
				return;
			}
			temp = temp.next;
		}
	
		temp.next = new Queue(son);
	}
	
	
}