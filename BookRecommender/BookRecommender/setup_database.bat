@echo off
echo ========================================
echo BookRecommender Database Setup
echo ========================================
echo.

REM Verifica se Java è installato
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Verifica se il driver JDBC è disponibile
if not exist "lib\postgresql-*.jar" (
    echo WARNING: PostgreSQL JDBC driver not found in lib/ directory
    echo Please download postgresql-*.jar and place it in the lib/ directory
    echo Download from: https://jdbc.postgresql.org/download/
    echo.
)

REM Compila il progetto
echo Compiling project...
javac -d bin -cp "lib/*" src/bookrecommender/*.java
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo ========================================
echo Opzioni di inizializzazione del database:
echo ========================================
echo 1. Inizializza solo il database
echo 2. Testa la connessione al database
echo 3. Mostra le informazioni del database
echo 4. Esci
echo.
echo NOTA: Ti verrà chiesto la password del database
echo.

set /p choice="Enter your choice (1-4): "

if "%choice%"=="1" (
    echo.
    echo Inizializzazione del database...
    echo Ti verrà chiesto la password del PostgreSQL
    java -cp "bin;lib/*" bookrecommender.DatabaseInitializer
) else if "%choice%"=="2" (
    echo.
    echo Test della connessione al database...
    echo Ti verrà chiesto la password del PostgreSQL
    java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.testConnection()"
) else if "%choice%"=="3" (
    echo.
    echo Mostra le informazioni del database...
    echo Ti verrà chiesto la password del PostgreSQL
    java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.showDatabaseInfo()"
) else if "%choice%"=="4" (
    echo Esci...
    exit /b 0
) else (
    echo Scelta non valida. Inserisci un numero tra 1 e 4.
)

echo.
echo ========================================
echo Setup completed!
echo ========================================
echo.
echo Passaggi successivi
echo 1. Configura il servizio PostgreSQL (eseguendo setup_database.bat e populate_db.bat)
echo 2. Avvia il server (eseguendo start_server.bat)
echo 3. Avvia il client (eseguendo start_client.bat)
echo 4. Esegui un altro client per testare il sistema multi-utente (eseguendo start_client.bat)

echo.
echo NOTA: Puoi anche eseguire il server e il client in una singola finestra (eseguendo start_all.bat)
echo.
echo NOTA: Il server chiederà anche la password del database PostgreSQL per poter accedere al database
echo   (inserisci la password del database PostgreSQL)
echo.
pause 