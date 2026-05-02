# BookRecommender

## Presentazione
BookRecommender è un sistema distribuito client-server per la gestione e la raccomandazione di libri, sviluppato in Java e basato su Java RMI (Remote Method Invocation). Permette agli utenti di registrarsi, autenticarsi, consultare un catalogo di libri, valutare libri, consigliare letture, e gestire librerie personali in modo concorrente e distribuito.

**Caratteristiche principali:**
- **Sistema multi-utente** con sessioni isolate
- **Database PostgreSQL** per la persistenza dei dati
- **Setup automatico** tramite DatabaseInitializer
- **Architettura client-server** basata su Java RMI
- **Gestione concorrente** delle operazioni

---

## 🚀 Funzionalità principali

### 👥 **Gestione Utenti**
- **Registrazione** : Creazione account con validazione dati (nome, cognome, codice fiscale, email, userid, password)
- **Login/Logout** : Autenticazione sicura con sessioni isolate per ogni client
- **Recupero Password** : Sistema di recupero password con generazione di password temporanee sicure
- **Cambio Password** : Funzionalità per cambiare password con validazione
- **Validazione** : Controlli su codice fiscale, email, password e userid
- **Sistema di sessioni:** Ogni client ha una sessione univoca identificata da un Session ID
- **Isolamento delle sessioni:** Gli utenti non possono accedere ai dati di altri utenti

### 📚 **Gestione Libri**
- **Ricerca Avanzata** : Per titolo, autore, autore+anno
- **Visualizzazione Completa**  Visualizza tutti i libri presenti nel sistema: 
                                Dettagli libri con valutazioni e consigli
- **Catalogo Completo** : Lista di tutti i libri disponibili nel sistema

### ⭐ **Sistema di Valutazione**
- **Valutazione Multi-criteri**  Gli utenti autenticati possono valutare i libri su diversi aspetti:
                                 Stile, contenuto, gradevolezza, originalità, edizione (1-5)
- **Calcolo Automatico** : Voto finale come media delle valutazioni
- **Visualizzazione** : Mostra tutte le valutazioni per ogni libro

### 💡 **Sistema di Consigli**
- **Consigli Personalizzati** Gli utenti possono consigliare altri libri a partire da un libro di riferimento: Suggerimenti basati sui libri di riferimento
- **Gestione Multi-libri** : Fino a 3 libri consigliati per ogni riferimento
- **Visualizzazione** : Mostra tutti i consigli per ogni libro

### 📖 **Librerie Personali**
- **Creazione** : Librerie personalizzate con nomi personalizzati
- **Gestione** : Aggiunta visualizzazione e rimozione libri dalle librerie
- **Visualizzazione** : Lista completa dei libri in ogni libreria

### 🔐 **Gestione Sessione**
- **Sessioni Isolate** : Ogni client ha una sessione indipendente
- **Multi-utente** : Supporto per più utenti contemporaneamente
- **Sicurezza** : Hashing SHA-256 delle password e validazione dati
- **Concorrenza:** Più utenti possono utilizzare il sistema simultaneamente

---

## Architettura

### **Componenti Principali**
- **Server RMI:** Espone i servizi remoti implementando le logiche di business e l'accesso al database PostgreSQL
- **Client RMI:** Interfaccia testuale che permette all'utente di interagire con il sistema tramite menu e input da riga di comando
- **Database PostgreSQL:** Contiene le tabelle per utenti, libri, valutazioni, consigli e librerie
- **DatabaseInitializer:** Classe per l'inizializzazione automatica del database

### **Tecnologie Utilizzate**
- **Java 17+** - Linguaggio di programmazione principale
- **Java RMI** - Comunicazione client-server
- **PostgreSQL** - Database relazionale
- **JDBC** - Connessione al database
- **ConcurrentHashMap** - Gestione delle sessioni concorrenti

---

## Configurazione Rapida

### **Prerequisiti**
- **Java 17** o superiore
- **PostgreSQL 12** o superiore
- **Driver JDBC PostgreSQL** (postgresql-*.jar)

### **Setup Automatico (Raccomandato)**

#### **Metodo 1: Script Batch (Windows)**
```bash
setup_database.bat
```

#### **Metodo 2: Script PowerShell (Windows)**
```powershell
.\setup_database.ps1
```

