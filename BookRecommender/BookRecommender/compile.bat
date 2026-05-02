@echo off
echo ========================================
echo BookRecommender - Compilazione
echo ========================================
echo.

REM Verifica se Java è installato
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERRORE: Java non è installato o non è nel PATH
    echo Installa Java 17+ e riprova
    pause
    exit /b 1
)

REM Verifica se il driver JDBC è presente
if not exist "lib\postgresql-*.jar" (
    echo ❌ ERRORE: Driver JDBC PostgreSQL non trovato in lib/
    echo Scarica il driver da: https://jdbc.postgresql.org/download/
    pause
    exit /b 1
)

REM Crea la directory bin se non esiste
if not exist "bin" (
    echo 📁 Creazione directory bin...
    mkdir bin
)

REM Crea la directory bin/bookrecommender se non esiste
if not exist "bin\bookrecommender" (
    echo 📁 Creazione directory bin\bookrecommender...
    mkdir bin\bookrecommender
)

echo 🔨 Compilazione in corso...
echo.

REM Compila tutti i file Java con target Java 17
echo 📝 Target: Java 17
for /r src %%f in (*.java) do javac -d bin -cp "lib/*;src" -source 17 -target 17 %%f

if %errorlevel% equ 0 (
    echo ✅ Compilazione completata con successo!
    echo.
    echo 📁 File compilati salvati in: bin\bookrecommender\
    echo.
    echo 🚀 Prossimi passi:
    echo 1. Esegui setup_database.bat per configurare il database
    echo 2. Esegui start_server.bat per avviare il server
    echo 3. Esegui start_client.bat per avviare il client
    echo.
) else (
    echo ❌ ERRORE: Compilazione fallita
    echo Controlla i messaggi di errore sopra
    echo.
)

pause 