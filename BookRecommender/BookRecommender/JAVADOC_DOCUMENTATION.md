# BookRecommender - Documentazione JavaDoc

## Panoramica del Progetto

Il **BookRecommender** è un sistema di raccomandazione di libri sviluppato in Java che utilizza un'architettura client-server con RMI (Remote Method Invocation) e un database PostgreSQL per la gestione dei dati.

## Struttura del Progetto

```
BookRecommender/
├── src/bookrecommender/          # Codice sorgente Java
│   ├── BookRecommender.java      # Classe principale del sistema
│   ├── BookRecommenderGUI.java   # Interfaccia grafica
│   ├── Client.java              # Client RMI
│   ├── Server.java              # Server RMI
│   ├── InterfaceBook.java       # Interfaccia RMI
│   ├── InterfaceImpl.java       # Implementazione dell'interfaccia
│   ├── Libro.java              # Modello dati per i libri
│   ├── UserID.java             # Modello dati per gli utenti
│   ├── Libreria.java           # Gestione delle librerie personali
│   ├── Valutazione.java        # Gestione delle valutazioni
│   ├── Consiglio.java          # Gestione dei consigli
│   ├── Trasferimento.java      # Gestione del trasferimento dati
│   └── DatabaseInitializer.java # Inizializzazione del database
├── bin/                        # File compilati (.class)
├── lib/                        # Librerie esterne
├── dati/                       # Dataset dei libri
└── javadoc/                    # Documentazione JavaDoc generata
```

## Documentazione delle Classi

### 1. BookRecommender.java

**Classe principale del sistema di raccomandazione di libri.**

#### Descrizione
La classe `BookRecommender` gestisce l'interazione con l'utente e le operazioni sul database PostgreSQL. Fornisce funzionalità per la gestione degli utenti, la ricerca di libri, la gestione delle librerie personali e il sistema di raccomandazioni.

#### Attributi Principali
- `DB_URL`: URL di connessione al database PostgreSQL
- `DB_USER`: Username per la connessione al database
- `DB_PASSWORD`: Password per la connessione al database
- `userSessions`: Mappa delle sessioni utente attive
- `connection`: Connessione al database

#### Metodi Principali

##### `initializeDatabasePassword()`
```java
public static synchronized boolean initializeDatabasePassword()
```
**Descrizione**: Inizializza la password del database richiedendola all'utente.
**Ritorna**: `true` se la password è stata impostata correttamente, `false` altrimenti

##### `visualizzareLibri()`
```java
public synchronized String visualizzareLibri()
```
**Descrizione**: Restituisce la lista di tutti i libri disponibili nel sistema.
**Ritorna**: Stringa formattata con la lista dei libri

##### `cercaLibroConTitolo(String title)`
```java
public synchronized String cercaLibroConTitolo(String title)
```
**Descrizione**: Cerca libri per titolo.
**Parametri**: 
- `title` - Titolo del libro da cercare
**Ritorna**: Stringa con i risultati della ricerca

##### `registrazione(String sessionId, String name, String cf, String email, String userid, String password)`
```java
public synchronized String registrazione(String sessionId, String name, String cf, String email, String userid, String password)
```
**Descrizione**: Registra un nuovo utente nel sistema.
**Parametri**:
- `sessionId` - ID della sessione
- `name` - Nome e cognome dell'utente
- `cf` - Codice fiscale
- `email` - Email dell'utente
- `userid` - Username
- `password` - Password dell'utente
**Ritorna**: Messaggio di conferma o errore

##### `login(String sessionId, String username, String password)`
```java
public synchronized String login(String sessionId, String username, String password)
```
**Descrizione**: Effettua il login di un utente.
**Parametri**:
- `sessionId` - ID della sessione
- `username` - Username dell'utente
- `password` - Password dell'utente
**Ritorna**: Messaggio di conferma o errore

### 2. Libro.java

**Classe modello per rappresentare un libro nel sistema.**

#### Descrizione
La classe `Libro` rappresenta un libro con i suoi attributi principali: titolo, autore, categoria, editore e anno di pubblicazione.

