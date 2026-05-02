@echo off
echo ========================================
echo    BookRecommender JavaDoc Generator
echo ========================================
echo.

REM Create javadoc directory if it doesn't exist
if not exist "javadoc" mkdir javadoc

echo Generating JavaDoc documentation...
echo.

REM Generate JavaDoc for all Java files in src/bookrecommender
javadoc -d javadoc -sourcepath src -subpackages bookrecommender -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -windowtitle "BookRecommender API Documentation" -doctitle "BookRecommender - Sistema di Raccomandazione Libri" -header "<b>BookRecommender</b>" -footer "<i>Documentazione generata automaticamente</i>" -author -version -private

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo    JavaDoc generato con successo!
    echo ========================================
    echo.
    echo La documentazione e' disponibile in:
    echo   %CD%\javadoc\index.html
    echo.
    echo Per visualizzare la documentazione:
    echo   start javadoc\index.html
    echo.
) else (
    echo.
    echo ========================================
    echo    ERRORE nella generazione JavaDoc
    echo ========================================
    echo.
    echo Verifica che:
    echo 1. Java sia installato e configurato
    echo 2. I file sorgente siano presenti in src/
    echo 3. Non ci siano errori di sintassi nei file Java
    echo.
)

pause 