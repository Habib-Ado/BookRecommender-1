@echo off
echo ========================================
echo BookRecommender - Diagnostica Sistema
echo ========================================
echo.

REM Verifica Java
echo [INFO] Verifica Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java non installato o non nel PATH
    echo Installa Java 17+ e riprova
    pause
    exit /b 1
) else (
    echo [OK] Java installato correttamente
)

REM Verifica driver JDBC
echo.
echo [INFO] Verifica Driver JDBC...
if not exist "lib\postgresql-*.jar" (
    echo [ERROR] Driver JDBC PostgreSQL non trovato in lib/
    echo Scarica il driver da: https://jdbc.postgresql.org/download/
    pause
    exit /b 1
) else (
    echo [OK] Driver JDBC trovato
)

REM Verifica file compilati
echo.
echo [INFO] Verifica file compilati...
if not exist "bin\bookrecommender\*.class" (
    echo [WARN] File compilati non trovati - Compilazione necessaria
    call compile.bat
    if %errorlevel% neq 0 (
        echo [ERROR] Compilazione fallita
        pause
        exit /b 1
    )
) else (
    echo [OK] File compilati presenti
)

REM Verifica file CSV
echo.
echo [INFO] Verifica dati libri...
if not exist "dati\BooksDatasetClean.csv" (
    echo [ERROR] File CSV con i libri non trovato in dati/
    echo Assicurati che il file BooksDatasetClean.csv sia presente
    pause
    exit /b 1
) else (
    echo [OK] File CSV libri trovato
)

REM Test connessione database
echo.
echo [INFO] Test connessione database...
echo Inserisci la password del database PostgreSQL per il test:
java -cp "bin;lib/*" bookrecommender.DatabaseInitializer
if %errorlevel% neq 0 (
    echo [ERROR] Test connessione database fallito
    pause
    exit /b 1
)

echo.
echo [OK] DIAGNOSTICA COMPLETATA
echo.
echo [INFO] PROSSIMI PASSI:
echo 1. Esegui: populate_db.bat (per popolare il database)
echo 2. Esegui: start_server.bat (per avviare il server)
echo 3. Esegui: start_gui.bat (per avviare la GUI)
echo.
pause
