package bookrecommender;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Implementazione dell'interfaccia RMI per il sistema BookRecommender.
 * Fornisce le funzionalità definite nell'interfaccia InterfaceBook, delegando le operazioni alla classe BookRecommender.
 * Gestisce le richieste dei client e interagisce con il database per fornire i servizi richiesti.
 * @version 1.0
 * @author Mouhammad Toure
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

    @Override
    /**
     * Visualizza tutti i libri disponibili nel sistema.
     * @return Una stringa contenente l'elenco dei libri.
     * @throws RemoteException
     */
    public String visualizzaLibri() throws RemoteException {
        return bookRecommender.visualizzareLibri();
    }

    @Override
    /**
     * Cerca un libro in base al titolo.
     * @param titolo Il titolo del libro da cercare.
     * @return Una stringa contenente i dettagli del libro trovato o un messaggio di errore.
     * @throws RemoteException
     */
    public String cercaLibroConTitolo(String titolo) throws RemoteException {
        titolo = titolo.toLowerCase();
        return bookRecommender.cercaLibroConTitolo(titolo);
    }

    @Override
    /**
     * Cerca un libro in base all'autore.
     * @param autore Il nome dell'autore del libro da cercare.
     * @return Una stringa contenente i dettagli del libro trovato o un messaggio di errore.
     * @throws RemoteException
     */
    public String cercaLibroConAutore(String autore) throws RemoteException {
        return bookRecommender.cercaLibroConAutore(autore);
    }

    @Override
    /**
     * Cerca un libro in base all'autore e all'anno di pubblicazione.
     * @param autor Il nome dell'autore del libro da cercare.
     * @param anno L'anno di pubblicazione del libro da cercare.
     * @return Una stringa contenente i dettagli del libro trovato o un messaggio di errore.
     * @throws RemoteException
     */
    public String cercaLibroConAutoreAnno(String autor, int anno) throws RemoteException {
        return bookRecommender.cercaLibroConAutoreAnno(autor, anno);
    }

    @Override
    /**
     * Registra un nuovo utente nel sistema.
     * @param sessionId L'ID della sessione dell'utente.
     * @param name Il nome dell'utente.
     * @param cf Il codice fiscale dell'utente.
     * @param email L'email dell'utente.
     * @param userid L'ID utente scelto dall'utente.
     * @param password La password scelta dall'utente.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String registrazione(String sessionId, String name, String cf, String email, String userid, String password) throws RemoteException {
        return bookRecommender.registrazione(sessionId, name, cf, email, userid, password);
    }

    @Override
    /**
     * Effettua il login di un utente nel sistema.
     * @param sessionId L'ID della sessione dell'utente.
     * @param userid L'ID utente dell'utente.
     * @param password La password dell'utente.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String login(String sessionId, String userid, String password) throws RemoteException {
        return bookRecommender.login(sessionId, userid, password);
    }

    @Override
    /**
     * Crea una nuova libreria per l'utente.
     * @param sessionId L'ID della sessione dell'utente.
     * @param nomeLibreria Il nome della libreria da creare.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String creaLibreria(String sessionId, String nomeLibreria) throws RemoteException {
        return bookRecommender.creareLibreria(sessionId, nomeLibreria);
    }

    @Override
    /**
     * Inserisce una valutazione per un libro.
     * @param sessionId L'ID della sessione dell'utente.
     * @param title Il titolo del libro da valutare.
     * @param style Lo stile della valutazione.
     * @param content Il contenuto della valutazione.
     * @param pleasantness La piacevolezza del libro.
     * @param originality L'originalità del libro.
     * @param edition L'edizione del libro.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String inserisciValutazioneLibro(String sessionId, String title, String style, String content, String pleasantness, String originality, String edition) throws RemoteException {
        return bookRecommender.inserisciValutazioneLibro(sessionId, title, style, content, pleasantness, originality, edition);
    }

    @Override
    /**
     * Inserisce un consiglio per un libro.
     * @param sessionId L'ID della sessione dell'utente.
     * @param titoloLibro Il titolo del libro per cui si vuole inserire il consiglio.
     * @param consigliati I libri consigliati.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String inserisciConsiglioLibro(String sessionId, String titoloLibro, String consigliati) throws RemoteException {
        return bookRecommender.inserisciConsiglioLibro(sessionId, titoloLibro, consigliati);
    }

    @Override
    /**
     * Aggiunge un libro a una libreria esistente.
     * @param sessionId L'ID della sessione dell'utente.
     * @param nomeLibreria Il nome della libreria a cui aggiungere il libro.
     * @param titoloLibro Il titolo del libro da aggiungere.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String aggiungiLibroLibreria(String sessionId, String nomeLibreria, String titoloLibro) throws RemoteException{
        return bookRecommender.aggiungiLibroLibreria(sessionId, nomeLibreria, titoloLibro);
    }

    @Override
    /**
     * Rimuove un libro da una libreria esistente nella propria libreria.
     * @param sessionId L'ID della sessione dell'utente.
     * @param nomeLibreria Il nome della libreria da cui rimuovere il libro.
     * @param titoloLibro Il titolo del libro da rimuovere.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String rimuoviLibroLibreria(String sessionId, String nomeLibreria, String titoloLibro) throws RemoteException{
        return bookRecommender.rimuoviLibroLibreria(sessionId, nomeLibreria, titoloLibro);
    }

    @Override
    /**
     * Visualizza il contenuto di una libreria esistente nella propria libreria.
     * @param sessionId L'ID della sessione dell'utente.
     * @param nomeLibreria Il nome della libreria da visualizzare.
     * @return Una stringa contenente l'elenco dei libri nella libreria o un messaggio di errore.
     * @throws RemoteException
     */
    public String visualizzaLibreria(String sessionId, String nomeLibreria) throws RemoteException{
        return bookRecommender.visualizzaLibreria(sessionId, nomeLibreria);
    }

    @Override
    /**
     * Permette di torna al menu principale del sistema.
     * @return Una stringa contenente il messaggio di ritorno al menu principale.
     * @throws RemoteException
     */
    public String tornaMenuPrincipale() throws RemoteException{
        return bookRecommender.tornaMenuPrincipale();
    }
    

    @Override
    /**
     * Effettua il logout di un utente dal sistema.
     * @param sessionId L'ID della sessione dell'utente.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String logout(String sessionId) throws RemoteException {
        return bookRecommender.logout(sessionId);
    }

    @Override
    /**
     * Permette di uscire dal sistema / Chiusura del sistema.
     * @return Una stringa contenente il messaggio di uscita.
     * @throws RemoteException
     */
    public String esci() throws RemoteException {
        return "OK: Uscita dal sistema...";
    }

    @Override
    /**
     * Recupera la password di un utente in caso di smarrimento.
     * In base all'implementazione propone una nuova password che dovrà essere cambiata al prossimo login.
     * @param username L'ID utente dell'utente.
     * @return Una stringa contenente la password recuperata o un messaggio di errore.
     * @throws RemoteException
     */
    public String recuperaPassword(String username) throws RemoteException {
        return bookRecommender.recuperaPassword(username);
    }

    @Override
    /**
     * Genera una password temporanea per un utente in caso di smarrimento della password.
     * La password temporanea dovrà essere cambiata al prossimo login.
     * @param username L'ID utente dell'utente.
     * @return Una stringa contenente la password temporanea generata o un messaggio di errore.
     * @throws RemoteException
     */
    public String generaPasswordTemporanea(String username) throws RemoteException {
        return bookRecommender.generaPasswordTemporanea(username);
    }

    @Override
    /**
     * Cambia la password di un utente.
     * @param sessionId L'ID della sessione dell'utente.
     * @param username L'ID utente dell'utente.
     * @param oldPassword La vecchia password dell'utente.
     * @param newPassword La nuova password scelta dall'utente.
     * @return Una stringa contenente un messaggio di successo o errore.
     * @throws RemoteException
     */
    public String cambiaPassword(String sessionId, String username, String oldPassword, String newPassword) throws RemoteException {
        return bookRecommender.cambiaPassword(sessionId, username, oldPassword, newPassword);
    }

}