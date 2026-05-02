# Università degli studi Insubria 

# "BOOK RECOMMENDER" - 

# USER MANUAL 

**Autori:**  
- Mouhammad Toure 
- Daniel Viny Kamdem Tagne 
- Agnes Balkaire Makouwe 

**Progetto Laboratorio A: BookRecommender**  

**Anno accademico: 2023/2024**

---

## Sommario 
1. [Introduzione](#1-introduzione)
2. [Installazione](#2-installazione)
3. [Configurazione del Database](#3-configurazione-del-database)
4. [Utilizzo dell'Applicazione](#4-utilizzo-dellapplicazione)
5. [Troubleshooting](#5-troubleshooting)
6. [Sitografia](#6-sitografia)
7. [Bibliografia](#7-bibliografia)

---

## 1. Introduzione

Il programma Book Recommender è progettato per aiutare gli utenti a trovare libri consigliati in base alle loro preferenze, che tu sia un utente registrato o no. Il programma offre funzionalità diverse per migliorare la tua esperienza di lettura.

Attraverso un sistema di ricerca intuitivo e accessibile a tutti, è possibile scoprire facilmente nuovi libri che corrispondono ai tuoi gusti letterari. Book Recommender rende la scoperta di nuovi libri e la consultazione delle recensioni un processo semplice e piacevole, rendendo la lettura un'attività più coinvolgente e informata.

### 1.1 Funzionamento generale dell'applicazione

L'applicazione Book Recommender utilizza un'architettura client-server basata su Java RMI (Remote Method Invocation) che permette a più utenti di utilizzare il sistema contemporaneamente. Ogni client ha una sessione indipendente, garantendo che le operazioni di un utente non interferiscano con quelle degli altri.

**Caratteristiche principali:**
- **Sistema multi-utente**: Ogni client ha una sessione univoca identificata da un Session ID generata automaticamente
- **Isolamento delle sessioni**: Gli utenti non possono accedere ai dati di altri utenti per sicurezza
- **Database PostgreSQL**: Tutti i dati sono memorizzati in un database relazionale sicuro
- **Setup automatico**: Configurazione semplificata tramite script automatici
- **Concorrenza**: Più utenti possono utilizzare il sistema simultaneamente

### 1.2 Strutture dati utilizzate

Il sistema utilizza un database PostgreSQL per la gestione dei dati, che include le seguenti tabelle:

- **userid**: Credenziali degli utenti registrati
- **libri**: Catalogo dei libri disponibili (oltre 66.000 libri)
- **valutazioni**: Valutazioni degli utenti sui libri
- **consigli**: Raccomandazioni di libri tra utenti
- **librerie**: Librerie personali degli utenti

**NOTA**: Per garantire il corretto funzionamento di tutte le funzionalità dell'applicazione, è fondamentale non modificare manualmente i dati del database. Utilizzare esclusivamente l'applicazione per interagire con i dati.

---

## 2. Installazione

BookRecommender include un sistema di installazione completamente automatizzato che configura tutto ciò che serve per far funzionare l'applicazione. Il processo di setup è stato semplificato con script automatici che gestiscono compilazione, database e avvio del sistema.

### 2.1 Requisiti di Sistema

**Sistemi operativi supportati:**
- Windows 10/11 (raccomandato)
- macOS Big Sur (versione 11.7.6 e successive)
- Linux (Ubuntu 20.04+)

### 2.2 Prerequisiti Necessari

Prima di iniziare l'installazione, assicurati di avere installato:

#### Java 17 o superiore
L'applicazione è stata sviluppata con Java 17. È necessario avere Java Development Kit (JDK) installato.

**Per verificare la versione di Java:**
```bash
java -version
javac -version
```

**Scarica Java dal sito ufficiale:**
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK](https://adoptium.net/) (gratuito, raccomandato)

#### PostgreSQL 12+
Il sistema utilizza PostgreSQL come database.

**Installazione PostgreSQL:**
1. Scaricare da [postgresql.org](https://www.postgresql.org/download/)
2. Durante l'installazione, impostare una password per l'utente 'postgres'
3. Avviare il servizio PostgreSQL

**IMPORTANTE**: Il database 'bookrecommender' verrà creato automaticamente dal sistema.

### 2.3 Setup Automatico Completo

Il progetto include `run_all.bat` che esegue automaticamente:
- Pulizia dei file precedenti
- Compilazione del progetto  
- Creazione automatica del database
- Popolamento con i libri (se il database è vuoto / per il primo avvio del sistema)
- Avvio del server
- Avvio del client

#### Metodo Raccomandato - Esecuzione Automatica
```bash
# Da PowerShell
.\run_all.bat

# Da CMD (Prompt dei Comandi)
run_all.bat

# Oppure doppio click sul file run_all.bat
Fare un doppio click sul file run_all.bat per configurare ed eseguire il sistema automaticamente
```

#### Metodo Alternativo - Setup Manuale Passo-Passo
Se preferisci controllare ogni passaggio:

0. **Pulizia:**
   ```bash
   .\clean.bat
   ```

1. **Compilazione:**
   ```bash
   .\compile.bat
   ```

2. **Setup Database:**
   ```bash
   .\setup_database.bat
   ```

3. **Popolamento Database:**
   ```bash
   .\populate_db.bat
   ```

4. **Avvio Sistema:**
   ```bash
   .\start_server.bat    # In una finestra
   .\start_client.bat    # In un'altra finestra con possibilità di avviare piu utenti 
   ```

### 2.4 File Inclusi

Il progetto include tutti i file necessari:
  **Driver JDBC PostgreSQL** (`lib/postgresql-42.7.5.jar`) 
- già incluso e permette di conettersi al database
  **Dati dei libri** (`dati/BooksDatasetClean.csv`) 
- già incluso, file da cui caricare i libri nel database
  **Script di automazione** 
- già configurati con il file run_all.bat
  **Configurazione manuale** 
- si puo anche configurare manualmente il programma:
    1. Pulire con clean.bat
    2. Compilare con compile.bat
    3. Inizializzare il database con setup_database.bat
    4. Popolare il database con i libri, populate_db.bat
    5. Avviare il server con start_server.bat
    6. Avviare il cliente con start_client.bat

**NON È NECESSARIO** scaricare manualmente driver o dati aggiuntivi se necessario.

---

## 3. Configurazione del Database

Il progetto include una classe `DatabaseInitializer` che automatizza completamente la configurazione del database.

### 3.1 Configurazione Automatica (Inclusa in run_all.bat)

Il sistema include una configurazione del database completamente automatizzata. Quando esegui `run_all.bat`, il sistema si occupa automaticamente di:

1. **Creare il database** 'bookrecommender' se non esiste
2. **Creare tutte le tabelle** necessarie (userid, libri, valutazioni, consigli, librerie)
3. **Popolare il database** con oltre 66.000 libri (solo se il database è vuoto / il primo avvio)
4. **Verificare la connessione** testa la connessione al database e l'integrità dei dati

### 3.2 Setup Manuale del Database (Opzionale)

Se preferisci configurare il database separatamente:

#### Metodo 1: Script Specifico per Database
```bash
.\setup_database.bat
```

#### Metodo 2: Solo Popolamento Libri
```bash
.\populate_db.bat
```

#### Metodo 3: Comando Manuale
```bash
java -cp "bin;lib/*" bookrecommender.DatabaseInitializer
```

### 3.3 Opzioni di Configurazione Database

Lo script `setup_database.bat` offre le seguenti opzioni:

1. **Initialize database only** - Crea database e tabelle
2. **Test database connection** - Verifica connessione PostgreSQL
3. **Show database information** - Mostra statistiche database
4. **Exit** - Esce dal setup

### 3.4 Gestione Intelligente del Popolamento

Lo script `populate_db.bat` permette di inserire libri nel database.
Il sistema include una logica intelligente per il popolamento:

- **Database vuoto**: Popola automaticamente con tutti i libri
- **Database esistente**: Salta il popolamento per evitare duplicazioni
- **Controllo automatico**: Verifica il numero di libri presenti

**Messaggio tipico:**
```
🔍 Numero di libri nel database: 66313
📚 Il database contiene già dei libri.
ℹ️ Popolamento saltato per evitare duplicazioni e perdita tempo.
```

### 3.5 Struttura del Database

Il database PostgreSQL include le seguenti tabelle:

**Tabella userid:**
```sql
CREATE TABLE userid (
    nome_cognome VARCHAR(100) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    userid VARCHAR(50) PRIMARY KEY,
    password VARCHAR(256) NOT NULL
);
```

**Tabella libri:**
```sql
CREATE TABLE libri (
    titolo VARCHAR(500) PRIMARY KEY,
    autore VARCHAR(100),
    genere VARCHAR(50),
    editore VARCHAR(100),
    anno INT NOT NULL
);
```

**Tabella valutazioni:**
```sql
CREATE TABLE valutazioni (
    userid VARCHAR(50) NOT NULL,
    titolo_libro VARCHAR(500) NOT NULL,
    stile INT NOT NULL CHECK (stile >= 1 AND stile <= 5),
    contenuto INT NOT NULL CHECK (contenuto >= 1 AND contenuto <= 5),
    gradevolezza INT NOT NULL CHECK (gradevolezza >= 1 AND gradevolezza <= 5),
    originalita INT NOT NULL CHECK (originalita >= 1 AND originalita <= 5),
    edizione INT NOT NULL CHECK (edizione >= 1 AND edizione <= 5),
    voto_finale FLOAT NOT NULL,
    PRIMARY KEY (userid, titolo_libro),
    FOREIGN KEY (userid) REFERENCES userid(userid) ON DELETE CASCADE,
    FOREIGN KEY (titolo_libro) REFERENCES libri(titolo) ON DELETE CASCADE
);
```

**Tabella consigli:**
```sql
CREATE TABLE consigli (
    userid VARCHAR(50) NOT NULL,
    libro_referenziale VARCHAR(500) NOT NULL,
    libro_consigliato VARCHAR(500) NOT NULL,
    FOREIGN KEY (userid) REFERENCES userid(userid) ON DELETE CASCADE,
    FOREIGN KEY (libro_referenziale) REFERENCES libri(titolo) ON DELETE CASCADE,
    FOREIGN KEY (libro_consigliato) REFERENCES libri(titolo) ON DELETE CASCADE
);
```

**Tabella librerie:**
```sql
CREATE TABLE librerie (
    id SERIAL PRIMARY KEY,
    userid VARCHAR(50) NOT NULL,
    nome_libreria VARCHAR(100) NOT NULL,
    libro VARCHAR(500),
    UNIQUE(userid, nome_libreria, libro),
    FOREIGN KEY (userid) REFERENCES userid(userid) ON DELETE CASCADE,
    FOREIGN KEY (libro) REFERENCES libri(titolo) ON DELETE CASCADE
);
```

---

### 3.6 Risoluzione Problemi Comuni

#### Problema: "java non riconosciuto"
**Soluzione:**
```bash
.\fix_java_version.bat
```
Questo script verifica e corregge i problemi di versione Java.

#### Problema: "PostgreSQL connection failed"
**Verifiche:**
1. PostgreSQL è installato e in esecuzione?
2. La password dell'utente 'postgres' è corretta?
3. Il servizio PostgreSQL è avviato?

#### Problema: "run_all.bat non si avvia"
**Da PowerShell:**
```powershell
.\run_all.bat
```

**Da CMD:**
```cmd
run_all.bat
```

#### Problema: Caratteri strani nell'output
Questo è normale in alcuni terminali Windows - il funzionamento non è compromesso.

---

## 4. Utilizzo dell'Applicazione

### 4.1 Registrazione

La funzione di registrazione permette ai nuovi utenti di creare un account inserendo le proprie credenziali. Per completare la registrazione, è necessario fornire informazioni come il nome e cognome, codice fiscale, email, UserID e password.

### 4.2 Login

La funzione di login è essenziale per permettere agli utenti di accedere a funzionalità importanti, come creazione e la gestione delle librerie personali e la visualizzazione di suggerimenti personalizzati. Durante il login, agli utenti sarà richiesto di inserire il proprio UserID e Password che sono stati memorizzati durante la fase di registrazione.

### 4.2.1 Recupero Password

Il sistema include una funzionalità di recupero password per gli utenti che hanno dimenticato le proprie credenziali:

**Caratteristiche del Recupero Password:**
- **Ricerca Utente**: Inserimento dell'username per trovare l'utente
- **Visualizzazione Informazioni**: Mostra nome, email e username dell'utente
- **Generazione Password Temporanea**: Crea una nuova password sicura di 12 caratteri
- **Sicurezza**: Password temporanea con mix di lettere maiuscole, minuscole, numeri e caratteri speciali

**Come Utilizzare:**
1. Dal menu principale, seleziona l'opzione "Recupera password"
2. Inserisci l'username dell'utente
3. Il sistema mostrerà le informazioni dell'utente se trovato
4. Scegli l'opzione per generare una nuova password temporanea
5. La nuova password verrà mostrata e salvata nel database
6. Al primo accesso bisognera combiare la password temporanea

**Note di Sicurezza:**
- Le password sono salvate in formato hash SHA-256
- Non è possibile recuperare la password originale
- Le password temporanee devono essere cambiate al prossimo login
- Conserva le password temporanee in modo sicuro

### 4.3 Funzionalità Principali

#### 4.3.1 Ricerca dei Libri
L'applicazione consente agli utenti di cercare libri nel repository utilizzando vari criteri di ricerca:
1. **Visualizza tutti i libri** - Mostra il catalogo completo
2. **Cerca libro per titolo** - Ricerca specifica per titolo
3. **Cerca libro per autore** - Ricerca per autore
4. **Cerca libro per autore e anno** - Ricerca avanzata per autore e anno

#### 4.3.2 Gestione Librerie Personali
Gli utenti registrati possono:
- Creare librerie personali
- Aggiungere libri alle librerie
- Rimuovere libri dalle librerie
- Visualizzare le proprie librerie

#### 4.3.3 Valutazioni dei Libri
Gli utenti registrati possono inserire valutazioni per i libri basate su:
- Stile (1-5)
- Contenuto (1-5)
- Gradevolezza (1-5)
- Originalità (1-5)
- Edizione (1-5)

#### 4.3.4 Consigli di Libri
Gli utenti registrati possono suggerire libri ad altri utenti, contribuendo a un sistema di raccomandazioni più ricco e diversificato.

### 4.4 Menu Principale

Il menu principale offre le seguenti opzioni:
1. **Visualizza tutti i libri** - Mostra il catalogo completo
2. **Cerca libro per titolo** - Ricerca specifica per titolo
3. **Cerca libro per autore** - Ricerca per autore
4. **Cerca libro per autore e anno** - Ricerca avanzata per autore e anno
5. **Registrazione** - Creazione nuovo account
6. **Login** - Accesso al sistema

---

## 5. Troubleshooting

### 5.1 Problemi di Installazione

#### Problema: "Java not recognized as internal or external command"
**Soluzione:**
1. Verifica che Java sia installato: `java -version`
2. Se non installato, scarica Java 17+ dal sito ufficiale
3. Aggiungi Java al PATH di sistema
4. Usa `.\fix_java_version.bat` per correzioni automatiche

#### Problema: "PostgreSQL connection failed"
**Verifiche:**
1. PostgreSQL è installato e in esecuzione?
2. La password dell'utente 'postgres' è corretta?
3. Il servizio PostgreSQL è avviato?

#### Problema: "run_all.bat non si avvia"
**Da PowerShell:**
```powershell
.\run_all.bat
```

**Da CMD:**
```cmd
run_all.bat
```

### 5.2 Problemi di Esecuzione

#### Problema: "Database non trovato"
**Soluzione:**
1. Esegui `.\setup_database.bat`
2. Verifica che PostgreSQL sia in esecuzione
3. Controlla la password dell'utente 'postgres'

#### Problema: "File .jar non trovato"
**Soluzione:**
1. Verifica di essere nella directory corretta
2. Esegui `.\compile.bat` per ricompilare
3. Usa `.\run_all.bat` per setup completo

#### Problema: Caratteri strani nell'output
Questo è normale in alcuni terminali Windows - il funzionamento non è compromesso.

### 5.3 Supporto

Per qualsiasi altro problema, è disponibile la struttura originale del progetto (sorgenti compresi) che può essere importata in un qualsiasi IDE.

---

## 6. Sitografia

- [JAR Files: The Basics](https://docs.oracle.com/javase/tutorial/deployment/jar/basicsindex.html)
- [Java recognition problems](https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-Not-Recognized-Error-Fix)
- [Classes, Methods and Paths in Java](https://stackoverflow.com/)
- [Input, Output in Java](https://www.w3schools.com/java/)
- [PostgreSQL Downloads](https://www.postgresql.org/download/)
- [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK Downloads](https://adoptium.net/)

---

## 7. Bibliografia

- "Dai fondamenti agli oggetti: corso di programmazione in Java" - Pearson
- "Java: A Beginner's Guide" - McGraw-Hill Education
- "PostgreSQL: Up and Running" - O'Reilly Media 

**Versione del manuale:** 3.0  
**Data ultimo aggiornamento:** 08/2025  
**Compatibile con:** BookRecommender v3.0 (Sistema multi-utenti) 