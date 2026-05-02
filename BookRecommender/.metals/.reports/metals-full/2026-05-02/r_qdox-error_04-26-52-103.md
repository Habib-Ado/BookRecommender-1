error id: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Libro.java
file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Libro.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[3,1]

error in qdox parser
file content:
```java
offset: 20
uri: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Libro.java
text:
```scala
package models;

i@@mpo

/*
 * Classe per la gestione dei libri nel sistema di raccomandazione libri. 
 * Contiene informazioni come titolo, autore, categoria, editore e anno di pubblicazione.
 * Include anche metodi per rimuovere caratteri speciali dalle stringhe, utili per la pulizia dei dati.
 * 
 */

public class Libro {
    private String titolo;
    private String autore;
    private String categoria;
    private String editore;
    private String anno;
    
    public Libro(String titolo, String autore, String categoria, String editore, String anno){
        
        this.titolo = titolo;
        this.autore = autore;
        this.categoria = categoria;
        this.editore = editore;
        this.anno = anno;

    }
    
    public String getTitolo(){
        return titolo;
    }
    public void setTitolo(String titolo){
        this.titolo = titolo;
    }
    public String getAutore(){
        return autore;
    }
    public void setAutore(String autore){
        this.autore = autore;
    }
    public String getAnno(){
        return anno;
    }
    public void setAnno(String anno){
        this.anno = anno;
    }
    public String getEditore(){
        return editore;
    }
    public void setEditore(String editore){
        this.editore = editore;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    
    public String toString(){
        return String.join(",", getTitolo(), getAutore(), getCategoria(), getEditore(), getAnno());
    }


    public static String rimuoviCaratteriSpeciali(String input) {
        if (input == null) {
            return null; // Gestione del caso in cui l'input è null
        }
    
        // Regex per mantenere solo lettere, numeri e spazi
        String regex = "[^a-zA-Z0-9\\s]";
    
        // Sostituisci tutti i caratteri speciali con una stringa vuota
        return input.replaceAll(regex, "");
    }

    public static String rimuoviSpeciali(String input) {    
        // Regex per mantenere solo lettere, numeri e spazi
        String regex = "[^a-zA-Z0-9\\s]";
        // Sostituisci tutti i caratteri speciali con una stringa vuota
        return input.replaceAll(regex, "");
    }

    public static String nuovoAutore(String author){
        String authore = "";
        for(int i = 0; i < author.length(); i++){
            if(i > 3){
                authore += author.charAt(i);
            }
        }

        return authore;
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

QDox parse error in file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/Libro.java