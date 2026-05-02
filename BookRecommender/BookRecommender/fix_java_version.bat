@echo off
echo ========================================
echo Risoluzione Problema Versione Java
echo ========================================
echo.

echo 🔍 Verifica versione Java...
java -version
if %errorlevel% neq 0 (
    echo ❌ ERRORE: Java non è installato
    echo Installa Java 17+ da: https://www.oracle.com/java/technologies/downloads/ e riprova
    pause
    exit /b 1
)

echo.
echo 🔍 Verifica versione Java compiler...
javac -version
if %errorlevel% neq 0 (
    echo ❌ ERRORE: Java compiler non disponibile
    pause
    exit /b 1
)

echo.
echo 🧹 Pulizia file compilati...
call clean.bat

echo.
echo 🔄 Ricompilazione con impostazioni corrette...
echo.

REM Ricompila con target Java 17 esplicito
javac -d bin -cp "lib/*" -source 17 -target 17 src/bookrecommender/*.java

if %errorlevel% neq 0 (
    echo ❌ ERRORE: Compilazione fallita
    echo.
    echo 💡 Soluzioni possibili:
    echo 1. Installa Java 17 o superiore
    echo 2. Verifica che JAVA_HOME punti alla versione corretta
    echo 3. Aggiungi Java al PATH
    pause
    exit /b 1
)

echo.
echo ✅ Compilazione completata con successo!
echo.
echo 🧪 Test di esecuzione...
java -cp "bin;lib/*" bookrecommender.BookRecommender

if %errorlevel% neq 0 (
    echo ❌ ERRORE: Test di esecuzione fallito
    pause
    exit /b 1
)

echo.
echo ✅ Tutto funziona correttamente!
echo.
echo 📝 Ora puoi eseguire:
echo - start_server.bat
echo - start_client.bat
echo - start_gui.bat
echo.
pause 