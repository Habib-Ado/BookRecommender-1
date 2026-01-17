@echo off
echo ========================================
echo BookRecommender - Compilazione
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

REM Crea la directory bin se non esiste
if not exist "bin" (
    echo [INFO] Creazione directory bin...
    mkdir bin
)

REM Crea la directory bin/bookrecommender se non esiste
if not exist "bin\bookrecommender" (
    echo [INFO] Creazione directory bin\bookrecommender...
    mkdir bin\bookrecommender
)

echo [INFO] Compilazione in corso...
echo.

REM Compila tutti i file Java con target Java 17
echo [INFO] Target: Java 17
javac -d bin -cp "lib/*" -source 17 -target 17 src/bookrecommender/*.java

if %errorlevel% equ 0 (
    echo [OK] Compilazione completata con successo!
    echo.
    echo [INFO] File compilati salvati in: bin\bookrecommender\
    echo.
    echo [INFO] Prossimi passi:
    echo 1. Esegui setup_database.bat per configurare il database
    echo 2. Esegui start_server.bat per avviare il server
    echo 3. Esegui start_client.bat per avviare il client
    echo.
) else (
    echo [ERROR] ERRORE: Compilazione fallita
    echo Controlla i messaggi di errore sopra
    echo.
)

pause 