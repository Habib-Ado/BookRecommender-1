package server;

import bookrecommender.BookRecommender;
import models.*;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/*
 * CLASSE CHE IMPLEMENTA L'INTERFACCIA REMOTA E DELEGA LE RICHIESTE AL BOOKRECOMMENDER
 *  La classe InterfaceImpl estende UnicastRemoteObject e implementa l'interfaccia remota InterfaceBook.
 *  Ogni metodo dell'interfaccia remota viene implementato delegando la logica al BookRecommender, 
 *  che si occupa di gestire le operazioni sui libri, utenti e librerie.
 *  PROGETTO REALIZZATO DA:
 *
 *  MOUHAMMAD TOURE 
 */

public class InterfaceImpl extends UnicastRemoteObject implements InterfaceBook {

    private static final long serialVersionUID = 1L;

    private BookRecommender bookRecommender;
   
    /**
     * Costruttore della classe che estende UnicastRemoteObject.
     * 
     * @throws java.rmi.RemoteException
     */

    public InterfaceImpl() throws RemoteException {
        super();
        bookRecommender = new BookRecommender();
    }

    /**
     * Implementazione dei metodi dell'interfaccia remota, 
     * delegando le richieste al BookRecommender.
     * Ogni metodo corrisponde a una funzionalità del sistema, 
     * come visualizzare libri, cercare libri, gestire utenti e librerie, ecc.
     */
    public String visualizzaLibri() throws RemoteException {
        return bookRecommender.visualizzareLibri();
    }

