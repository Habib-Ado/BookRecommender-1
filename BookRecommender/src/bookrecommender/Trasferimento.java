package bookrecommender;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 * 
 * Classe per il trasferimento dei dati da un file CSV al database PostgreSQL.
 * Legge i dati da un file CSV contenente informazioni sui libri e li inserisce
 * nella tabella corrispondente nel database.
 * @author MOUHAMMAD TOURE
 */
public class Trasferimento {

    // Percorso del file CSV di input
    private static final String FILE_CSV = "./dati/BooksDatasetClean.csv";
    
    // Dettagli di connessione al database PostgreSQL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bookrecommender";
    private static final String DB_USER = "postgres";
    private static String DB_PASSWORD = "1234";
    
    // Connessione al database
    private static Connection conn = null;
    
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
    
    /**
     * Metodo principale che esegue il trasferimento dei dati.
     * Prende la password del database dall'input dell'utente, 
     * stabilisce una connessione al database,
     * legge il file CSV e inserisce i dati nella tabella "libri".
     * 
     * @param args Argomenti da riga di comando (non utilizzati)
     * @author Mouhammad Toure
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // Richiede all'utente la password del database
        System.out.print("Inserire la password del database PostgreSQL: ");
        DB_PASSWORD = scan.nextLine();
        
        // Verifica l'esistenza del file CSV
        File file = new File(FILE_CSV);

        if (!file.exists()) {
            System.err.println("File non trovato: " + FILE_CSV);
            scan.close();
            return;
        }

        try {
            // Stabilisce la connessione al database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            System.out.println("Connessione al database stabilita con successo.");
            
            // Verifica se il database è già popolato
            if (!isDatabaseEmpty()) {
                System.out.println("Il database contiene già dei libri.");
                System.out.println("Popolamento saltato per evitare duplicazioni e perdita tempo.");
                scan.close();
                return;
            }
            
            System.out.println("Database vuoto - Avvio popolamento con i libri...");
            
            // Prepara la query SQL per l'inserimento
            String sql = "INSERT INTO libri (titolo, autore, genere, editore, anno) VALUES (?, ?, ?, ?, ?)";
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_CSV), "UTF-8"));
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
                
                System.out.println("Trasferimento completato con successo. Inseriti " + count + " libri nel database.");
                                                                    //  66313 libri inseriti nel database

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
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
            scan.close();
        }
    }

    /**
     * Rimuove i caratteri speciali da una stringa, mantenendo solo lettere, numeri e spazi.
     * 
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