@echo off
echo ========================================
echo BookRecommender - Server RMI
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

echo [OK] Avvio del server RMI...
echo.
echo [INFO] NOTE:
echo - Il server richiederà la password del database PostgreSQL
echo - Il server rimarrà in esecuzione fino a quando non lo chiudi
echo - Premi Ctrl+C per fermare il server
echo.

REM Avvia il server con opzioni di memoria ottimizzate
java -Xmx512m -Xms128m -XX:+UseG1GC -cp "bin;lib/*" bookrecommender.Server

echo.
echo [OK] Server chiuso
pause 