#### **Metodo 3: Comando Manuale**
```bash
# Compilare il progetto
javac -d bin -cp "lib/*" src/bookrecommender/*.java

# Inizializzare il database
java -cp "bin;lib/*" bookrecommender.DatabaseInitializer

# Popolare la base di dati
java -cp "bin;lib/*" bookrecommender.Trasferimento
```

### **Opzioni di Setup**
1. **Initialize database only** - Crea solo le tabelle
2. **Initialize database with sample data** - Crea le tabelle e inserisce dati di esempio
3. **Test database connection** - Verifica la connessione al database in modo automatico
4. **Exit** - Esce dal setup

### **🔐 Gestione Password Database**

**IMPORTANTE:** Il sistema richiederà automaticamente la password del database PostgreSQL all'avvio.

**Quando viene richiesta la password:**
- All'avvio del DatabaseInitializer (per configurare il database)
- All'avvio del Server (per connettersi al database)
- Durante i test di connessione

**Comportamento:**
- La password viene richiesta tramite console
- Viene testata la connessione con la password inserita
- Se la password è errata, viene data la possibilità di riprovare
- La password non viene mai salvata in chiaro nel codice

---

## Come eseguire l'applicazione

### **1. Configurazione del Database**

Prima di tutto, configura il database utilizzando uno dei metodi sopra descritti.

**Nota:** Verrà richiesta la password del database PostgreSQL durante il setup.

### **2. Compilazione**

Posizionati nella cartella principale del progetto e compila tutti i file Java:

```bash
javac -d bin -cp "lib/*" src/bookrecommender/*.java
```

### **3. Avvio del Server**

Avvia il registry RMI (se non già attivo altrimenti cambia porta):

```bash
start rmiregistry 1099
```

In un nuovo terminale, avvia il server:

```bash
cd bin
java -cp ".;../lib/*" bookrecommender.Server
```

**Nota:** Il server richiederà la password del database all'avvio.

### **4. Avvio del Client**

In un altro terminale, avvia il client:

```bash
cd bin
java -cp ".;../lib/*" bookrecommender.Client
```

### **5. Avvio dell'Interfaccia GUI (Opzionale)**

Per utilizzare l'interfaccia grafica invece del client testuale:

```bash
cd bin
java -cp ".;../lib/*" bookrecommender.BookRecommenderGUI
```

**Oppure utilizza lo script batch:**
```bash
start_gui.bat
```

### **6. Utilizzo**

Segui il menu interattivo che appare nel client. Puoi:
- Registrarti o fare login
- Visualizzare e cercare libri
- Creare e gestire librerie personali
- Valutare e consigliare libri
- Effettuare il logout o uscire

---

## Struttura del Database

### **Tabelle Principali**

#### **Tabella `userid`**
```sql
CREATE TABLE userid (
    nome_cognome VARCHAR(100) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    userid VARCHAR(50) PRIMARY KEY,
    password VARCHAR(256) NOT NULL
);
```

#### **Tabella `libri`**
```sql
CREATE TABLE libri (
    titolo VARCHAR(100) PRIMARY KEY,
    autore VARCHAR(100) NOT NULL,
    genere VARCHAR(50) NOT NULL,
    editore VARCHAR(100),
    anno INT NOT NULL
);
```

#### **Tabella `valutazioni`**
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

#### **Tabella `consigli`**
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

#### **Tabella `librerie`**
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

## 📁 File del Progetto

### 🔧 **File Principali**
- `src/bookrecommender/` : Codice sorgente Java
- `bin/` : File compilati (.class)
- `lib/` : Driver JDBC PostgreSQL
- `doc/` : Documentazione del progetto

### 🚀 **Script di Configurazione**
- `setup_database.bat` : Setup database Windows
- `setup_database.ps1` : Setup database PowerShell
- `compile.bat` : Compilazione progetto
- `start_server.bat` : Avvio server RMI
- `start_client.bat` : Avvio client RMI
- `start_gui.bat` : Avvio interfaccia GUI
- `run_all.bat` : Setup completo automatizzato
- `clean.bat` : Pulizia file compilati

