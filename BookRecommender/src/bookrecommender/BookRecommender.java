package bookrecommender;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Scanner;


/**
 * Sistema di raccomandazione di libri che permette agli utenti di cercare,
 * visualizzare,
 * registrarsi, effettuare login, creare librerie personali, aggiungere
 * valutazioni e fornire consigli sui libri.
 *
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Marcel Precieux Moukoko      -Matricola:     759674      -Sede: VA
 *
 * Classe principale per il sistema di raccomandazione di libri.
 * Gestisce l'interazione con l'utente e le operazioni sul database.
 */
public class BookRecommender {
    // Dettagli di connessione al database PostgreSQL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bookrecommender";
    private static final String DB_USER = "postgres";
    private static String DB_PASSWORD; // Password verrà impostata dal client

    // Stato dell'applicazione
    private static ConcurrentHashMap<String, UserID> userSessions = new ConcurrentHashMap<>(); // Sessioni utente per client
    private static Connection connection = null; // Connessione al database

    /**
     * Inizializza la password del database richiedendola all'utente.
     * @return true se la password è stata impostata correttamente, false altrimenti
     */
    public static synchronized boolean initializeDatabasePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BookRecommender Database Setup ===");
        System.out.println("Inserisci la password del database PostgreSQL:");
        System.out.print("Password: ");
        
