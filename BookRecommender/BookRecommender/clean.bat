@echo off
echo ========================================
echo Pulizia File Compilati
echo ========================================
echo.

echo 🧹 Rimozione directory bin...
if exist "bin" (
    rmdir /s /q "bin"
    echo ✅ Directory bin rimossa
) else (
    echo ℹ️  Directory bin non trovata
)

echo.
echo 🧹 Rimozione file .class sparsi...
for /r . %%f in (*.class) do (
    del "%%f" 2>nul
    echo ✅ Rimosso: %%f
)

echo.
echo 🧹 Rimozione file .jar temporanei...
del *.jar 2>nul

echo.
echo ✅ Pulizia completata
echo.
echo 📝 Prossimi passi:
echo 1. Esegui compile.bat per ricompilare
echo 2. Verifica la versione Java: java -version
echo 3. Assicurati di usare Java 17 o superiore
echo.
pause 