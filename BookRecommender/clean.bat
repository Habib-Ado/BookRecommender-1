@echo off
echo ========================================
echo Pulizia File Compilati
echo ========================================
echo.

echo [INFO] Rimozione directory bin...
if exist "bin" (
    rmdir /s /q "bin"
    echo [OK] Directory bin rimossa
) else (
    echo [INFO] Directory bin non trovata
)

echo.
echo [INFO] Rimozione file .class sparsi...
for /r . %%f in (*.class) do (
    del "%%f" 2>nul
    echo [OK] Rimosso: %%f
)

echo.
echo [INFO] Rimozione file .jar temporanei...
del *.jar 2>nul

echo.
echo [OK] Pulizia completata
echo.
echo [INFO] Prossimi passi:
echo 1. Esegui compile.bat per ricompilare
echo 2. Verifica la versione Java: java -version
echo 3. Assicurati di usare Java 17 o superiore
echo.
pause 