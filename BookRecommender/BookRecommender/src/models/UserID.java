package models;

import java.io.Serializable;


// 
/*
 * Class per la gestione degli utenti nel sistema di raccomandazione libri
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE            
 */
public class UserID implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributi della classe
    private String nomeCognome;
    private String codiceFiscale;
    private String email;
    private String userID;
    private String password;  // La password sarà memorizzata in forma hash
    private String Questione;
    private String Risposta;
    
    /**
     * Costruttore della classe UserID
     * @param nome Nome e cognome dell'utente
     * @param codice Codice fiscale dell'utente
     * @param email Email dell'utente
     * @param user Username dell'utente
     * @param pass Password dell'utente (che verrà automaticamente hashatta) 
     * @author Mouhammad Toure
     */
    public UserID(String nome, String codice, String email, String user, String pass) {
        nomeCognome = nome;
        this.codiceFiscale = codice;
        this.email = email;
        this.userID = user;
        this.password = hashedPassword(pass);  // Hash della password alla creazione
        this.Questione = "";
        this.Risposta = hashedPassword(""); // Inizialmente vuota, hash di stringa vuota
    }
    
    // Metodi getter e setter
    
    public String getNomeCognome() {
        return nomeCognome;
    }
    
    public void setNomeCognome(String nome) {
        nomeCognome = nome;
    }
    
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    
    public void setCodiceFiscale(String codice) {
        codiceFiscale = codice;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String user) {
        userID = user;
    }
    
    public String getPassword() {
        return password;
    }
    
    /**
     * Imposta una nuova password (viene automaticamente hashatta)
     * @param pass La nuova password in chiaro
     */
    public void setPassword(String pass) {
        password = hashedPassword(pass);
    }

    /**
     * Restituisce la domanda di sicurezza dell'utente
     * @return the security question
     */
    public String getQuestione() {
        return Questione;
    }

    /**
     * Imposta la domanda di sicurezza dell'utente
     * @param questione the security question to set
     */
    public void setQuestione(String questione) {
        this.Questione = questione;
    }

    /**
     * Restituisce la risposta di sicurezza dell'utente
     * @return the security answer
     */
    public String getRisposta() {
        return Risposta;
    }

    /**
     * Imposta la risposta di sicurezza dell'utente
     * @param risposta the security answer to set
     */
    public void setRisposta(String risposta) {
        this.Risposta = hashedPassword(risposta); // Hash della risposta di sicurezza
    }

    /**
     * Restituisce una stringa con tutti i dati dell'utente, inclusa la password hash
     * @return Stringa concatenata con i dati separati da virgola
     */
    public String toString() {
        return String.join(",", getNomeCognome(), getCodiceFiscale(), getEmail(), getUserID(), getQuestione());
    }

    /**
     * Metodo per generare l'hash di una password
     * @param password La password in chiaro
     * @return L'hash della password
     */
    private String hashedPassword(String password) {
        return String.valueOf(password.hashCode()); 
    }

    /**
     * Metodo per verificare se la password fornita è corretta
     * @param password La password da verificare (in chiaro)
     * @return true se la password è corretta, false altrimenti
     */
    public boolean checkPassword(String password) {
        return this.password.equals(hashedPassword(password));
    }
   
}
