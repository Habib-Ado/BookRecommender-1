@echo off
echo ========================================
echo BookRecommender - Setup Completo (versione 1.0)
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

echo [INFO] Avvio setup completo BookRecommender...
echo.

echo [STEP 0] Passo 0: Pulizia del sistema
echo ========================================
call clean.bat
if %errorlevel% neq 0 (
    echo [ERROR] ERRORE: Pulizia del sistema fallita
    pause
    exit /b 1
)

echo [STEP 1] Passo 1: Compilazione del progetto
echo ========================================
call compile.bat
if %errorlevel% neq 0 (
    echo [ERROR] ERRORE: Compilazione fallita
    pause
    exit /b 1
)

echo.
echo [STEP 2] Passo 2: Configurazione del database
echo ========================================
echo [WARNING] ATTENZIONE: Questo passaggio richiede PostgreSQL installato
echo.
set /p choice="Vuoi configurare il database ora? (s/n): "
if /i "%choice%"=="s" (
    call setup_database.bat
    if %errorlevel% neq 0 (
        echo [ERROR] ERRORE: Configurazione database fallita
        pause
        exit /b 1
    )
    echo.
    echo [INFO] Verifica e popolamento del database con i libri...
    echo [INFO] Il popolamento avverrà solo se il database è vuoto
    call populate_db.bat
    if %errorlevel% neq 0 (
        echo [ERROR] ERRORE: Popolamento database fallito
        pause
        exit /b 1
    )
) else (
    echo [SKIP] Configurazione database saltata
    echo Puoi eseguire setup_database.bat e populate_db.bat manualmente
)

echo.
echo [STEP 3] Passo 3: Avvio del server
echo ========================================
echo [WARNING] ATTENZIONE: Il server rimarrà in esecuzione
echo Premi Ctrl+C per fermare il server quando vuoi
echo.
set /p choice="Vuoi avviare il server ora? (s/n): "
if /i "%choice%"=="s" (
    echo [OK] Avvio del server...
    start "BookRecommender Server" cmd /k "start_server.bat"
    echo.
    echo [STEP 4] Passo 4: Avvio del client
    echo ========================================
    echo [WAIT] Attendi 10 secondi per permettere al server di avviarsi...
    echo       (per evitare errori di connessione)
    timeout /t 10 /nobreak >nul
    echo [OK] Avvio del client...
    start "BookRecommender Client" cmd /k "start_client.bat"
    echo.
    echo [SUCCESS] Setup completato!
    echo.
    echo [INFO] Istruzioni:
    echo - Il server è in esecuzione in una finestra separata
    echo - Il client è in esecuzione in una finestra separata
    echo - Chiudi le finestre per fermare i programmi
    echo - Esegui un altro client per testare il sistema multi-utente
    echo   (usa start_client.bat)
    echo.
) else (
    echo [SKIP] Avvio server saltato
    echo Puoi eseguire start_server.bat e start_client.bat manualmente
)

echo.
echo [SUCCESS] Setup completato!
pause