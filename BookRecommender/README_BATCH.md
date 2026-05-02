# BookRecommender - Script Batch

Questo documento spiega come utilizzare i file batch per eseguire il progetto BookRecommender.

## 📁 File Batch Disponibili

### 🔨 **compile.bat**
Compila tutti i file Java del progetto.

**Utilizzo:**
```batch
compile.bat
```

**Funzionalità:**
- Verifica l'installazione di Java
- Verifica la presenza del driver JDBC PostgreSQL (incluso nel progetto)
- Crea le directory necessarie (bin, bin/bookrecommender)
- Compila tutti i file Java in `src/bookrecommender/`
- Mostra messaggi di successo o errore

### 🗄️ **setup_database.bat**
Configura il database PostgreSQL.

**Utilizzo:**
```batch
setup_database.bat
```

**Funzionalità:**
- Menu interattivo per la configurazione del database
- Opzioni per inizializzare il database senza dati di esempio
- Test della connessione al database
- Visualizzazione delle informazioni del database

### 🗄️ **populate_db.bat**
Popola il database PostgreSQL con libri.

**Utilizzo:**
```batch
populate_db.bat
```

**Funzionalità:**
- Verifica se il database esiste e se è vuoto o non
- Popola il database con libri del file CSV
- Restituisce il numero totale di libri inseriti nel database


### 🖥️ **start_server.bat**
Avvia il server RMI.

**Utilizzo:**
```batch
start_server.bat
```

**Funzionalità:**
- Verifica i prerequisiti (Java, driver JDBC, file compilati)
- Richiede la password del database PostgreSQL
- Avvia il server RMI sulla porta 1099
- Rimane in esecuzione fino a Ctrl+C

### 💻 **start_client.bat**
Avvia il client RMI.

**Utilizzo:**
```batch
start_client.bat
```

**Funzionalità:**
- Verifica i prerequisiti (Java, driver JDBC, file compilati)
- Si connette al server RMI su localhost:1099
- Mostra il menu interattivo per l'utente
- Rimane in esecuzione fino a Ctrl+C

### 🖥️ **start_gui.bat**
Avvia l'interfaccia grafica GUI.

**Utilizzo:**
```batch
start_gui.bat
```

**Funzionalità:**
- Verifica i prerequisiti (Java, driver JDBC, file compilati)
- Si connette al server RMI su localhost:1099
- Mostra un'interfaccia grafica con pulsanti
- Ogni client GUI ha una sessione univoca
- Funzionalità: Login, Registrazione, Visualizzazione libri, Ricerca, Logout

### 🚀 **run_all.bat**
Setup completo automatizzato.

**Utilizzo:**
```batch
run_all.bat
```

**Funzionalità:**
- Esegue tutti i passaggi in sequenza
- Compilazione del progetto
- Configurazione del database (opzionale)
- Popolazione del database da libri
- Avvio del server in una finestra separata
- Avvio del client in una finestra separata

### 🧹 **clean.bat**
Pulisce i file compilati.

**Utilizzo:**
```batch
clean.bat
```

**Funzionalità:**
- Rimuove la directory `bin/` e tutti i file `.class`
- Utile per una compilazione pulita

### 🔍 **test_connection.bat**
Testa la connessione al database.

**Utilizzo:**
```batch
test_connection.bat
```

**Funzionalità:**
- Verifica la connessione al database PostgreSQL
- Mostra informazioni sulla connessione
- Utile per diagnosticare problemi di connessione

### 🔧 **fix_java_version.bat**
Risolve problemi di compatibilità Java.

**Utilizzo:**
```batch
fix_java_version.bat
```

**Funzionalità:**
- Verifica versione Java
- Pulizia file compilati
- Ricompilazione con target Java 17
- Test di esecuzione
- Risoluzione problemi di compatibilità

## 🔒 Test di Sicurezza

### **Test Recupero Password**
```batch
# Test completo del sistema di recupero password
test_password_recovery.bat
```

**Cosa Testa:**
- Ricerca utente tramite username
- Visualizzazione informazioni utente (senza password originale)
- Generazione password temporanee sicure
- Aggiornamento password nel database
- Validazione del sistema di sicurezza

**Prerequisiti:**
- Server RMI in esecuzione
- Database PostgreSQL configurato
- Almeno un utente registrato nel database

## 🚀 Guida Rapida

### Prima Esecuzione

1. **Verifica Prerequisiti:**
   - Java 17+ installato
   - PostgreSQL installato e configurato
   - Driver JDBC PostgreSQL in `lib/`

2. **Setup Completo:**
   ```batch
   run_all.bat
   ```

### Esecuzione Manuale

0. **Pulizia:**
   ```batch
   clean.bat
   ```

1. **Compilazione:**
   ```batch
   compile.bat
   ```

2. **Configurazione Database:**
   ```batch
   setup_database.bat
   ```

3. **Popolazione database:**
   ```batch
   populate.bat
   ```

4. **Avvio Server:**
   ```batch
   start_server.bat
   ```

5. **Avvio Client:**
   ```batch
   start_client.bat
   ```

## ⚠️ Note Importanti

### Prerequisiti

- **Java 17+**: Installato e configurato nel PATH
- **PostgreSQL**: Installato e in esecuzione
- **Driver JDBC**: Scaricato e posizionato in `lib/`

### Ordine di Esecuzione

0. **Pulizia** (vortemente consigliato)
1. **Compilazione** (solo la prima volta o dopo modifiche)
2. **Setup Database** (solo la prima volta)
3. **Populate Database** (solo la prima volta)
4. **Avvio Server** (prima del client)
5. **Avvio Client** (dopo il server)

### Risoluzione Problemi

### Errore "Porta gia in uso"
```
# Cambiare la porta sia nel server che nel client.
# Eseguire il programma di nuovo
```

#### Errore "Java non è installato"
```batch
# Verifica l'installazione di Java
# Puoi scaricare da: https://jdbc.postgresql.org/download/
java -version
```

#### Errore "Driver JDBC non trovato"
```batch
# Scarica il driver da:
# https://jdbc.postgresql.org/download/
# E posizionalo in lib/
```

#### Errore "File compilati non trovati"
```batch
# Ricompila il progetto
compile.bat
```

### Errore "Database vuoto"
```batch
# popola il database
populate_db.bat
```

## 📋 Esempi di Utilizzo

### Setup Completo Automatico
```batch
run_all.bat
```

### Setup Manuale Step-by-Step
```batch
# 1. Compilazione
compile.bat

# 2. Configurazione database
setup_database.bat

# 3. Avvio server (in una finestra)
start_server.bat

# 4. Avvio client (in un'altra finestra)
start_client.bat

# 5. Avvio GUI (opzionale)
start_gui.bat
```

### Pulizia e Ricompilazione
```batch
# 1. Pulizia
clean.bat

# 2. Ricompilazione
compile.bat
```

## 🔧 Personalizzazione

### Modifica della Porta RMI
Modifica la porta nei file `Server.java` e `Client.java`:
```java
private static final int PORT = 1099; // Cambia questo valore "1099"
```

### Modifica delle Credenziali Database
Le credenziali del database sono richieste dinamicamente all'avvio. Per modificare le impostazioni di default, modifica i file:
- `BookRecommender.java`
- `DatabaseInitializer.java`

## 📞 Supporto

Per problemi o domande:
1. Controlla i messaggi di errore nei file batch
2. Verifica i prerequisiti
3. Consulta la documentazione principale (`README.md`)
4. Controlla il manuale utente (`USER_MANUAL.md`)

---

**Versione:** 3.0  
**Data:** 08/2025  
**Compatibile con:** BookRecommender v3.0