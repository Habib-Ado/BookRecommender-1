@echo off
echo ========================================
echo BookRecommender - Client RMI
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

REM Verifica se i file compilati esistono
if not exist "bin\bookrecommender\*.class" (
    echo ⚠️  ATTENZIONE: File compilati non trovati
    echo Compilazione in corso...
    call compile.bat
    if %errorlevel% neq 0 (
        echo ❌ ERRORE: Compilazione fallita
        pause
        exit /b 1
    )
)

echo ✅ Avvio del client RMI...
echo.
echo 📝 NOTE: 
echo - Assicurati che il server sia già in esecuzione
echo - Il client si connetterà al server su localhost:1099
echo - Premi Ctrl+C per chiudere il client
echo.

REM Avvia il client
java -cp "bin;lib/*" bookrecommender.Client

echo.
echo ✅ Client chiuso
pause 