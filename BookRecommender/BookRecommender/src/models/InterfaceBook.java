package models;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */

public interface InterfaceBook extends Remote {

  public String visualizzaLibri() throws RemoteException;
  public String cercaLibroConTitolo(String titolo) throws RemoteException;
  public String cercaLibroConAutore(String autore) throws RemoteException;
  public String cercaLibroConAutoreAnno(String autore, int anno) throws RemoteException;

  public String registrazione(String name, String cf, String email, String userid, String password) throws RemoteException;
  public UserID login(String userid, String password) throws RemoteException;
  public String configRecuperoPassword(String username, String questione, String risposta) throws RemoteException;
  public String visualizzaProfilo(String userId) throws RemoteException;
  public String modificaProfilo(String userId, String name, String cf, String email) throws RemoteException;
  public String eliminaProfilo(String userId, String password) throws RemoteException;
  public String listaUtenti(String userId) throws RemoteException;

  public String creaLibreria(String userId, String nomeLibreria) throws RemoteException;
  public String inserisciValutazioneLibro(String userId, String title, String style, String content, String pleasantness, String originality, String edition) throws RemoteException;
  public String inserisciConsiglioLibro(String userId, String titoloLibro, String consigliati) throws RemoteException;
  
  public UserID logout(String userId) throws RemoteException;
  public int esci(String userId) throws RemoteException;
  public String aggiungiLibroLibreria(String userId, String nomeLibreria, String titoloLibro) throws RemoteException;
  public String rimuoviLibroLibreria(String userId, String nomeLibreria, String titoloLibro) throws RemoteException;
  public String visualizzaLibriInLibreria(String userId, String nomeLibreria) throws RemoteException;
  public String recuperoQuestione(String username) throws RemoteException;
  public String recuperaPassword(String username, String risposta, String nuovaPassword) throws RemoteException;
  public String cambiaPassword(String username, String oldPassword, String newPassword) throws RemoteException;
 
}
