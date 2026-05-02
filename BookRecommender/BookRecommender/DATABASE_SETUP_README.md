# BookRecommender Database Setup

Questo documento spiega come configurare il database PostgreSQL per il progetto BookRecommender.

## 📋 Prerequisiti

### 1. PostgreSQL
- Installare PostgreSQL 12 o superiore
- Configurare la password per l'utente 'postgres'
- Assicurarsi che il servizio PostgreSQL sia in esecuzione

### 2. Java
- Java 17 o superiore
- Verificare con: `java -version`

### 3. Driver JDBC PostgreSQL
- Scaricare `postgresql-*.jar` da https://jdbc.postgresql.org/download/
- Posizionare il file nella cartella `lib/`

## 🚀 Configurazione Rapida

### Metodo 1: Script Batch (Windows)
```bash
setup_database.bat
```

### Metodo 2: Script PowerShell (Windows)
```powershell
.\setup_database.ps1
```

### Metodo 3: Comando Manuale
```bash
# Compilare il progetto
javac -d bin -cp "lib/*" src/bookrecommender/*.java

# Inizializzare solo il database
java -cp "bin;lib/*" bookrecommender.DatabaseInitializer

# Inizializzare con dati di esempio
java -cp "bin;lib/*" bookrecommender.DatabaseInitializer --with-sample-data
```

## 🔐 Gestione Password Database

**IMPORTANTE:** Il sistema richiederà automaticamente la password del database PostgreSQL all'avvio.

### Quando viene richiesta la password:
1. **All'avvio del DatabaseInitializer** - Per configurare il database
2. **All'avvio del Server** - Per connettersi al database
3. **Durante i test di connessione** - Per verificare la configurazione

### Comportamento:
- La password viene richiesta tramite console
- Viene testata la connessione con la password inserita
- Se la password è errata, viene data la possibilità di riprovare
- La password non viene mai salvata in chiaro nel codice

### Esempio di output:
```
=== BookRecommender Database Setup ===
Inserisci la password del database PostgreSQL:
Password: ****
✅ Password del database verificata correttamente!
```

## 📊 Struttura del Database

### Tabelle Create

#### 1. **userid** - Utenti registrati
```sql
CREATE TABLE userid (
    nome_cognome VARCHAR(100) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    userid VARCHAR(50) PRIMARY KEY,
    password VARCHAR(256) NOT NULL
);
```

#### 2. **libri** - Catalogo dei libri
```sql
CREATE TABLE libri (
    titolo VARCHAR(100) PRIMARY KEY,
    autore VARCHAR(100) NOT NULL,
    genere VARCHAR(50) NOT NULL,
    editore VARCHAR(100),
    anno INT NOT NULL
);
```

#### 3. **valutazioni** - Valutazioni degli utenti
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

#### 4. **consigli** - Raccomandazioni tra utenti
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

#### 5. **librerie** - Librerie personali degli utenti
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

## 🔧 Configurazione

### Password del Database

**Non è più necessario modificare il codice per cambiare la password!**

Il sistema ora richiede automaticamente la password all'avvio. Questo rende il sistema più sicuro e flessibile.

### Dati di Esempio

La classe include dati di esempio per testare il sistema:

#### Libri di Esempio
- Il Nome della Rosa (Umberto Eco)
- Il Signore degli Anelli (J.R.R. Tolkien)
- 1984 (George Orwell)
- Il Piccolo Principe (Antoine de Saint-Exupéry)
- La Divina Commedia (Dante Alighieri)
- E altri...

#### Utenti di Esempio
- Mario Rossi (mario_rossi / password123)
- Giulia Bianchi (giulia_bianchi / password123)
- Luca Verdi (luca_verdi / password123)

## 🧪 Test e Verifica

### Test di Connessione
```bash
java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.testConnection()"
```

### Informazioni Database
```bash
java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.showDatabaseInfo()"
```

## 🔍 Risoluzione Problemi

❌ ERRORE: Password non può essere vuota!
```
**Soluzione:**
- Inserire una password valida quando richiesto
- Verificare che la password non contenga solo spazi

### Errore di connessione al database
```
❌ ERRORE: Impossibile connettersi al database con la password inserita!
```
**Soluzioni:**
- Verificare che PostgreSQL sia in esecuzione
- Controllare che la password sia corretta
- Verificare che l'utente 'postgres' abbia i permessi necessari
- Controllare che il database 'postgres' esista

### Errore di compilazione
```
❌ Compilation failed
```
**Soluzioni:**
- Verificare che Java sia installato: `java -version`
- Controllare che il driver PostgreSQL sia nella cartella `lib/`
- Verificare la sintassi del codice

### Errore di permessi
```
❌ Permission denied
```
**Soluzioni:**
- Verificare che l'utente 'postgres' abbia i permessi necessari
- Controllare che la password sia corretta
- Verificare che il database 'postgres' esista

## 📝 Note Importanti

### Sicurezza
- **Password richiesta dinamicamente** - Non più hardcodata nel codice
- **Test di connessione** - Verifica automatica della password
- **Gestione errori** - Messaggi informativi per problemi di connessione
- **Possibilità di riprovare** - In caso di password errata

### Backup
- Eseguire backup regolari del database
- Utilizzare `pg_dump` per esportare i dati
- Mantenere copie di sicurezza delle configurazioni

### Performance
- Creare indici appropriati per le query frequenti
- Monitorare le performance del database
- Considerare l'ottimizzazione delle query

## 🚀 Prossimi Passi

Dopo aver configurato il database:

1. **Avviare il Registry RMI:**
   ```bash
   start rmiregistry 1099
   ```

2. **Avviare il Server:**
   ```bash
   java -cp "bin;lib/*" bookrecommender.Server
   ```
   *Nota: Verrà richiesta la password del database*

3. **Avviare il Client:**
   ```bash
   java -cp "bin;lib/*" bookrecommender.Client
   ```

## 📞 Supporto

Per problemi o domande:
1. Controllare i log di errore
2. Verificare la documentazione PostgreSQL
3. Controllare la configurazione Java


## 🔒 Sicurezza

### **Gestione Password**
- **Hashing SHA-256** : Le password sono salvate in formato hash sicuro
- **Validazione Input** : Controlli su tutti i dati inseriti dall'utente
- **Recupero Password** : Sistema sicuro di recupero con password temporanee
- **Sessioni Isolate** : Ogni client ha una sessione indipendente

### **Controlli di Integrità**
- **Database** : Controlli di integrità referenziale
- **Validazione** : Controlli sui dati inseriti dall'utente
- **Gestione Errori** : Messaggi informativi senza esposizione di dati sensibili

### **Sistema di Recupero Password**
- **Ricerca Utente** : Verifica esistenza tramite username
- **Visualizzazione Sicura** : Mostra informazioni senza password originale
- **Password Temporanee** : Generazione di password sicure di 12 caratteri
- **Mix di Caratteri** : Lettere maiuscole, minuscole, numeri, caratteri speciali 

---

**Versione:** 2.0  
**Data:** 08/2025  
**Compatibile con:** BookRecommender v3.0 (Password dinamica) 
