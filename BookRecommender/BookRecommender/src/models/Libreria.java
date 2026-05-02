package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */

public class Libreria implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userID;
    private String nomeLibreria;
    private List<String> libri;
    
    public Libreria(String user, String nomeLib){

        userID = user;
        nomeLibreria = nomeLib;
        libri = new ArrayList<>();

    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String user){
        userID = user;
    }

    public String getNomeLibreria(){
        return nomeLibreria;
    }
    
    public void setNomeLibreria(String nomeLib){
        nomeLibreria = nomeLib;
    }
    
    public void aggiungiLibri(String titoloLib){
        libri.add(titoloLib);
    }

    public String getLibriAsString() {
        return String.join(",", libri);  // Unisce tutti i libri con una virgola
    }
    
    public String toString(){
        return getUserID()+","+ getNomeLibreria()+","+ String.join(",", getLibriAsString());
    }
    
}
