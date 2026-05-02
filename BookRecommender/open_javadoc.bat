@echo off
echo ========================================
echo    Apertura Documentazione JavaDoc
echo ========================================
echo.

if exist "javadoc\index.html" (
    echo Apertura della documentazione JavaDoc...
    start javadoc\index.html
    echo.
    echo ✅ Documentazione aperta nel browser!
    echo.
    echo Se il browser non si apre automaticamente,
    echo apri manualmente il file: javadoc\index.html
) else (
    echo.
    echo ❌ ERRORE: Documentazione JavaDoc non trovata!
    echo.
    echo Per generare la documentazione, esegui:
    echo   generate_javadoc.bat
    echo.
)

pause 