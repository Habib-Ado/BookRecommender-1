error id: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Consiglio.java
file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Consiglio.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[3,1]

error in qdox parser
file content:
```java
offset: 20
uri: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Consiglio.java
text:
```scala
package models;

i@@mpo
import java.util.ArrayList;
import java.util.List;


/**
 * Classe consiglio che permette agli utenti di consigliare libri.
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA 
 */
public class Consiglio {

    private String userID; // ID dell'utente che ha fatto il consiglio
    private String titoloLibro; // Titolo del libro referenziale
    private List<String> consigliati; // Lista dei libri consigliati

    // Costruttore
    public Consiglio(String userID, String titoloLibro) {

        this.userID = userID;
        this.titoloLibro = titoloLibro;
        this.consigliati = new ArrayList<>();
        
    }

    // Getter per userID
    public String getUserID() {
        return userID;
    }

    // Setter per userID
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter per titoloLibro
    public String getTitoloLibro() {
        return titoloLibro;
    }

    // Setter per titoloLibro
    public void setTitoloLibro(String titoloLibro) {
        this.titoloLibro = titoloLibro;
    }

    // Getter per la lista dei libri consigliati
    public List<String> getConsigliati() {
        return consigliati;
    }

    public String getConsigliAString(){
        return String.join(",", consigliati);
    }
    // Aggiunge un libro alla lista dei consigliati (evitando duplicati)
    public void aggiungiLibro(String titoloLibro) {
        if (titoloLibro != null && !titoloLibro.isEmpty() && consigliati.size() < 3) {
            consigliati.add(titoloLibro);
        }else {
            System.out.println("Non è possibile aggiungere più di 3 libri consigliati o il titolo è vuoto.");
        }
    }

    // Add the missing method
    public void inserisciSuggerimentoLibro(String titoloConsigliato) {
        if (consigliati.size() < 3) {
            consigliati.add(titoloConsigliato);
        } else {
            System.out.println("Non è possibile aggiungere più di 3 libri consigliati.");
        }
    }
 
    // Restituisce una rappresentazione testuale dell'oggetto Consiglio
    public String toString() {
        return getUserID()+ "," + getTitoloLibro() + "," + String.join(",", getConsigliAString());
    }


}
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:560)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:691)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:688)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:630)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:628)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1313)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:688)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:940)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:691)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:500)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

QDox parse error in file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Consiglio.java