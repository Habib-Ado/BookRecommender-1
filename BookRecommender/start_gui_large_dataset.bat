@echo off
echo ========================================
echo BookRecommender - GUI per Dataset Grandi
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

echo [OK] Avvio dell'interfaccia GUI per dataset grandi...
echo.
echo [INFO] NOTE:
echo - Assicurati che il server sia già in esecuzione
echo - Questa versione usa memoria extra per dataset grandi
echo - L'interfaccia GUI si connetterà al server su localhost:1099
echo - Ogni client GUI avrà una sessione univoca
echo.
echo [WARN] Se la GUI non si apre, controlla che:
echo    1. Il server RMI sia in esecuzione
echo    2. PostgreSQL sia attivo
echo    3. La password del database sia corretta
echo.
echo [INFO] OPZIONI MEMORIA:
echo - Heap massimo: 2048MB
echo - Heap iniziale: 512MB
echo - Garbage Collector: G1GC (ottimizzato per grandi dataset)
echo.

REM Avvia l'interfaccia GUI con opzioni di memoria per dataset grandi
java -Xmx2048m -Xms512m -XX:+UseG1GC -XX:+UseStringDeduplication -cp "bin;lib/*" bookrecommender.BookRecommenderGUI

echo.
echo [OK] Interfaccia GUI chiusa
pause
