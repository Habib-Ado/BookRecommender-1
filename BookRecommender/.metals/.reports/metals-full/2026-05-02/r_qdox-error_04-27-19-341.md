error id: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Valutazione.java
file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Valutazione.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[15,1]

error in qdox parser
file content:
```java
offset: 375
uri: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Valutazione.java
text:
```scala
package models;

import

/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */

p@@ublic class Valutazione {

    private String userID;
    private String titoloLibro;
    private int stile;
    private int contenuto;
    private int gradevolezza;
    private int originalita;
    private int edizione;
    private double votoFinale;
    
    public Valutazione(String user, String titolo, int stil, int cont, int grad, int orig, int ediz){

        this.userID = user;
        this.titoloLibro = titolo;
        this.stile = stil;
        this.contenuto = cont;
        this.gradevolezza = grad;
        this.originalita = orig;
        this.edizione = ediz;
        this.votoFinale = calcoloVotoFinale();

    }
    
    private double calcoloVotoFinale(){
        return (stile + contenuto + gradevolezza + originalita + edizione)/5;
    }
    
    public String getUserID(){
        return userID;
    }

    public void setUserID(String user){
        userID = user;
    }

    public String getTitoloLibro(){
        return titoloLibro;
    }

    public void setTitoloLibro(String titolo){
        titoloLibro = titolo;
    }

    public int getStile(){
        return stile;
    }

    public void setStile(int stil){
        stile = stil;
    }

    public int getContenuto(){
        return contenuto;
    }

    public void setContenuto(int cont){
        contenuto = cont;
    }

    public int getGradevolezza(){
        return gradevolezza;
    }

    public void setGradevolezza(int grad){
        gradevolezza = grad;
    }

    public int getOriginalita(){
        return originalita;
    }

    public void setOriginalita(int orig){
        originalita = orig;
    }

    public int getEdizione(){
        return edizione;
    }

    public void setEdizione(int ediz){
        edizione = ediz;
    }

    public double getVotoFinale(){
        return votoFinale;
    }

    public void setVotoFinale(double votoFin){
        votoFinale = votoFin;
    }

    public String toString(){
        return getUserID()+","+ getTitoloLibro()+","+ getStile()+","+ getContenuto()+","+
        getGradevolezza()+","+ getOriginalita()+","+ getEdizione()+","+ getVotoFinale();
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

QDox parse error in file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Valutazione.java