#### Attributi
- `titolo`: Titolo del libro
- `autore`: Autore del libro
- `categoria`: Categoria/genere del libro
- `editore`: Casa editrice
- `anno`: Anno di pubblicazione

#### Metodi Principali

##### Costruttore
```java
public Libro(String titolo, String autore, String categoria, String editore, String anno)
```
**Descrizione**: Crea una nuova istanza di Libro.
**Parametri**:
- `titolo` - Titolo del libro
- `autore` - Autore del libro
- `categoria` - Categoria del libro
- `editore` - Editore del libro
- `anno` - Anno di pubblicazione

##### `toString()`
```java
public String toString()
```
**Descrizione**: Restituisce una rappresentazione stringa del libro.
**Ritorna**: Stringa con i dati del libro separati da virgole

##### `rimuoviCaratteriSpeciali(String input)`
```java
public static String rimuoviCaratteriSpeciali(String input)
```
**Descrizione**: Rimuove i caratteri speciali da una stringa, mantenendo solo lettere, numeri e spazi.
**Parametri**:
- `input` - Stringa da processare
**Ritorna**: Stringa pulita senza caratteri speciali

### 3. UserID.java

**Classe per la gestione degli utenti nel sistema.**

#### Descrizione
La classe `UserID` gestisce le informazioni degli utenti registrati nel sistema, inclusa la gestione sicura delle password tramite hashing.

#### Attributi
- `nomeCognome`: Nome e cognome dell'utente
- `codiceFiscale`: Codice fiscale dell'utente
- `email`: Email dell'utente
- `userID`: Username dell'utente
- `password`: Password hashata dell'utente

#### Metodi Principali

##### Costruttore
```java
public UserID(String nome, String codice, String email, String user, String pass)
```
**Descrizione**: Crea una nuova istanza di UserID.
**Parametri**:
- `nome` - Nome e cognome dell'utente
- `codice` - Codice fiscale dell'utente
- `email` - Email dell'utente
- `user` - Username dell'utente
- `pass` - Password dell'utente (viene automaticamente hashata)

##### `checkPassword(String password)`
```java
public boolean checkPassword(String password)
```
**Descrizione**: Verifica se la password fornita è corretta.
**Parametri**:
- `password` - Password da verificare (in chiaro)
**Ritorna**: `true` se la password è corretta, `false` altrimenti

##### `hashedPassword(String password)`
```java
private String hashedPassword(String password)
```
**Descrizione**: Genera l'hash di una password.
**Parametri**:
- `password` - Password in chiaro
**Ritorna**: Hash della password

### 4. InterfaceBook.java

**Interfaccia RMI per il sistema di raccomandazione libri.**

#### Descrizione
L'interfaccia `InterfaceBook` definisce tutti i metodi remoti disponibili per i client RMI. Estende `Remote` per supportare la comunicazione remota.

#### Metodi dell'Interfaccia

##### `visualizzaLibri()`
```java
public String visualizzaLibri() throws RemoteException
```
**Descrizione**: Visualizza tutti i libri disponibili.

##### `cercaLibroConTitolo(String titolo)`
```java
public String cercaLibroConTitolo(String titolo) throws RemoteException
```
**Descrizione**: Cerca libri per titolo.

##### `registrazione(String sessionId, String name, String cf, String email, String userid, String password)`
```java
public String registrazione(String sessionId, String name, String cf, String email, String userid, String password) throws RemoteException
```
**Descrizione**: Registra un nuovo utente.

##### `login(String sessionId, String userid, String password)`
```java
public String login(String sessionId, String userid, String password) throws RemoteException
```
**Descrizione**: Effettua il login di un utente.

### 5. Server.java

**Classe server RMI per il sistema di raccomandazione libri.**

#### Descrizione
La classe `Server` avvia il server RMI e gestisce la connessione al database PostgreSQL.

#### Attributi
- `PORT`: Porta su cui il server è in ascolto (1099)

#### Metodi Principali

