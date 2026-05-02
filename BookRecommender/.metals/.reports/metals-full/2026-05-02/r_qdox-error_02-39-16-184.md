error id: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/server/Server.java
file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/server/Server.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[23,1]

error in qdox parser
file content:
```java
offset: 653
uri: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/server/Server.java
text:
```scala
package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */
public class Server {

    
    private static final String ipAdress = "192.168.43.94";
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "BookRecommender";
    
 @@   public static void main(String[] args) {    
        // Force la JVM a utilizzare IPv4 per evitare problemi di compatibilità con IPv6
        System.setProperty("java.rmi.server.hostname", "192.168.43.94");
        try {
            System.out.println("=== BookRecommender Server ===");
            System.out.println("Avvio del server...");
            
            // Crea il registry RMI
            Registry registry = LocateRegistry.createRegistry(PORT);
            InterfaceImpl interfaceImpl = new InterfaceImpl();
            registry.rebind("ipAdress + "/" + SERVICE_NAME, interfaceImpl);
            
            System.out.println("✅ Server avviato con successo!");
            System.out.println("📍 Server in ascolto alla porta " + PORT);
            System.out.println("🌐 Database connesso correttamente");
            System.out.println("🚀 Pronto per accettare connessioni client...");
            System.out.println("\nPer fermare il server, premi Ctrl+C");
            
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'avvio del server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
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

QDox parse error in file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/server/Server.java