    /**
     * Ogni metodo dell'interfaccia remota viene implementato delegando la logica al BookRecommender,
     * che si occupa di gestire le operazioni sui libri, utenti e librerie.
     * Ad esempio, il metodo cercaLibroConTitolo chiama il metodo corrispondente nel BookRecommender per cercare un libro in base al titolo.
     * @param titolo Il titolo del libro da cercare
     * @return Una stringa con i dettagli del libro trovato o un messaggio di errore se il libro non viene trovato
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String cercaLibroConTitolo(String titolo) throws RemoteException {
        return bookRecommender.cercaLibroConTitolo(titolo);
    }

    /**
     * Cerca un libro in base all'autore.
     * @param autore L'autore del libro da cercare
     * @return Una stringa con i dettagli del libro trovato o un messaggio di errore se il libro non viene trovato
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String cercaLibroConAutore(String autore) throws RemoteException {
        return bookRecommender.cercaLibroConAutore(autore);
    }

    /**
     * Cerca un libro in base all'autore e all'anno di pubblicazione.
     * @param autore L'autore del libro da cercare
     * @param anno L'anno di pubblicazione del libro da cercare
     * @return Una stringa con i dettagli del libro trovato o un messaggio di errore se il libro non viene trovato
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String cercaLibroConAutoreAnno(String autore, int anno) throws RemoteException {
        return bookRecommender.cercaLibroConAutoreAnno(autore, anno);
    }

    /**
     * Registra un nuovo utente.
     * @param name Il nome dell'utente
     * @param codiceFiscale Il codice fiscale dell'utente
     * @param email L'email dell'utente
     * @param userid Lo user ID dell'utente
     * @param password La password dell'utente
     * @return Una stringa con il risultato della registrazione
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String registrazione(String name, String codiceFiscale, String email, String userid, String password) throws RemoteException {
        return bookRecommender.registrazione(name, codiceFiscale, email, userid, password);
    }

    /**
     * Effettua il login di un utente.
     * @param userid Lo user ID dell'utente
     * @param password La password dell'utente
     * @return Un oggetto UserID con le informazioni dell'utente loggato o null se il login fallisce
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public UserID login(String userid, String password) throws RemoteException {
        return bookRecommender.login(userid, password);
    }

    /**
     * Crea una nuova libreria per l'utente.
     * @param userId Lo user ID dell'utente
     * @param nomeLibreria Il nome della libreria da creare
     * @return Una stringa con il risultato della creazione della libreria
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String creaLibreria(String userId, String nomeLibreria) throws RemoteException {
        return bookRecommender.creareLibreria(userId, nomeLibreria);
    }

    /**
     * Inserisce una nuova valutazione per un libro.
     * @param userId Lo user ID dell'utente
     * @param title Il titolo del libro
     * @param style Lo stile di scrittura
     * @param content Il contenuto del libro
     * @param pleasantness La piacevolezza del libro
     * @param originality L'originalità del libro
     * @param edition L'edizione del libro
     * @return Una stringa con il risultato dell'inserimento della valutazione
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String inserisciValutazioneLibro(String userId, String title, String style, String content, String pleasantness, String originality, String edition) throws RemoteException {
        return bookRecommender.inserisciValutazioneLibro(userId, title, style, content, pleasantness, originality, edition);
    }

    /**
     * Inserisce un nuovo consiglio per un libro.
     * @param userId Lo user ID dell'utente
     * @param titoloLibro Il titolo del libro
     * @param consigliati I consigliati per il libro
     * @return Una stringa con il risultato dell'inserimento del consiglio
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String inserisciConsiglioLibro(String userId, String titoloLibro, String consigliati) throws RemoteException {
        return bookRecommender.inserisciConsiglioLibro(userId, titoloLibro, consigliati);
    }

    /**
     * Aggiunge un libro a una libreria.
     * @param userId Lo user ID dell'utente
     * @param nomeLibreria Il nome della libreria
     * @param titoloLibro Il titolo del libro
     * @return Una stringa con il risultato dell'aggiunta del libro
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String aggiungiLibroLibreria(String userId, String nomeLibreria, String titoloLibro) throws RemoteException{
        return bookRecommender.aggiungiLibroLibreria(userId, nomeLibreria, titoloLibro);
    }

    /**
     * Rimuove un libro da una libreria.
     * @param userId Lo user ID dell'utente
     * @param nomeLibreria Il nome della libreria
     * @param titoloLibro Il titolo del libro
     * @return Una stringa con il risultato della rimozione del libro
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String rimuoviLibroLibreria(String userId, String nomeLibreria, String titoloLibro) throws RemoteException{
        return bookRecommender.rimuoviLibroLibreria(userId, nomeLibreria, titoloLibro);
    }

    /**
     * Visualizza i libri presenti in una libreria.
     * @param userId Lo user ID dell'utente
     * @param nomeLibreria Il nome della libreria
     * @return Una stringa con la lista dei libri nella libreria
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String visualizzaLibriInLibreria(String userId, String nomeLibreria) throws RemoteException{
        return bookRecommender.visualizzaLibriInLibreria(userId, nomeLibreria);
    }

    /**
     * Recupera la questione di sicurezza per un utente.
     * @param userId Lo user ID dell'utente
     * @return Una stringa con la questione di sicurezza dell'utente
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String recuperoQuestione(String userId) throws RemoteException {
        return bookRecommender.recuperoQuestione(userId);
    }

    /**
     * Visualizza il profilo di un utente.
     * @param userId Lo user ID dell'utente
     * @return Una stringa con i dettagli del profilo dell'utente
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String visualizzaProfilo(String userId) throws RemoteException {
        return bookRecommender.visualizzaProfilo(userId);
    }

    /**
     * Modifica il profilo di un utente.
     * @param userId Lo user ID dell'utente
     * @param name Il nuovo nome dell'utente
     * @param cf Il nuovo codice fiscale dell'utente
     * @param email La nuova email dell'utente
     * @return Una stringa con il risultato della modifica del profilo
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String modificaProfilo(String userId, String name, String cf, String email) throws RemoteException {
        return bookRecommender.modificaProfilo(userId, name, cf, email);
    }

    /**
     * Elimina il profilo di un utente.
     * @param userId Lo user ID dell'utente
     * @param password La password dell'utente per confermare l'eliminazione
     * @return Una stringa con il risultato dell'eliminazione del profilo
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String eliminaProfilo(String userId, String password) throws RemoteException {
        return bookRecommender.eliminaProfilo(userId, password);
    }

    /**
     * Visualizza la lista degli utenti registrati.
     * @param userId Lo user ID dell'utente che richiede la lista
     * @return Una stringa con la lista degli utenti registrati
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String listaUtenti(String userId) throws RemoteException {
        return bookRecommender.listaUtenti(userId);
    }

    /**
     * Effettua il logout di un utente.
     * @param userId Lo user ID dell'utente che effettua il logout
     * @return Un oggetto UserID con le informazioni dell'utente che ha effettuato il logout o null se il logout fallisce
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public UserID logout(String userId) throws RemoteException {
        return bookRecommender.logout(userId);
    }

    /**
     * Metodo per terminare la sessione del server.
     * @return Un intero che indica il risultato dell'operazione (0 per successo)
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public int esci(String userId) throws RemoteException {
        return bookRecommender.esci(userId);
    }

    /**
     * Configura la questione di recupero password per un utente.
     * @param username Lo username dell'utente
     * @param questione La questione di sicurezza da configurare
     * @param risposta La risposta alla questione di sicurezza
     * @return Una stringa con il risultato della configurazione della questione di recupero password
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String configRecuperoPassword(String username, String questione, String risposta) throws RemoteException {
        return bookRecommender.configRecuperoPassword(username, questione, risposta);
    }

    public String modificaRecuperoPassword(String username, String password, String questione, String risposta) throws RemoteException {
        return bookRecommender.modificaRecuperoPassword(username, password, questione, risposta);
    }

    /**
     * Recupera la password di un utente in base alla risposta alla questione di sicurezza.
     * @param username Lo username dell'utente
     * @param risposta La risposta alla questione di sicurezza
     * @param nuovaPassword La nuova password da impostare se la risposta è corretta
     * @return Una stringa con il risultato del recupero password
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String recuperaPassword(String username, String risposta, String nuovaPassword) throws RemoteException {
        return bookRecommender.recuperaPassword(username, risposta, nuovaPassword);
    }

    /**
     * Cambia la password di un utente.
     * @param username Lo username dell'utente
     * @param oldPassword La vecchia password dell'utente
     * @param newPassword La nuova password da impostare
     * @return Una stringa con il risultato del cambio password
     * @throws RemoteException In caso di errori di comunicazione remota
     */
    public String cambiaPassword(String username, String oldPassword, String newPassword) throws RemoteException {
        return bookRecommender.cambiaPassword(username, oldPassword, newPassword);
    }

}
