error id: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/UserID.java
file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/UserID.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[17,1]

error in qdox parser
file content:
```java
offset: 457
uri: file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/UserID.java
text:
```scala
package models;

import 


// 
/*
 * Class per la gestione degli utenti nel sistema di raccomandazione libri
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */
p@@ublic class UserID {

    // Attributi della classe
    private String nomeCognome;
    private String codiceFiscale;
    private String email;
    private String userID;
    private String password;  // La password sarà memorizzata in forma hash
    private String Questione;
    private String Risposta;
    
    /**
     * Costruttore della classe UserID
     * @param nome Nome e cognome dell'utente
     * @param codice Codice fiscale dell'utente
     * @param email Email dell'utente
     * @param user Username dell'utente
     * @param pass Password dell'utente (che verrà automaticamente hashatta) 
     * @author Mouhammad Toure
     */
    public UserID(String nome, String codice, String email, String user, String pass) {
        nomeCognome = nome;
        this.codiceFiscale = codice;
        this.email = email;
        this.userID = user;
        this.password = hashedPassword(pass);  // Hash della password alla creazione
        this.Questione = "";
        this.Risposta = hashedPassword(""); // Inizialmente vuota, hash di stringa vuota
    }
    
    // Metodi getter e setter
    
    public String getNomeCognome() {
        return nomeCognome;
    }
    
    public void setNomeCognome(String nome) {
        nomeCognome = nome;
    }
    
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    
    public void setCodiceFiscale(String codice) {
        codiceFiscale = codice;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String user) {
        userID = user;
    }
    
    public String getPassword() {
        return password;
    }
    
    /**
     * Imposta una nuova password (viene automaticamente hashatta)
     * @param pass La nuova password in chiaro
     */
    public void setPassword(String pass) {
        password = hashedPassword(pass);
    }

    /**
     * Restituisce la domanda di sicurezza dell'utente
     * @return the security question
     */
    public String getQuestione() {
        return Questione;
    }

    /**
     * Imposta la domanda di sicurezza dell'utente
     * @param questione the security question to set
     */
    public void setQuestione(String questione) {
        this.Questione = questione;
    }

    /**
     * Restituisce la risposta di sicurezza dell'utente
     * @return the security answer
     */
    public String getRisposta() {
        return Risposta;
    }

    /**
     * Imposta la risposta di sicurezza dell'utente
     * @param risposta the security answer to set
     */
    public void setRisposta(String risposta) {
        this.Risposta = hashedPassword(risposta); // Hash della risposta di sicurezza
    }

    /**
     * Restituisce una stringa con tutti i dati dell'utente, inclusa la password hash
     * @return Stringa concatenata con i dati separati da virgola
     */
    public String toString() {
        return String.join(",", getNomeCognome(), getCodiceFiscale(), getEmail(), getUserID(), getQuestione());
    }

    /**
     * Metodo per generare l'hash di una password
     * @param password La password in chiaro
     * @return L'hash della password
     */
    private String hashedPassword(String password) {
        return String.valueOf(password.hashCode()); 
    }

    /**
     * Metodo per verificare se la password fornita è corretta
     * @param password La password da verificare (in chiaro)
     * @return true se la password è corretta, false altrimenti
     */
    public boolean checkPassword(String password) {
        return this.password.equals(hashedPassword(password));
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

QDox parse error in file:///C:/Users/Mouhammad%20Toure/Videos/BookRecommender/BookRecommender/src/models/UserID.java