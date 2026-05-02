# PowerShell script for BookRecommender Database Setup

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "BookRecommender Database Setup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Java is installed
try {
    $javaVersion = java -version 2>&1
    if ($LASTEXITCODE -ne 0) {
        throw "Java not found"
    }
    Write-Host "✅ Java found: $($javaVersion | Select-Object -First 1)" -ForegroundColor Green
} catch {
    Write-Host "❌ ERROR: Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java 17 or higher" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Check if PostgreSQL driver is available
$postgresJar = Get-ChildItem "lib\postgresql-*.jar" -ErrorAction SilentlyContinue
if (-not $postgresJar) {
    Write-Host "⚠️ WARNING: PostgreSQL JDBC driver not found in lib/ directory" -ForegroundColor Yellow
    Write-Host "Please download postgresql-*.jar and place it in the lib/ directory" -ForegroundColor Yellow
    Write-Host "Download from: https://jdbc.postgresql.org/download/" -ForegroundColor Yellow
    Write-Host ""
}

# Compile the project
Write-Host "🔨 Compiling project..." -ForegroundColor Yellow
try {
    javac -d bin -cp "lib/*" src/bookrecommender/*.java
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Compilation successful!" -ForegroundColor Green
    } else {
        throw "Compilation failed"
    }
} catch {
    Write-Host "❌ ERROR: Compilation failed" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Database Initialization Options:" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "1. Initialize database only" -ForegroundColor White
Write-Host "2. Initialize database with sample data" -ForegroundColor White
Write-Host "3. Test database connection" -ForegroundColor White
Write-Host "4. Show database information" -ForegroundColor White
Write-Host "5. Exit" -ForegroundColor White
Write-Host ""
Write-Host "NOTE: You will be prompted for the database password" -ForegroundColor Yellow
Write-Host ""

$choice = Read-Host "Enter your choice (1-5)"

switch ($choice) {
    "1" {
        Write-Host ""
        Write-Host "🔧 Initializing database..." -ForegroundColor Yellow
        Write-Host "You will be prompted for the PostgreSQL password" -ForegroundColor Yellow
        java -cp "bin;lib/*" bookrecommender.DatabaseInitializer
    }
    "2" {
        Write-Host ""
        Write-Host "🔧 Initializing database with sample data..." -ForegroundColor Yellow
        Write-Host "You will be prompted for the PostgreSQL password" -ForegroundColor Yellow
        java -cp "bin;lib/*" bookrecommender.DatabaseInitializer --with-sample-data
    }
    "3" {
        Write-Host ""
        Write-Host "🔍 Testing database connection..." -ForegroundColor Yellow
        Write-Host "You will be prompted for the PostgreSQL password" -ForegroundColor Yellow
        java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.testConnection()"
    }
    "4" {
        Write-Host ""
        Write-Host "📊 Showing database information..." -ForegroundColor Yellow
        Write-Host "You will be prompted for the PostgreSQL password" -ForegroundColor Yellow
        java -cp "bin;lib/*" -e "bookrecommender.DatabaseInitializer.showDatabaseInfo()"
    }
    "5" {
        Write-Host "👋 Exiting..." -ForegroundColor Green
        exit 0
    }
    default {
        Write-Host "❌ Invalid choice. Please enter a number between 1 and 5." -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Setup completed!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Start PostgreSQL service" -ForegroundColor White
Write-Host "2. Run: start rmiregistry 1099" -ForegroundColor White
Write-Host "3. Run: java -cp 'bin;lib/*' bookrecommender.Server" -ForegroundColor White
Write-Host "4. Run: java -cp 'bin;lib/*' bookrecommender.Client" -ForegroundColor White
Write-Host ""
Write-Host "NOTE: The server will also prompt for the database password" -ForegroundColor Yellow
Write-Host ""

Read-Host "Press Enter to exit" 