##### `main(String[] args)`
```java
public static void main(String[] args)
```
**Descrizione**: Metodo principale che avvia il server RMI.
**Parametri**:
- `args` - Argomenti da riga di comando

### 6. Client.java

**Classe client RMI per il sistema di raccomandazione libri.**

#### Descrizione
La classe `Client` gestisce la connessione al server RMI e fornisce l'interfaccia utente per interagire con il sistema.

### 7. BookRecommenderGUI.java

**Interfaccia grafica per il sistema di raccomandazione libri.**

#### Descrizione
La classe `BookRecommenderGUI` fornisce un'interfaccia grafica Swing per interagire con il sistema di raccomandazione libri.

### 8. Libreria.java

**Classe per la gestione delle librerie personali degli utenti.**

#### Descrizione
La classe `Libreria` gestisce le librerie personali degli utenti, permettendo di aggiungere e rimuovere libri dalle proprie collezioni.

### 9. Valutazione.java

**Classe per la gestione delle valutazioni dei libri.**

#### Descrizione
La classe `Valutazione` gestisce le valutazioni che gli utenti possono dare ai libri, includendo criteri come stile, contenuto, piacevolezza, originalità ed edizione.

### 10. Consiglio.java

**Classe per la gestione dei consigli di lettura.**

#### Descrizione
La classe `Consiglio` gestisce i consigli di lettura, permettendo agli utenti di consigliare libri ad altri utenti.

### 11. Trasferimento.java

**Classe per la gestione del trasferimento dati tra client e server.**

#### Descrizione
La classe `Trasferimento` gestisce il trasferimento di dati tra client e server, includendo la serializzazione e deserializzazione degli oggetti.

### 12. DatabaseInitializer.java

**Classe per l'inizializzazione del database PostgreSQL.**

#### Descrizione
La classe `DatabaseInitializer` gestisce la creazione e l'inizializzazione del database PostgreSQL, incluse le tabelle e i dati di esempio.

## Come Generare la Documentazione JavaDoc

### Metodo 1: Usando lo Script Batch
1. Esegui il file `generate_javadoc.bat`
2. La documentazione sarà generata nella cartella `javadoc/`
3. Apri `javadoc/index.html` nel browser per visualizzare la documentazione

### Metodo 2: Comando Manuale
```bash
javadoc -d javadoc -sourcepath src -subpackages bookrecommender -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -windowtitle "BookRecommender API Documentation" -doctitle "BookRecommender - Sistema di Raccomandazione Libri" -header "<b>BookRecommender</b>" -footer "<i>Documentazione generata automaticamente</i>" -author -version -private
```

## Struttura della Documentazione Generata

La documentazione JavaDoc generata includerà:

1. **Pagina principale** (`index.html`): Panoramica del progetto
2. **Documentazione delle classi**: Dettagli completi per ogni classe
3. **Documentazione dei metodi**: Descrizione di tutti i metodi pubblici
4. **Indice alfabetico**: Accesso rapido alle classi e metodi
5. **Albero delle classi**: Struttura gerarchica delle classi

## Note sulla Documentazione

- Tutti i metodi pubblici sono documentati
- I parametri e i valori di ritorno sono specificati
- Le eccezioni sono documentate dove applicabile
- La documentazione è in italiano per coerenza con il codice
- Include esempi di utilizzo dove appropriato

## Requisiti per la Generazione

- Java Development Kit (JDK) installato
- Variabile d'ambiente JAVA_HOME configurata
- File sorgente Java presenti in `src/bookrecommender/`
- Nessun errore di sintassi nei file Java

## Troubleshooting

### Errore: "javadoc non è riconosciuto"
- Verifica che il JDK sia installato correttamente
- Controlla che la variabile PATH includa il percorso del JDK

### Errore: "Impossibile trovare i file sorgente"
- Verifica che i file Java siano presenti in `src/bookrecommender/`
- Controlla che i file abbiano estensione `.java`

### Errore: "Errore di codifica"
- Verifica che i file Java utilizzino la codifica UTF-8
- Controlla le impostazioni di codifica nel comando javadoc 