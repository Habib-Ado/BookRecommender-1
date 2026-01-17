package bookrecommender;

import java.util.ArrayList;
import java.util.List;

/**
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */

/**
 * La classe Consiglio rappresenta i consigli di lettura forniti da un utente per un determinato libro.
 * Ogni consiglio può contenere fino a un massimo di 3 libri consigliati.
 * Classe consiglio che permette agli utenti di consigliare libri.
 *
 * @author MOUHAMMAD TOURE
 */

public class Consiglio {

    private String userID; // ID dell'utente che ha fatto il consiglio
    private String titoloLibro; // Titolo del libro referenziale
    private List<String> consigliati; // Lista dei libri consigliati

    // Costruttore
    public Consiglio(String userID, String titoloLibro) {

        this.userID = userID;
        this.titoloLibro = titoloLibro;
        this.consigliati = new ArrayList<>();
        
    }

    /**
     * @return UserID dell'utente che ha fatto il consiglio
     */ 
    public String getUserID() {
        return userID;
    }

    /**
     * @Set per modificare userID dell'utente che ha fatto il consiglio
     */ 
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter per titoloLibro
    public String getTitoloLibro() {
        return titoloLibro;
    }

    // Setter per titoloLibro
    public void setTitoloLibro(String titoloLibro) {
        this.titoloLibro = titoloLibro;
    }

    // Getter per la lista dei libri consigliati
    public List<String> getConsigliati() {
        return consigliati;
    }

    public String getConsigliAsString(){
        return String.join(",", consigliati);
    }
    // Aggiunge un libro alla lista dei consigliati (evitando duplicati)
    public void aggiungiLibro(String titoloLibro) {
        if (titoloLibro != null && !titoloLibro.isEmpty() && consigliati.size() < 3) {
            consigliati.add(titoloLibro);
        }else {
            System.out.println("Non è possibile aggiungere più di 3 libri consigliati o il titolo è vuoto.");
        }
    }

    // Add the missing method
    public void inserisciSuggerimentoLibro(String titoloConsigliato) {
        if (consigliati.size() < 3) {
            consigliati.add(titoloConsigliato);
        } else {
            System.out.println("Non è possibile aggiungere più di 3 libri consigliati.");
        }
    }
 
    // Restituisce una rappresentazione testuale dell'oggetto Consiglio
    public String toString() {
        return getUserID()+ "," + getTitoloLibro() + "," + String.join(",", getConsigliAsString());
    }


}