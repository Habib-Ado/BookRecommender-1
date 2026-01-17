@echo off
echo ========================================
echo BookRecommender - Popolamento Database
echo ========================================
echo.

REM Verifica se Java è installato
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] ERRORE: Java non è installato o non è nel PATH
    echo Installa Java 17+ e riprova
    pause
    exit /b 1
)

REM Verifica se il driver JDBC è presente
if not exist "lib\postgresql-*.jar" (
    echo [ERROR] ERRORE: Driver JDBC PostgreSQL non trovato in lib/
    echo Scarica il driver da: https://jdbc.postgresql.org/download/
    pause
    exit /b 1
)

REM Verifica se i file compilati esistono
if not exist "bin\bookrecommender\*.class" (
    echo [WARN] ATTENZIONE: File compilati non trovati
    echo Compilazione in corso...
    call compile.bat
    if %errorlevel% neq 0 (
        echo [ERROR] ERRORE: Compilazione fallita
        pause
        exit /b 1
    )
)

REM Verifica se il file CSV esiste
if not exist "dati\BooksDatasetClean.csv" (
    echo [ERROR] ERRORE: File CSV con i libri non trovato in dati/
    echo Assicurati che il file BooksDatasetClean.csv sia presente
    pause
    exit /b 1
)

echo [OK] Avvio popolamento database...
echo.
echo [INFO] NOTE:
echo - Ti verrà richiesta la password del database PostgreSQL
echo - Il processo potrebbe richiedere alcuni minuti
echo - Se il database è già popolato, il processo verrà saltato
echo.

REM Avvia il popolamento
java -cp "bin;lib\postgresql-42.7.5.jar" bookrecommender.Trasferimento
if %errorlevel% neq 0 (
    echo [ERROR] ERRORE: Popolamento database fallito
    echo.
    echo [INFO] POSSIBILI SOLUZIONI:
    echo 1. Verifica che PostgreSQL sia in esecuzione
    echo 2. Controlla che la password del database sia corretta
    echo 3. Assicurati che il database sia stato inizializzato
    echo 4. Verifica che il file CSV sia leggibile
    pause
    exit /b 1
)
echo.
echo [OK] Popolamento database completato con successo!
echo.
echo [INFO] PROSSIMI PASSI:
echo 1. Esegui: start_server.bat (per avviare il server)
echo 2. Esegui: start_gui.bat (per avviare la GUI)
echo.
pause