        String password = scanner.nextLine();
        
        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ ERRORE: Password non può essere vuota!");
            scanner.close();
            return false;
        }
        
        // Test della connessione con la password inserita
        try {
            Connection testConnection = DriverManager.getConnection(DB_URL, DB_USER, password);
            testConnection.close();
            DB_PASSWORD = password;
            System.out.println("✅ Password del database impostata correttamente!");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ ERRORE: Impossibile connettersi al database con la password inserita!");
            System.out.println("Messaggio: " + e.getMessage());
            System.out.println("Verifica che:");
            System.out.println("1. PostgreSQL sia in esecuzione");
            System.out.println("2. La password sia corretta");
            System.out.println("3. Il database 'bookrecommender' esista");
            scanner.close();
            return false;
        }finally {
            scanner.close();
        }
    }

    /**
     * Verifica se la password del database è stata impostata.
     * @return true se la password è impostata, false altrimenti
     */
    public static synchronized boolean isDatabasePasswordSet() {
        return DB_PASSWORD != null && !DB_PASSWORD.trim().isEmpty();
    }

         /**
         * Restituisce la lista di tutti i libri disponibili.
         * @return Stringa formattata con la lista dei libri
         */
        public synchronized String visualizzareLibri() {
        // Verifica che la password sia impostata
        if (!isDatabasePasswordSet()) {
            return "ERRORE: Password del database non impostata. Riavvia il server.";
        }
        
            StringBuilder result = new StringBuilder();
            try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM libri")) {
                ResultSet rs = stmt.executeQuery();
    
                result.append("LIBRI DEL SISTEMA:\n");
                boolean trovato = false;
                while (rs.next()) {
                    String titolo = rs.getString("titolo");
                    result.append("Titolo: ").append(titolo).append("\n");
                    result.append("Autore: ").append(rs.getString("autore")).append("\n");
                    result.append("Genere: ").append(rs.getString("genere")).append("\n");
                    result.append("Editore: ").append(rs.getString("editore")).append("\n");
                    result.append("Anno: ").append(rs.getInt("anno")).append("\n");
    
                    // Aggiungi le valutazioni del libro
                    result.append(mostrareValutazioni(titolo)).append("\n");
    
                    // Aggiungi i consigli per il libro
                    result.append(mostraConsigli(titolo)).append("\n");
    
                    result.append("-------------------------------------------------\n");
                    trovato = true;
                }
    
                if (!trovato) {
                    result.append("Nessun libro disponibile.");
                }
            } catch (SQLException e) {
                result.append("ERRORE: Errore durante il recupero dei libri: ").append(e.getMessage());
            }
            return result.toString();
        }

    /**
     * Cerca un libro per titolo.
     * @param title Titolo del libro da cercare
     * @return Informazioni sul libro trovato o messaggio di errore
     */
    public synchronized String cercaLibroConTitolo(String title) {
        title = title.toLowerCase();
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM libri WHERE titolo = ?")) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.append("Libro trovato:\n");
                result.append("Titolo: ").append(rs.getString("titolo")).append("\n");
                result.append("Autore: ").append(rs.getString("autore")).append("\n");
                result.append("Genere: ").append(rs.getString("genere")).append("\n");
                result.append("Casa editrice: ").append(rs.getString("editore")).append("\n");
                result.append("Anno: ").append(rs.getInt("anno")).append("\n");
                result.append(mostrareValutazioni(rs.getString("titolo"))).append("\n");
                result.append(mostraConsigli(rs.getString("titolo"))).append("\n");
                result.append("-------------------------------------------------\n");
            } else {
                return "Libro non trovato!";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante la ricerca del libro: " + e.getMessage();
        }
        return result.toString();
    }

    /**
     * Cerca libri per autore.
     * @param author Autore dei libri da cercare
     * @return Lista di libri dell'autore o messaggio di errore
     */
    public synchronized String cercaLibroConAutore(String author) {
        StringBuilder result = new StringBuilder();
    
        String sql = "SELECT * FROM libri WHERE autore = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, author);
            ResultSet rs = stmt.executeQuery();
    
            boolean trovato = false;
            
            while (rs.next()) {
                trovato = true;
    
                String titolo = rs.getString("titolo");
    
                result.append("\nLibro trovato:\n");
                result.append("Titolo: ").append(titolo).append("\n");
                result.append("Autore: ").append(rs.getString("autore")).append("\n");
                result.append("Genere: ").append(rs.getString("genere")).append("\n");
                result.append("Casa editrice: ").append(rs.getString("editore")).append("\n");
                result.append("Anno: ").append(rs.getInt("anno")).append("\n");
                result.append(mostrareValutazioni(titolo)).append("\n");
                result.append(mostraConsigli(titolo)).append("\n");
                result.append("-------------------------------------------------\n");
            }
    
            if (!trovato) {
                return "Autore non trovato!";
            }
    
        } catch (SQLException e) {
            return "ERRORE: Errore durante la ricerca del libro: " + e.getMessage();
        }
    
        return result.toString();
    }
    

    /**
     * Cerca libri per autore e anno di pubblicazione.
     * @param author Autore dei libri
     * @param year Anno di pubblicazione
     * @return Lista di libri corrispondenti o messaggio di errore
     */
    public synchronized String cercaLibroConAutoreAnno(String author, int year) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM libri WHERE autore = ? AND anno = ?")) {
            stmt.setString(1, author);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            boolean trovato = false;
            while (rs.next()) {
                result.append("\nLibro trovato:\n");
                result.append("Titolo: ").append(rs.getString("titolo")).append("\n");
                result.append("Autore: ").append(rs.getString("autore")).append("\n");
                result.append("Genere: ").append(rs.getString("genere")).append("\n");
                result.append("Casa editrice: ").append(rs.getString("editore")).append("\n");
                result.append("Anno: ").append(rs.getInt("anno")).append("\n");
                result.append(mostrareValutazioni(rs.getString("titolo"))).append("\n");
                result.append(mostraConsigli(rs.getString("titolo"))).append("\n");
                result.append("-------------------------------------------------\n");
                trovato = true;
            }

            if (!trovato) {
                return "Libro non trovato!";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante la ricerca del libro: " + e.getMessage();
        }
        return result.toString();
    }

    /**
     * Gestisce la registrazione di un nuovo utente.
     * @param sessionId ID della sessione client
     * @param name Nome e cognome
     * @param cf Codice fiscale
     * @param email Email
     * @param userid UserID
     * @param password Password
     * @return Messaggio di conferma o errore
     */
    public synchronized String registrazione(String sessionId, String name, String cf, String email, String userid, String password) {
        if (userSessions.containsKey(sessionId)) {
            return "ERRORE: Sei già loggato. Devi effettuare il logout per registrarti.";
        }

        if (!verificaCodiceFiscale(cf)) {
            return "ERRORE: Codice fiscale non valido.";
        }

        if (!verificaEmail(email)) {
            return "ERRORE: Email non valida.";
        }

        if (verificaEmailEsistente(email)) {
            return "ERRORE: Email già in uso.";
        }

        if (verificaUserIDEsistente(userid)) {
            return "ERRORE: UserID già in uso.";
        }

        if (!verificaPassword(password)) {
            return "ERRORE: Password non valida. Deve contenere almeno 8 caratteri, una lettera e un numero.";
        }

        // Hash della password
        password = hashedPassword(password);

        // Inserimento nel database
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO userid (nome_cognome, codice_fiscale, email, userid, password) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, cf);
            stmt.setString(3, email);
            stmt.setString(4, userid);
            stmt.setString(5, password);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                UserID newUser = new UserID(name, cf, email, userid, password);
                userSessions.put(sessionId, newUser);
                return "OK: Registrazione effettuata con successo.";
            } else {
                return "ERRORE: Errore durante la registrazione.";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante la registrazione: " + e.getMessage();
        }
    }

    /**
     * Gestisce il login dell'utente.
     * @param sessionId ID della sessione client
     * @param username UserID
     * @param password Password
     * @return Messaggio di conferma o errore
     */
    public synchronized String login(String sessionId, String username, String password) {
        if (userSessions.containsKey(sessionId)) {
            return "ERRORE: Sei già loggato.";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM userid WHERE userid = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword(password));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserID user = new UserID(
                        rs.getString("nome_cognome"),
                        rs.getString("codice_fiscale"),
                        rs.getString("email"),
                        rs.getString("userid"),
                        rs.getString("password"));
                userSessions.put(sessionId, user);
                return "OK: Login effettuato con successo. Session ID: " + sessionId;
            } else {
                return "ERRORE: UserID o password non validi.";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante il login: " + e.getMessage();
        }
    }

    /**
     * Gestisce la creazione di una libreria.
     * @param sessionId ID della sessione client
     * @param nomeLibreria Nome della libreria da creare
     * @return Messaggio di conferma o errore
     */
    public synchronized String creareLibreria(String sessionId, String nomeLibreria) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per creare una libreria.";
        }
    
        // Controllo se la libreria esiste già per quell'utente
        try (PreparedStatement checkStmt = getConnection().prepareStatement(
                "SELECT 1 FROM librerie WHERE userid = ? AND nome_libreria = ?")) {
            checkStmt.setString(1, user.getUserID());
            checkStmt.setString(2, nomeLibreria);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return "ERRORE: Esiste già una libreria con questo nome per il tuo utente.";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante il controllo dell'esistenza della libreria: " + e.getMessage();
        }
    
        // Inserimento della nuova libreria con valore NULL per libro
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO librerie (userid, nome_libreria, libro) VALUES (?, ?, NULL)")) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, nomeLibreria);
            stmt.executeUpdate();
            return "OK: Libreria \"" + nomeLibreria + "\" creata con successo.";
        } catch (SQLException e) {
            return "ERRORE: Errore durante la creazione della libreria: " + e.getMessage();
        }
    }

    /**
     * Gestisce la rimozione di un libro dalla libreria.
     * @param sessionId ID della sessione client
     * @param nomeLibreria Nome della libreria
     * @param titoloLibro Titolo del libro da rimuovere
     * @return Messaggio di conferma o errore
     */
    public synchronized String rimuoviLibroLibreria(String sessionId, String nomeLibreria, String titoloLibro) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per rimuovere un libro.";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM librerie WHERE userid = ? AND nome_libreria = ? AND libro = ? AND libro IS NOT NULL")) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, nomeLibreria);
            stmt.setString(3, titoloLibro);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "OK: Libro rimosso con successo dalla tua libreria!";
            } else {
                return "ERRORE: Libro non trovato nella tua libreria.";
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante la rimozione del libro: " + e.getMessage();
        }
    }

    /**
     * Gestisce l'inserimento di una valutazione per un libro.
     * @param sessionId ID della sessione client
     * @param title Titolo del libro
     * @param style Valutazione stile (1-5)
     * @param content Valutazione contenuto (1-5)
     * @param pleasantness Valutazione gradevolezza (1-5)
     * @param originality Valutazione originalità (1-5)
     * @param edition Valutazione edizione (1-5)
     * @return Messaggio di conferma o errore
     */
    public synchronized String inserisciValutazioneLibro(String sessionId, String title, String style, String content, 
                                           String pleasantness, String originality, String edition) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per valutare un libro.";
        }

        try {
            int stile = Integer.parseInt(style);
            int contenuto = Integer.parseInt(content);
            int gradevolezza = Integer.parseInt(pleasantness);
            int originalita = Integer.parseInt(originality);
            int edizione = Integer.parseInt(edition);

            if (stile < 1 || stile > 5 || contenuto < 1 || contenuto > 5 ||
                gradevolezza < 1 || gradevolezza > 5 || originalita < 1 || originalita > 5 ||
                edizione < 1 || edizione > 5) {
                return "ERRORE: Le valutazioni devono essere tra 1 e 5.";
            }

            // Calcola il voto finale (media delle valutazioni)
            double votoFinale = (stile + contenuto + gradevolezza + originalita + edizione) / 5.0;

            try (PreparedStatement stmt = getConnection().prepareStatement(
                    "INSERT INTO valutazioni (userid, titolo_libro, stile, contenuto, gradevolezza, originalita, edizione, voto_finale) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, user.getUserID());
                stmt.setString(2, title);
                stmt.setInt(3, stile);
                stmt.setInt(4, contenuto);
                stmt.setInt(5, gradevolezza);
                stmt.setInt(6, originalita);
                stmt.setInt(7, edizione);
                stmt.setDouble(8, votoFinale);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    return "OK: Valutazione inserita con successo.";
                } else {
                    return "ERRORE: Errore durante l'inserimento della valutazione.";
                }
            }
        } catch (NumberFormatException e) {
            return "ERRORE: Le valutazioni devono essere numeri interi tra 1 e 5.";
        } catch (SQLException e) {
            System.err.println("Errore SQL: " + e.getMessage());
            return "ERRORE: Libro gia valutato o dati non validi.";
        }
    }

    /**
     * Gestisce l'inserimento di un consiglio per un libro.
     * @param sessionId ID della sessione client
     * @param referenceBook Libro di riferimento
     * @param recommendedBooks Libri raccomandati (separati da virgola)
     * @return Messaggio di conferma o errore
     */
    public synchronized String inserisciConsiglioLibro(String sessionId, String referenceBook, String recommendedBooks) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per consigliare un libro.";
        }

        if (!verificaLibro(referenceBook)) {
            return "ERRORE: Libro referenziale non trovato.";
        }

        String[] libriConsigliati = recommendedBooks.split(",");
        StringBuilder result = new StringBuilder();

        // Conta quanti consigli ha già dato l'utente per questo libro
        int count = 0;
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT COUNT(*) FROM consigli WHERE userid = ? AND libro_referenziale = ?")) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, referenceBook);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante il conteggio dei consigli: " + e.getMessage();
        }

        for (String libroConsigliato : libriConsigliati) {
            libroConsigliato = libroConsigliato.trim();
            if (count >= 3) {
                result.append("Hai già consigliato tre libri per questo libro referenziale. ").append(referenceBook).append("\n");
                break;
            }

            if (verificaLibro(libroConsigliato)) {
                try (PreparedStatement stmt = getConnection().prepareStatement(
                        "INSERT INTO consigli (userid, libro_referenziale, libro_consigliato) VALUES (?, ?, ?)")) {
                    stmt.setString(1, user.getUserID());
                    stmt.setString(2, referenceBook);
                    stmt.setString(3, libroConsigliato);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        result.append("OK: Libro consigliato '").append(libroConsigliato).append("' aggiunto con successo.\n");
                        count++;
                    } else {
                        result.append("ERRORE: Errore durante l'aggiunta del consiglio per '").append(libroConsigliato).append("'.\n");
                    }
                } catch (SQLException e) {
                    result.append("ERRORE: Errore durante l'aggiunta del consiglio per '").append(libroConsigliato).append("': ").append(e.getMessage()).append("\n");
                }
            } else {
                result.append("ERRORE: Libro '").append(libroConsigliato).append("' non trovato.\n");
            }
        }

        return result.toString();
    }

    /**
     * Gestisce il logout dell'utente.
     * @param sessionId ID della sessione
     * @return Messaggio di conferma o errore
     */
    public synchronized String logout(String sessionId) {
        if (!userSessions.containsKey(sessionId)) {
            return "ERRORE: Nessun utente loggato in questa sessione.";
        }
        userSessions.remove(sessionId);
        return "OK: Logout effettuato con successo.";
    }

    /**
     * Mostra le valutazioni di un libro.
     * @param titolo Titolo del libro
     * @return Stringa formattata con le valutazioni
     */
    private String mostrareValutazioni(String titolo) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT AVG(stile) as avg_stile, AVG(contenuto) as avg_contenuto, " +
                "AVG(gradevolezza) as avg_gradevolezza, AVG(originalita) as avg_originalita, " +
                "AVG(edizione) as avg_edizione, AVG(voto_finale) as avg_voto_finale, " +
                "COUNT(*) as num_valutazioni FROM valutazioni WHERE titolo_libro = ?")) {
            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();

            result.append("Valutazioni del libro: ").append(titolo).append("\n");
            if (rs.next() && rs.getInt("num_valutazioni") > 0) {
              //  result.append("Valutazioni del libro: ").append(titolo).append("\n");
                result.append("Numero di valutazioni: ").append(rs.getInt("num_valutazioni")).append("\n");
                result.append("Media dello stile: ").append(String.format("%.2f", rs.getDouble("avg_stile"))).append("\n");
                result.append("Media del contenuto: ").append(String.format("%.2f", rs.getDouble("avg_contenuto"))).append("\n");
                result.append("Media della gradevolezza: ").append(String.format("%.2f", rs.getDouble("avg_gradevolezza"))).append("\n");
                result.append("Media dell'originalità: ").append(String.format("%.2f", rs.getDouble("avg_originalita"))).append("\n");
                result.append("Media dell'edizione: ").append(String.format("%.2f", rs.getDouble("avg_edizione"))).append("\n");
                result.append("Media del voto finale: ").append(String.format("%.2f", rs.getDouble("avg_voto_finale"))).append("\n");
            } else {
                result.append("Nessuna valutazione trovata per questo libro.\n");
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante il recupero delle valutazioni: " + e.getMessage();
        }
        return result.toString();
    }

    /**
     * Mostra i consigli per un libro.
     * @param titolo Titolo del libro
     * @return Stringa formattata con i consigli
     */
    private String mostraConsigli(String titolo) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT libro_consigliato FROM consigli WHERE libro_referenziale = ?")) {
            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();

            boolean trovato = false;
            result.append("Consigli per il libro: '").append(titolo).append("'\n");
            result.append("Consigliati: ");
            while (rs.next()) {
                result.append(rs.getString("libro_consigliato")).append(", ");
                trovato = true;
            }

            if (!trovato) {
                result.append("Nessun consiglio trovato per questo libro\n");
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante il recupero dei consigli: " + e.getMessage();
        }
        return result.toString();
    }

    /**
     * Verifica la validità di un codice fiscale italiano.
     * @param codice Codice fiscale da validare
     * @return true se valido, false altrimenti
     */
    public static synchronized boolean verificaCodiceFiscale(String codice) {
        if (codice == null || codice.length() != 16) {
            return false;
        }
        return codice.matches("^[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}$");
    }

    /**
     * Verifica la validità di una password.
     * Requisiti: almeno 8 caratteri, almeno una lettera e un numero.
     * @param password Password da validare
     * @return true se valida, false altrimenti
     */
    public static synchronized boolean verificaPassword(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*");
    }

    /**
     * Verifica la validità di un indirizzo email.
     * @param email Email da validare
     * @return true se valida, false altrimenti
     */
    public static synchronized boolean verificaEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Verifica se l'email è già in uso nel database.
     * @param email Email da verificare
     * @return true se l'email esiste già, false altrimenti
     */
    private synchronized boolean verificaEmailEsistente(String email) {
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT email FROM userid WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica dello email: " + e.getMessage());
            return true;
        }
    }

    /**
     * Verifica se un UserID è già in uso nel database.
     * @param userID UserID da verificare
     * @return true se l'UserID esiste già, false altrimenti
     */
    private synchronized boolean verificaUserIDEsistente(String userID) {
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT userid FROM userid WHERE userid = ?")) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica dello userID: " + e.getMessage());
            return true;
        }
    }

    /**
     * Verifica se un libro esiste nel database.
     * @param titoloLibro Titolo del libro da verificare
     * @return true se il libro esiste, false altrimenti
     */
    private synchronized boolean verificaLibro(String titoloLibro) {
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT titolo FROM libri WHERE titolo = ?")) {
            stmt.setString(1, titoloLibro);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica del libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Genera un ID di sessione univoco.
     * @return Stringa con l'ID di sessione
     */
    private synchronized String generateSessionId() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    /**
     * Genera l'hash di una password.
     * @param password Password in chiaro
     * @return Password hashata
     */
    private synchronized String hashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo hash non disponibile: " + e.getMessage());
            return String.valueOf(password.hashCode());
        }
    }

    /**
     * Ottiene la connessione al database, creandola se necessario.
     * @return Oggetto Connection
     * @throws SQLException Se c'è un errore di connessione
     */
    private synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public synchronized String aggiungiLibroLibreria(String sessionId, String nomeLibreria, String titoloLibro) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per aggiungere un libro.";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO librerie (userid, nome_libreria, libro) VALUES (?, ?, ?)")) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, nomeLibreria);
            stmt.setString(3, titoloLibro);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "OK: Libro aggiunto con successo alla libreria!";
            } else {
                return "ERRORE: Errore durante l'aggiunta del libro.";
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante l'inserimento del libro in libreria: " + e.getMessage());
            return "ERRORE: Libro non presente nel catalogo dei libri. Controlla i dati inseriti.";
        }
    }
    

    public synchronized String visualizzaLibreria(String sessionId, String nomeLibreria) {
        UserID user = getUserFromSession(sessionId);
        if (user == null) {
            return "ERRORE: Devi essere loggato per visualizzare la libreria.";
        }
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT libro FROM librerie WHERE userid = ? AND nome_libreria = ? AND libro IS NOT NULL")) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, nomeLibreria);
            ResultSet rs = stmt.executeQuery();
            result.append("Libri nella libreria '").append(nomeLibreria).append("':\n");
            boolean trovato = false;
            while (rs.next()) {
                String libro = rs.getString("libro");
                if (libro != null) {
                    result.append("- ").append(libro).append("\n");
                    trovato = true;
                }
            }
            if (!trovato) {
                result.append("Nessun libro presente in questa libreria.");
            }
        } catch (SQLException e) {
            return "ERRORE: Errore durante la visualizzazione della libreria: " + (e.getMessage() != null ? e.getMessage() : "Errore sconosciuto");
        }
        return result.toString();
    }

    public String tornaMenuPrincipale() throws RemoteException{
        return "OK: Tornato al menu principale";
    }

    /**
     * Recupera la password di un utente tramite il suo username.
     * @param username L'username dell'utente
     * @return Stringa con il risultato dell'operazione
     */
    public synchronized String recuperaPassword(String username) {
        // Verifica che la password sia impostata
        if (!isDatabasePasswordSet()) {
            return "ERRORE: Password del database non impostata. Riavvia il server.";
        }
        
        if (username == null || username.trim().isEmpty()) {
            return "ERRORE: Username non può essere vuoto!";
        }
        
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT nome_cognome, email, password FROM userid WHERE userid = ?")) {
            
            stmt.setString(1, username.trim());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nomeCognome = rs.getString("nome_cognome");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password");
                
                StringBuilder result = new StringBuilder();
                result.append("✅ UTENTE TROVATO\n");
                result.append("Nome e Cognome: ").append(nomeCognome).append("\n");
                result.append("Username: ").append(username).append("\n");
                result.append("Email: ").append(email).append("\n");
                result.append("\n📝 OPZIONI DISPONIBILI:\n");
                result.append("1. Visualizza informazioni utente (password hash)\n");
                result.append("2. Genera nuova password temporanea\n");
                result.append("3. Cambia password (richiede login)\n");
                result.append("\n🔒 SICUREZZA:\n");
                result.append("- Per motivi di sicurezza, non è possibile recuperare la password originale\n");
                result.append("- È possibile generare una nuova password temporanea\n");
                result.append("- Dopo aver generato una password temporanea, cambiala al prossimo login\n");
                
                return result.toString();
            } else {
                return "❌ ERRORE: Utente con username '" + username + "' non trovato nel database.";
            }
            
        } catch (SQLException e) {
            return "❌ ERRORE: Impossibile recuperare i dati dell'utente. Errore: " + e.getMessage();
        }
    }

    /**
     * Genera una nuova password temporanea per un utente.
     * @param username L'username dell'utente
     * @return Stringa con la nuova password temporanea
     */
    public synchronized String generaPasswordTemporanea(String username) {
        // Verifica che la password sia impostata
        if (!isDatabasePasswordSet()) {
            return "ERRORE: Password del database non impostata. Riavvia il server.";
        }
        
        if (username == null || username.trim().isEmpty()) {
            return "ERRORE: Username non può essere vuoto!";
        }
        
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT nome_cognome, email FROM userid WHERE userid = ?")) {
            
            stmt.setString(1, username.trim());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nomeCognome = rs.getString("nome_cognome");
                String email = rs.getString("email");
                
                // Genera una password temporanea
                String tempPassword = generaPasswordCasuale();
                String tempPasswordHash = hashedPassword(tempPassword);
                
                // Aggiorna la password nel database
                try (PreparedStatement updateStmt = getConnection().prepareStatement(
                        "UPDATE userid SET password = ? WHERE userid = ?")) {
                    
                    updateStmt.setString(1, tempPasswordHash);
                    updateStmt.setString(2, username.trim());
                    updateStmt.executeUpdate();
                    
                    StringBuilder result = new StringBuilder();
                    result.append("✅ PASSWORD TEMPORANEA GENERATA\n");
                    result.append("Utente: ").append(nomeCognome).append(" (").append(username).append(")\n");
                    result.append("Email: ").append(email).append("\n");
                    result.append("Nuova password temporanea: ").append(tempPassword).append("\n");
                    result.append("\n⚠️  IMPORTANTE:\n");
                    result.append("- Cambia questa password al prossimo login\n");
                    result.append("- Vai su rescuro password e segui le istruzioni\n");
                    result.append("- La password è valida solo per questa sessione\n");
                    result.append("- Conserva questa password in modo sicuro\n");
                    
                    return result.toString();
                }
                
            } else {
                return "❌ ERRORE: Utente con username '" + username + "' non trovato nel database.";
            }
            
        } catch (SQLException e) {
            return "❌ ERRORE: Impossibile generare la password temporanea. Errore: " + e.getMessage();
        }
    }

    /**
     * Cambia la password di un utente.
     * @param sessionId ID della sessione
     * @param username Username dell'utente
     * @param oldPassword Password attuale
     * @param newPassword Nuova password
     * @return Stringa con il risultato dell'operazione
     */
    public synchronized String cambiaPassword(String sessionId, String username, String oldPassword, String newPassword) {
        // Verifica che la password sia impostata
        if (!isDatabasePasswordSet()) {
            return "ERRORE: Password del database non impostata. Riavvia il server.";
        }
        
        if (username == null || username.trim().isEmpty()) {
            return "ERRORE: Username non può essere vuoto!";
        }
        
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return "ERRORE: Password attuale non può essere vuota!";
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "ERRORE: Nuova password non può essere vuota!";
        }
        
        // Verifica che la nuova password sia valida
        if (!verificaPassword(newPassword)) {
            return "ERRORE: La nuova password non rispetta i criteri di sicurezza!\n" +
                   "La password deve contenere almeno 8 caratteri, inclusi lettere e numeri.";
        }
        
        try (PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT nome_cognome, email, password FROM userid WHERE userid = ?")) {
            
            stmt.setString(1, username.trim());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nomeCognome = rs.getString("nome_cognome");
                String email = rs.getString("email");
                String currentPasswordHash = rs.getString("password");
                
                // Verifica che la password attuale sia corretta
                if (!currentPasswordHash.equals(hashedPassword(oldPassword))) {
                    return "❌ ERRORE: Password attuale non corretta!";
                }
                
                // Verifica che la nuova password sia diversa dalla vecchia
                if (oldPassword.equals(newPassword)) {
                    return "❌ ERRORE: La nuova password deve essere diversa dalla password attuale!";
                }
                
                // Aggiorna la password nel database
                try (PreparedStatement updateStmt = getConnection().prepareStatement(
                        "UPDATE userid SET password = ? WHERE userid = ?")) {
                    
                    updateStmt.setString(1, hashedPassword(newPassword));
                    updateStmt.setString(2, username.trim());
                    updateStmt.executeUpdate();
                    
                    StringBuilder result = new StringBuilder();
                    result.append("✅ PASSWORD CAMBIATA CON SUCCESSO\n");
                    result.append("Utente: ").append(nomeCognome).append(" (").append(username).append(")\n");
                    result.append("Email: ").append(email).append("\n");
                    result.append("\n🔒 SICUREZZA:\n");
                    result.append("- La nuova password è stata salvata in formato hash\n");
                    result.append("- Usa la nuova password per i prossimi login\n");
                    result.append("- Conserva la password in modo sicuro\n");
                    
                    return result.toString();
                }
                
            } else {
                return "❌ ERRORE: Utente con username '" + username + "' non trovato nel database.";
            }
            
        } catch (SQLException e) {
            return "❌ ERRORE: Impossibile cambiare la password. Errore: " + e.getMessage();
        }
    }

    /**
     * Genera una password casuale sicura.
     * @return Password casuale di 12 caratteri
     */
    private String generaPasswordCasuale() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        java.util.Random random = new java.util.Random();
        
        // Assicura che la password contenga almeno una lettera maiuscola, una minuscola, un numero e un carattere speciale
        password.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt(26))); // Lettera maiuscola
        password.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26))); // Lettera minuscola
        password.append("0123456789".charAt(random.nextInt(10))); // Numero
        password.append("!@#$%^&*".charAt(random.nextInt(8))); // Carattere speciale
        
        // Aggiungi caratteri casuali per completare la password
        for (int i = 4; i < 12; i++) {
            password.append(caratteri.charAt(random.nextInt(caratteri.length())));
        }
        
        // Mischia la password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }

    /**
     * Ottiene l'utente dalla sessione specificata.
     * @param sessionId ID della sessione
     * @return UserID dell'utente o null se non loggato
     */
    private synchronized UserID getUserFromSession(String sessionId) {
        return userSessions.get(sessionId);
    }
}