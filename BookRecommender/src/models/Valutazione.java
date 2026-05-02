package models;

import java.io.Serializable;

/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE            
 */

public class Valutazione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userID;
    private String titoloLibro;
    private int stile;
    private int contenuto;
    private int gradevolezza;
    private int originalita;
    private int edizione;
    private double votoFinale;
    
    public Valutazione(String user, String titolo, int stil, int cont, int grad, int orig, int ediz){

        this.userID = user;
        this.titoloLibro = titolo;
        this.stile = stil;
        this.contenuto = cont;
        this.gradevolezza = grad;
        this.originalita = orig;
        this.edizione = ediz;
        this.votoFinale = calcoloVotoFinale();

    }
    
    private double calcoloVotoFinale(){
        return (stile + contenuto + gradevolezza + originalita + edizione)/5;
    }
    
    public String getUserID(){
        return userID;
    }

    public void setUserID(String user){
        userID = user;
    }

    public String getTitoloLibro(){
        return titoloLibro;
    }

    public void setTitoloLibro(String titolo){
        titoloLibro = titolo;
    }

    public int getStile(){
        return stile;
    }

    public void setStile(int stil){
        stile = stil;
    }

    public int getContenuto(){
        return contenuto;
    }

    public void setContenuto(int cont){
        contenuto = cont;
    }

    public int getGradevolezza(){
        return gradevolezza;
    }

    public void setGradevolezza(int grad){
        gradevolezza = grad;
    }

    public int getOriginalita(){
        return originalita;
    }

    public void setOriginalita(int orig){
        originalita = orig;
    }

    public int getEdizione(){
        return edizione;
    }

    public void setEdizione(int ediz){
        edizione = ediz;
    }

    public double getVotoFinale(){
        return votoFinale;
    }

    public void setVotoFinale(double votoFin){
        votoFinale = votoFin;
    }

    public String toString(){
        return getUserID()+","+ getTitoloLibro()+","+ getStile()+","+ getContenuto()+","+
        getGradevolezza()+","+ getOriginalita()+","+ getEdizione()+","+ getVotoFinale();
    }
    
}