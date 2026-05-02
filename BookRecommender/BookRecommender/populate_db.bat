@echo off
echo ========================================
echo BookRecommender - Popolamento Database
echo ========================================
echo.
java -cp "bin;lib\postgresql-42.7.5.jar" bookrecommender.Trasferimento
if %errorlevel% neq 0 (
    echo ❌ ERRORE: Esecuzione Trasferimento.java fallita
    exit /b 1
)
echo ✅ Verifica popolamento database completata!