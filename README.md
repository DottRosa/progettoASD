# Risoluzione al problema dell’aggiunta del minor numero di archi e identificazione dell’albero dei cammini minimi di un grafo orientato

### Autore: MARCO ROSA
### Per uno studio più approfondito di tutti gli aspetti del programma invito a scaricare la relazione (relazione.pdf)


## Enunciato del problema

Dato in input un grafo orientato G = (V, E):

1. identificare una radice r. Il grafo G ammette radice se e solo se, a partire da essa, è possibile raggiungere tutti gli altri nodi appartenenti a G. Per farlo è necessario

2. determinare il numero minimo di archi da aggiungere a G ottenendo un grafo G’ = (V, E’) che ammetta una radice r. I nuovi archi |E-E’| devono essere colorati di rosso. Ottenuto quindi il nuovo grafo bisogna

3. tratteggiare gli opportuni archi di G’ per evidenziare l’albero T = (V, Et) di radice r che permette di raggiungere tutti gli altri nodi percorrendo il cammino minimo. Tutti gli altri archi restano invariati.

Il tutto deve essere eseguito partendo da un input in formato .dot e restituendo il grafo in output (rappresentazione sia di G’ che dell’albero T) anch’esso in formato .dot.

## Come eseguire il programma

Il programma in questione è la versione priva di package, quindi:

per compilare il programma risolutivo è necessario eseguire l’istruzione:

> javac Project.java

Allo stesso modo per il programma per il calcolo dei tempi, eseguire l’istruzione:

> javac TimingAnalysis.java

Così facendo tutte le classi utilizzate dai due programmi saranno automaticamente compilate.
Per eseguire il programma risolutivo e il calcolo dei tempi bisogna eseguire:

> java Project \<input.dot\> output.dot

> java TimingAnalysis
