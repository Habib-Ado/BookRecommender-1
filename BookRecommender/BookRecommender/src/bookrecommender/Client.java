package bookrecommender;

import models.InterfaceBook;
import models.UserID;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/*
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */

public class Client {

    String url = "rmi://192.168.43.94:1099/BookRecommender";
    
    private static final String ipAdress = "192.168.43.94";
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "BookRecommender";
    private static Scanner scanner = new Scanner(System.in);
    private static InterfaceBook interfaceBook;
    private UserID userId;

    public Client() {
       userId = null; // Inizialmente nessun utente loggato
    }

     public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry(ipAdress,PORT);
            interfaceBook = (InterfaceBook) registry.lookup(SERVICE_NAME);
            System.out.println("Connessione al server effettuata con successo");          
            Client client = new Client();  
            client.start();
        } catch (Exception e) {
            System.err.println("Errore nell'avvio del client: " + e.getMessage());
            e.printStackTrace();
        }
    }

     public void start() {

        try {           
           
            while (true) {
                System.out.println("\nMENU DELLA PIATTAFORMA BOOKRECOMMENDER:\n");
                if (userId == null) {
                    menuNonLoggato();
                } else {
                    menuLoggato();
                }
            }
        } catch (Exception e) {
            System.err.println("Errore nell'avvio del client: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void menuNonLoggato() {
       
        System.out.println("1. Visualizza libri");
        System.out.println("2. Cerca libro con titolo");
        System.out.println("3. Cerca libro con autore");
        System.out.println("4. Cerca libro con autore e anno");
        System.out.println("5. Registrazione");
        System.out.println("6. Login");
        System.out.println("7. Recupera password");
        System.out.println("0. Esci"); 
        System.out.print("Scegli un'opzione:");

        int scelta = leggiIntero(scanner.nextLine());
        switch (scelta) {
            case 1:
                visualizzaLibri();
                break;
            case 2:
                cercaLibroConTitolo();
                break;
            case 3:
                cercaLibroConAutore();
                break;
            case 4:
                cercaLibroConAutoreAnno();
                break;
            case 5:
                registrazione();
                break;
            case 6:
                login();
                break;
            case 7:
                recuperaPassword();
                break;
            case 0:
                System.out.println("Arrivederci!");
                try {
                     System.exit(interfaceBook.esci("USCITA"));
                } catch (Exception e) {
                    System.err.println("Errore durante il logout: " + e.getMessage());
                }
            default:
                System.out.println("Opzione non valida.");
        }
    }

    private void menuLoggato() {
        if(userId != null) {
            System.out.println("Benvenuto, " + userId.getNomeCognome() + "!");
        }else{
            System.out.println("Errore: Devi effettuare il login.");
            return;
        }
        System.out.println("1. Visualizza libri");
        System.out.println("2. Per cerca libri");
        System.out.println("3. Gestione libreria");
        System.out.println("4. Inserisci valutazione libro");
        System.out.println("5. Inserisci consiglio libro");
        System.out.println("6. Gestione profilo");
        System.out.println("7. Visualizza lista utenti");
        System.out.println("8. Logout");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione:");

        int scelta = leggiIntero(scanner.nextLine());
        switch (scelta) {
            case 1:
                visualizzaLibri();
                break;
            case 2:
                ricercaLibri();
                break;
            case 3:
                gestioneLibreria();
                break;
            case 4:
                inserisciValutazioneLibro();
                break;
            case 5:
                inserisciConsiglioLibro();
                break;
            case 6:
                gestioneProfilo();
                break;
            case 7:
                visualizzaListaUtenti();
                break;
            case 8:
                logout();
                break;
            case 0:
                System.out.println("Arrivederci!");
                try {
                     System.exit(interfaceBook.esci(userId.getUserID()));
                } catch (Exception e) {
                    System.err.println("Errore durante il logout: " + e.getMessage());
                }
            default:
                System.out.println("Opzione non valida.");
        }
    }

    private void ricercaLibri(){
        try {
            while (true) {
                System.out.println("RICERCA LIBRI");
                System.out.println("1. Ricerca per titolo");
                System.out.println("2. Ricerca per autore");
                System.out.println("3. Ricerca per genere");
                System.out.println("0. Torna al menu principale");
                System.out.print("Scegli un'opzione: ");

                int scelta = leggiIntero(scanner.nextLine());
                switch (scelta) {
                    case 1:
                        cercaLibroConTitolo();
                        break;
                    case 2:
                        cercaLibroConAutore();
                        break;
                    case 3:
                        cercaLibroConAutoreAnno();
                        break;
                    case 0:
                        System.out.println("Torno al menu principale");
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            }
           
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private static void visualizzaLibri(){
        try {
            System.out.println("VISUALIZZAZIONE DEI LIBRI");
             System.out.println(interfaceBook.visualizzaLibri());
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private static void cercaLibroConTitolo(){
        try {
            System.out.println("RICERCA LIBRO CON TITOLO");
            System.out.println("Inserisci titolo: ");
            String titolo = scanner.nextLine();
            System.out.println(interfaceBook.cercaLibroConTitolo(titolo));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private static void cercaLibroConAutore(){
        try {
            System.out.println("RICERCA LIBRO CON AUTORE");
            System.out.println("Inserisci nome dell'autore: ");
            String autore = scanner.nextLine();
            System.out.println(interfaceBook.cercaLibroConAutore(autore));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private static void cercaLibroConAutoreAnno(){
        try {
            System.out.println("RICERCA LIBRO CON AUTORE E ANNO");
            System.out.println("Inserisci nome dell'autore: ");
            String autore = scanner.nextLine();
            System.out.println("Inserisci anno: ");
            int anno = leggiIntero(scanner.nextLine());
            System.out.println(interfaceBook.cercaLibroConAutoreAnno(autore, anno));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void registrazione(){
        try {
            System.out.println("REGISTRAZIONE");
            System.out.println("Inserisci nome e cognome: ");
            String nomeCognome = scanner.nextLine();
            System.out.println("Inserisci codice fiscale: ");
            String cf = scanner.nextLine();
            System.out.println("Inserisci email: ");
            String email = scanner.nextLine();
            System.out.println("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.println("Inserisci password: ");
            String password = scanner.nextLine();
            String risultato = interfaceBook.registrazione(nomeCognome, cf, email, username, password);
            System.out.println(risultato);
            if (risultato.startsWith("OK:")) {
                System.out.println("Puoi ora effettuare il login con le tue credenziali.");
                System.out.println("Per la recuperazione della password aggiungi una questione segreta.");
                System.out.println("Poui anche lasciare vuoto.");
                System.out.print("Questione segreta: ");
                String question = scanner.nextLine();
                System.out.print("Risposta segreta: ");
                String risposta = scanner.nextLine();
                if(!question.isEmpty() || !risposta.isEmpty()){
                    String configRecupero = interfaceBook.configRecuperoPassword(username, question, risposta);
                    System.out.println(configRecupero);
                    if (configRecupero.startsWith("OK:")) {
                        System.out.println("Questione e risposta segrete configurate con successo.");
                    }else{
                        System.out.println("Errore nella configurazione della questione segreta: " + configRecupero);
                    }
                }

            }
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void login(){
        try {
            System.out.println("LOGIN");
            System.out.println("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.println("Inserisci password: ");
            String password = scanner.nextLine();
            userId = interfaceBook.login(username, password);

            if (userId != null) {
                System.out.println("Login effettuato con successo. Benvenuto, " + username + "!");
            } else {
                System.out.println("Login fallito.");
            }
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void gestioneProfilo(){
        while(true){
            System.out.println("GESTIONE PROFILO");
            System.out.println("1. Visualizza profilo");
            System.out.println("2. Modifica profilo");
            System.out.println("3. Cambia password");
            System.out.println("4. Elimina profilo");
            System.out.println("5. Configura questione e risposta segreta");
            System.out.println("6. Modifica questione e risposta segreta");
            System.out.println("0. Torna al menu principale");  
            System.out.print("Scegli un'opzione:");

            int scelta0 = leggiIntero(scanner.nextLine());

            switch (scelta0) {
                case 1:
                    visualizzaProfilo();
                    break;
                case 2:
                    modificaProfilo();
                    break;
                case 3:
                    cambiaPassword();
                    break;
                case 4:
                    eliminaProfilo();
                    break;
                case 5:
                    configuraQuestioneRisposta();
                    break;
                case 6:
                    modificaQuestioneRisposta();
                    break;
                case 0:
                    System.out.println("Torno al menu principale");
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void visualizzaProfilo(){
        try {
            System.out.println("VISUALIZZAZIONE PROFILO");
            System.out.println(interfaceBook.visualizzaProfilo(userId.getUserID()));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void modificaProfilo(){
        try {
            System.out.println("MODIFICA PROFILO");
            System.out.println("Inserisci nuovo nome e cognome: ");
            String nomeCognome = scanner.nextLine();
            System.out.println("Inserisci nuovo codice fiscale: ");
            String cf = scanner.nextLine();
            System.out.println("Inserisci nuova email: ");
            String email = scanner.nextLine();
            String risultato = interfaceBook.modificaProfilo(userId.getUserID(), nomeCognome, cf, email);
            System.out.println(risultato);
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void eliminaProfilo(){
        try {
            System.out.println("ELIMINAZIONE PROFILO");
            System.out.println("Sei sicuro di voler eliminare il tuo profilo? (s/n)");
            String conferma = scanner.nextLine();
            if (conferma.equalsIgnoreCase("s")) {
                System.out.println("Inserisci password per confermare l'eliminazione: ");
                String password = scanner.nextLine();
                String risultato = interfaceBook.eliminaProfilo(userId.getUserID(), password);
                System.out.println(risultato);
                if (risultato.startsWith("OK:")) {
                    userId = null; // Reset dell'utente loggato
                    System.out.println("Il tuo profilo è stato eliminato. Arrivederci!");
                    System.exit(0);
                }
            } else {
                System.out.println("Eliminazione del profilo annullata.");
            }
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void configuraQuestioneRisposta(){
        try {
            System.out.println("CONFIGURAZIONE QUESTIONE E RISPOSTA SEGRETA");
            String esistenteQuestion = interfaceBook.recuperoQuestione(userId.getUserID());
            if (esistenteQuestion != null && !esistenteQuestion.isEmpty()) {
                System.out.println("Hai già una questione segreta configurata: " + esistenteQuestion);
                System.out.println("Se vuoi modificarla, usa l'opzione 'Modifica questione e risposta segreta' nel menu di gestione del profilo.");
                return;
            }
            System.out.println("Inserisci questione segreta: ");
            String question = scanner.nextLine();
            System.out.println("Inserisci risposta segreta: ");
            String risposta = scanner.nextLine();
            String risultato = interfaceBook.configRecuperoPassword(userId.getUserID(), question, risposta);
            System.out.println(risultato);
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void modificaQuestioneRisposta(){
        try {
            System.out.println("MODIFICA QUESTIONE E RISPOSTA SEGRETA");
            String esistenteQuestion = interfaceBook.recuperoQuestione(userId.getUserID());
            if (esistenteQuestion == null || esistenteQuestion.isEmpty()) {
                System.out.println("Non hai ancora una questione segreta configurata.") ;
                System.out.println("Usa l'opzione 'Configura questione e risposta segreta' per aggiungerla.");
                return;
            }
            System.out.println("Inserisci la tua password per confermare l'identità: ");
            String password = scanner.nextLine();
            System.out.println("Inserisci nuova questione segreta: ");
            String question = scanner.nextLine();
            System.out.println("Inserisci nuova risposta segreta: ");
            String risposta = scanner.nextLine();
            String risultato = interfaceBook.modificaRecuperoPassword(userId.getUserID(),password, question, risposta);
            System.out.println(risultato);
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void cambiaPassword(){
        try {
            System.out.println("CAMBIO PASSWORD");
            System.out.println("Inserisci vecchia password: ");
            String oldPassword = scanner.nextLine();
            System.out.println("Inserisci nuova password: ");
            String newPassword = scanner.nextLine();
            String risultato = interfaceBook.cambiaPassword(userId.getUserID(), oldPassword, newPassword);
            System.out.println(risultato);
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void recuperaPassword(){
        try {
            System.out.println("RECUPERO PASSWORD");
            System.out.println("Questione segreta: " + interfaceBook.recuperoQuestione(userId.getUserID()));
            System.out.println("Inserisci risposta alla questione segreta: ");
            String risposta = scanner.nextLine();
            System.out.println("Inserisci nuova password: ");
            String nuovaPassword = scanner.nextLine();
            String risultato = interfaceBook.recuperaPassword(userId.getUserID(), risposta, nuovaPassword);
            System.out.println(risultato);
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void logout(){
        try {
            System.out.println("LOGOUT");
            userId = interfaceBook.logout(userId.getUserID());
            if (userId == null) {
                System.out.println("Logout effettuato con successo!");
            } else {
                System.out.println("Logout fallito.");
            }
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void inserisciValutazioneLibro(){
        try {
            System.out.println("INSERIMENTO VALUTAZIONE LIBRO");
            System.out.println("Inserisci titolo libro: ");
            String titoloLibro = scanner.nextLine();
            System.out.println("Inserisci valutazione stile (1-5): ");
            String stile = scanner.nextLine();
            System.out.println("Inserisci valutazione contenuto (1-5): ");
            String contenuto = scanner.nextLine();
            System.out.println("Inserisci valutazione gradevolezza (1-5): ");
            String gradevolezza = scanner.nextLine();
            System.out.println("Inserisci valutazione originalità (1-5): ");
            String originalita = scanner.nextLine();
            System.out.println("Inserisci valutazione edizione (1-5): ");
            String edizione = scanner.nextLine();
            System.out.println(interfaceBook.inserisciValutazioneLibro(userId.getUserID(), titoloLibro, stile, contenuto, gradevolezza, originalita, edizione));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void inserisciConsiglioLibro(){
        try {
            System.out.println("INSERIMENTO CONSIGLIO LIBRO");
            System.out.println("Inserisci titolo libro: ");
            String titoloLibro = scanner.nextLine();
            System.out.println("Inserisci consigliati: ");
            String consigliati = scanner.nextLine();
            System.out.println(interfaceBook.inserisciConsiglioLibro(userId.getUserID(), titoloLibro, consigliati));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void gestioneLibreria(){
        while(true){                                        
            System.out.println("GESTIONE LIBRERIA");
            System.out.println("1. Crea libreria");
            System.out.println("2. Aggiungi un libro alla libreria");
            System.out.println("3. Rimuovi un libro dalla libreria");
            System.out.println("4. Visualizza la libreria");
            System.out.println("5. Torna al menu principale");  
            System.out.print("Scegli un'opzione:");

            int scelta0 = Integer.parseInt(scanner.nextLine());

            switch (scelta0) {
                case 1:
                    if(userId != null) {
                        creazioneLibreria();
                    } 
                    break;
                case 2:
                    if(userId != null) {
                        aggiungiLibroInLibreria();
                    } 
                    break;
                case 3:
                    if(userId != null) {
                        rimuoviLibroInLibreria();
                    }
                    break;
                case 4:
                    if(userId != null) {
                        visualizzaLibriInLibreria();
                    }
                    break;
                case 5:
                    System.out.println("Torno al menu principale");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
                          
    }

    private void creazioneLibreria(){
        try {
            System.out.println("CREAZIONE LIBRERIA");
            System.out.println("Inserisci nome libreria: ");
            String nomeLibreria = scanner.nextLine();
            System.out.println(interfaceBook.creaLibreria(userId.getUserID(), nomeLibreria));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void aggiungiLibroInLibreria(){
        try {
            System.out.println("AGGIUNTA LIBRO IN LIBRERIA");
            System.out.println("Inserisci nome della libreria: ");
            String nomeLibreria = scanner.nextLine();
            System.out.println("Inserisci titolo libro: ");
            String titoloLibro = scanner.nextLine();
            System.out.println(interfaceBook.aggiungiLibroLibreria(userId.getUserID(), nomeLibreria, titoloLibro));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void rimuoviLibroInLibreria(){
        try {
            System.out.println("RIMOZIONE LIBRO IN LIBRERIA");
            System.out.println("Inserisci nome della libreria: ");
            String nomeLibreria = scanner.nextLine();
            System.out.println("Inserisci titolo libro: ");
            String titoloLibro = scanner.nextLine();
            System.out.println(interfaceBook.rimuoviLibroLibreria(userId.getUserID(), nomeLibreria, titoloLibro));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void visualizzaLibriInLibreria(){
        try {
            System.out.println("VISUALIZZAZIONE LIBRI IN LIBRERIA");
            System.out.println("Inserisci nome della libreria: ");
            String nomeLibreria = scanner.nextLine();
            System.out.println(interfaceBook.visualizzaLibriInLibreria(userId.getUserID(), nomeLibreria));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private void visualizzaListaUtenti(){
        try {
            System.out.println("LISTA UTENTI REGISTRATI");
            System.out.println(interfaceBook.listaUtenti(userId.getUserID()));
        }catch(Exception e){
            System.err.println("Errore del client: " + e.getMessage());
        }
    }

    private static int leggiIntero(String intero) {
        try {
            return Integer.parseInt(intero);
        } catch (NumberFormatException e) {
            System.out.println("Input non valido");
            // You may want to prompt again or return a default value
            return -1;
        }
    }

}
