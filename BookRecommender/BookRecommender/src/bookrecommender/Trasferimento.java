package bookrecommender;

import java.io.Serializable;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

/**
 * Classe per il trasferimento dei dati da un file CSV al database PostgreSQL.
 * Legge i dati da un file CSV contenente informazioni sui libri e li inserisce
 * nella tabella corrispondente nel database.
 * 
 *  PROGETTO REALIZZATO DA:
 * 
 *  MOUHAMMAD TOURE                      
 */
public class Trasferimento implements Serializable {
    private static final long serialVersionUID = 1L;

    // Nome e cartella del file CSV di input
    private static final String CSV_DIR = "dati";
    private static final String CSV_FILE_NAME = "BooksDatasetClean.csv";
    
    // Dettagli di connessione al database PostgreSQL
    private static String DB_URL; // Il database URL sarà costruito dinamicamente in base alla password inserita
    private static String DB_USER;
    private static String DB_PASSWORD;

    private static File getCsvFile() {
        File current = new File(System.getProperty("user.dir"));
        File candidate = new File(current, CSV_DIR + File.separator + CSV_FILE_NAME);
        if (candidate.exists()) {
            return candidate;
        }

        try {
            File codeLocation = new File(Trasferimento.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File codeRoot = codeLocation.isFile() ? codeLocation.getParentFile() : codeLocation;
            File projectRoot = codeRoot.getName().equals("bin") ? codeRoot.getParentFile() : codeRoot;
            File alternate = new File(projectRoot, CSV_DIR + File.separator + CSV_FILE_NAME);
            if (alternate.exists()) {
                return alternate;
            }
        } catch (Exception ignored) {
            // Ignora errori di risoluzione del percorso; useremo il percorso di default
        }

        return candidate;
    }
    
    // Connessione al database
    private static Connection conn = null;

    /**
     * Verifica se il database esiste.
     * @return true se il database esiste, false altrimenti
     */
    private static boolean databaseExists() {
        try (Connection tempConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            return tempConn != null;
        } catch (SQLException e) {
            System.out.println("Errore: Database non esiste");
            return false;
        }
    }
    
    /**
     * Verifica se la tabella libri è vuota (non contiene dati).
     * @return true se la tabella è vuota, false altrimenti
     */
    private static boolean isDatabaseEmpty() {

        try (Connection tempConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = tempConn.prepareStatement("SELECT COUNT(*) FROM libri");
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("🔍 Numero di libri nel database: " + count);
                return count == 0;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("⚠️ Errore durante la verifica del database: " + e.getMessage());
            return true; // Se c'è un errore, assumiamo che sia vuoto
        }
    }

    public void compilaDatabase(String dbUrl, String dbUser, String dbPassword) {

        DB_URL = dbUrl;
        DB_USER = dbUser;
        DB_PASSWORD = dbPassword;

        // Verifica l'esistenza del file CSV
        File file = getCsvFile();
        String filePath = file.getAbsolutePath();

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            System.err.println("Percorso cercato: " + System.getProperty("user.dir") + File.separator + CSV_DIR + File.separator + CSV_FILE_NAME);
            return;
        }

        try {
            // Verifica se il database esiste
            if (!databaseExists()) {
                System.out.println("Errore: Il database 'bookrecommender' non esiste");
                System.out.println("Eseguire il file setup_database.bat per creare e inizializzare il database");
                return;
            }

            // Stabilisce la connessione al database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            System.out.println("Connessione al database stabilita con successo.");
            
            // Verifica se il database è già popolato
            if (!isDatabaseEmpty()) {
                System.out.println("Il database contiene già dei libri.");
                System.out.println("Popolamento saltato per evitare duplicazioni e perdita tempo.");
                return;
            }
            
            System.out.println("Database vuoto - Avvio popolamento con i libri...");
            
            // Prepara la query SQL per l'inserimento
            String sql = "INSERT INTO libri (titolo, autore, genere, editore, anno) VALUES (?, ?, ?, ?, ?)";
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Ignora la prima riga (intestazione del CSV)
                br.readLine(); 
                
                String line;
                int count = 0;
                
                // Legge ogni riga del file CSV
                while ((line = br.readLine()) != null) {
                    // Divide la riga in campi, gestendo correttamente le virgole dentro i valori quotati
                    String[] campi = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    // Verifica che ci siano abbastanza campi
                    if (campi.length < 8) {
                        System.err.println("Riga ignorata: numero di campi insufficiente");
                        continue;
                    }
                    String titolo = rimuoviCaratteriSpeciali(campi[0]).trim();
                    // Combina nome e cognome dell'autore e rimuove i caratteri speciali
                    String autoreCompleto = campi[1].trim() + " " + campi[2];
                    String autore = rimuoviCaratteriSpeciali(autoreCompleto).substring(3).trim();
                    String genere = rimuoviCaratteriSpeciali(campi[3]).trim();
                    String editore = campi[4].trim();
                    String annoPubblicato = campi[7].trim();
                    try {
                        // Imposta i parametri della query
                        pstmt.setString(1, titolo);
                        pstmt.setString(2, autore);
                        pstmt.setString(3, genere);
                        pstmt.setString(4, editore);
                        pstmt.setInt(5, Integer.parseInt(annoPubblicato));
                        
                        // Esegue l'inserimento
                        pstmt.executeUpdate();
                        count++;
                    } catch (SQLException | NumberFormatException e) {
                        System.err.println("Errore durante l'inserimento del libro: " + titolo);
                        e.printStackTrace();
                    }
                }
                System.out.println("Popolamento completato con successo. Inseriti " + count + " libri.");
                                                            
            } catch (IOException e) {
                System.err.println("Errore durante l'elaborazione del file CSV: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Chiude la connessione al database
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connessione al database chiusa.");
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }

    }

    /**
     * Rimuove i caratteri speciali da una stringa, mantenendo solo lettere, numeri e spazi.
     * @author Mouhammad Toure
     * @param input La stringa da pulire
     * @return La stringa senza caratteri speciali
     */
    public static String rimuoviCaratteriSpeciali(String input) {    
        // Espressione regolare per identificare caratteri non alfanumerici e non spazi
        final String regex = "[^a-zA-Z0-9\\s]";
        
        // Sostituisce tutti i caratteri speciali con una stringa vuota
        return input.replaceAll(regex, "");
    }
}