### 🔍 **Script di Test Effettuati**
- `test_connection.bat` : Test connessione database
- `test_password_recovery.bat` : Test recupero password
- `test_change_password.bat` : Test cambio password
- `test_visualizza_libri.bat` : Test visualizzazione libri
- `test_gui.bat` : Test interfaccia GUI completo
- `test_gui_simple.bat` : Test GUI semplificato
- `test_gui_standalone.bat` : Test GUI senza server
- `test_complete_project.bat` : Test completo del progetto
- `test_database_functionality.bat` : Test funzionalità database

### 📖 **Documentazione**
- `README.md` : Documentazione principale
- `USER_MANUAL.md` : Manuale utente
- `TECHNICAL_MANUAL.md` : Manuale tecnico
- `DATABASE_SETUP_README.md` : Guida setup database
- `README_BATCH.md` : Documentazione script batch

---

## Troubleshooting

### **Problemi Comuni**

#### **Errore di connessione al database**
```
❌ Errore di connessione al database
```
**Soluzioni:**
- Verificare che PostgreSQL sia in esecuzione
- Controllare la password del database fornita
- Verificare che il database 'bookrecommender' esista

#### **Errore RMI**
```
java.rmi.ConnectException: Connection refused
```
**Soluzioni:**
- Verificare che il registry RMI sia avviato: `start rmiregistry 1099`
- Controllare che il server sia in esecuzione
- Verificare che la porta 1099 sia libera oppure cambiare porta

#### **Errore di driver JDBC**
```
❌ ClassNotFoundException: org.postgresql.Driver
```
**Soluzioni:**
- Scaricare il driver PostgreSQL da: https://jdbc.postgresql.org/download/
- Posizionare il file `postgresql-*.jar` nella cartella `lib/`
- Verificare che il classpath includa la cartella `lib/`

#### **Errore di versione Java**
```
❌ UnsupportedClassVersionError: Versione non sopportata 
```
**Soluzioni:**
- Eseguire `fix_java_version.bat` per risolvere automaticamente
- Verificare che Java 17+ sia installato: `java -version`
- Ricompilare con target Java 17: `javac -source 17 -target 17`
- Scarica la versione adatta da: https://www.oracle.com/it/java/technologies/downloads/

---

## 🔧 Caratteristiche Tecniche

### 🔐 **Sicurezza**
- **Hashing SHA-256** : Password salvate in formato hash sicuro
- **Validazione Input** : Controlli su tutti i dati inseriti dall'utente
- **Sessioni Isolate** : Impossibilità di accesso ai dati di altri utenti
- **Recupero Password** : Sistema sicuro di recupero con password temporanee
- **Controlli Integrità** : Validazione database e gestione errori
- **Gestione sessioni isolate** per ogni client

### ⚡ **Performance**
- **Connessioni Pooling** : Gestione ottimizzata delle connessioni database
- **Query Ottimizzate** : Indici e controlli di integrità referenziale
- **Concorrenza** : Gestione thread-safe delle sessioni multiple
- **Scalabilità** : Supporto per grandi volumi di dati

### 🛠️ **Manutenibilità**
- **Codice Modulare** : Separazione chiara delle responsabilità
- **Documentazione Completa** : Commenti e guide dettagliate
- **Setup Automatico** : Configurazione semplificata del database
- **Script di Test** : Verifica automatica delle funzionalità
-  **Codice ben documentato** con JavaDoc

---

## Note Aggiuntive

- **Tutte le operazioni sono concorrenti** e thread-safe grazie all'uso di metodi `synchronized` lato server
- **I messaggi di errore dettagliati** del database non vengono mai mostrati all'utente finale, ma solo loggati lato server
- **Il sistema è facilmente estendibile** per nuove funzionalità
- **Setup automatico** tramite DatabaseInitializer semplifica la configurazione
- **Sistema multi-utente** con sessioni isolate garantisce la sicurezza

---

## Autori
- **Mouhammad Toure**                                               -Matricola:     758051      -Sede: VA
- **Daniel Viny Kamdem Tagne**                                      -Matricola:     759563      -Sede: VA
- **Agnes Balkaire Makouwe**                                        -Matricola:     759700      -Sede: VA

**Progetto Laboratorio B: BookRecommender**  
**Anno accademico: 2024/2025**

---

**Versione:** 3.0  
**Data ultimo aggiornamento:** 08/2025  
**Compatibile con:** Java 17+, PostgreSQL 12+
