package models;

import java.io.Serializable;

/*
 * Classe per la gestione dei libri nel sistema di raccomandazione libri. 
 * Contiene informazioni come titolo, autore, categoria, editore e anno di pubblicazione.
 * Include anche metodi per rimuovere caratteri speciali dalle stringhe, utili per la pulizia dei dati.
 * 
 */

public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String titolo;
    private String autore;
    private String categoria;
    private String editore;
    private String anno;
    
    public Libro(String titolo, String autore, String categoria, String editore, String anno){
        
        this.titolo = titolo;
        this.autore = autore;
        this.categoria = categoria;
        this.editore = editore;
        this.anno = anno;

    }
    
    public String getTitolo(){
        return titolo;
    }
    public void setTitolo(String titolo){
        this.titolo = titolo;
    }
    public String getAutore(){
        return autore;
    }
    public void setAutore(String autore){
        this.autore = autore;
    }
    public String getAnno(){
        return anno;
    }
    public void setAnno(String anno){
        this.anno = anno;
    }
    public String getEditore(){
        return editore;
    }
    public void setEditore(String editore){
        this.editore = editore;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    
    public String toString(){
        return String.join(",", getTitolo(), getAutore(), getCategoria(), getEditore(), getAnno());
    }


    public static String rimuoviCaratteriSpeciali(String input) {
        if (input == null) {
            return null; // Gestione del caso in cui l'input è null
        }
    
        // Regex per mantenere solo lettere, numeri e spazi
        String regex = "[^a-zA-Z0-9\\s]";
    
        // Sostituisci tutti i caratteri speciali con una stringa vuota
        return input.replaceAll(regex, "");
    }

    public static String rimuoviSpeciali(String input) {    
        // Regex per mantenere solo lettere, numeri e spazi
        String regex = "[^a-zA-Z0-9\\s]";
        // Sostituisci tutti i caratteri speciali con una stringa vuota
        return input.replaceAll(regex, "");
    }

    public static String nuovoAutore(String author){
        String authore = "";
        for(int i = 0; i < author.length(); i++){
            if(i > 3){
                authore += author.charAt(i);
            }
        }

        return authore;
    }
    
}