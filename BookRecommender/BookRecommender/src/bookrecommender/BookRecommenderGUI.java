package bookrecommender;

import models.InterfaceBook;
import models.UserID;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

public class BookRecommenderGUI extends JFrame {
    private static final String RMI_HOST = "192.168.43.94"; // Cambia se il server è su un host diverso
    private static final String SERVICE_NAME = "BookRecommender";
    private static final int RMI_PORT = 1099;

    private InterfaceBook interfaceBook;
    private UserID userId; // Session ID per questo client GUI
    private boolean isLoggedIn = false; // Stato di login

    public BookRecommenderGUI() {

        setTitle("Book Recommender");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Connessione RMI
        try {
            Registry registry = LocateRegistry.getRegistry(RMI_HOST, RMI_PORT);
            interfaceBook = (InterfaceBook) registry.lookup(SERVICE_NAME);
            JOptionPane.showMessageDialog(this, "Connessione al server RMI avvenuta con successo!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server RMI: " + e.getMessage());
            System.exit(1);
        }

        mostraMenuPrincipale();
    }

    private void mostraMenuPrincipale() {
        getContentPane().removeAll();
        setTitle("Book Recommender - Menu Principale");

        // Layout base
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnVisualizzaLibri = new JButton("Visualizza Libri");
        JButton btnCercaTitolo = new JButton("Cerca Libro per Titolo");
        JButton btnCercaAutore = new JButton("Cerca Libro per Autore");
        JButton btnCercaAutoreAnno = new JButton("Cerca Libro per Autore e Anno");
        JButton btnRegistrazione = new JButton("Registrazione");
        JButton btnLogin = new JButton("Login");
        JButton btnRecuperaPassword = new JButton("Recupera Password");
        JButton btnEsci = new JButton("Esci");

        panel.add(btnVisualizzaLibri);
        panel.add(btnCercaTitolo);
        panel.add(btnCercaAutore);
        panel.add(btnCercaAutoreAnno);
        panel.add(btnRegistrazione);
        panel.add(btnLogin);
        panel.add(btnRecuperaPassword);
        panel.add(btnEsci);

        add(panel);

        // Azioni pulsanti
        btnVisualizzaLibri.addActionListener(e -> mostraLibri());
        btnCercaTitolo.addActionListener(e -> cercaLibroPerTitolo());
        btnCercaAutore.addActionListener(e -> cercaLibroPerAutore());
        btnCercaAutoreAnno.addActionListener(e -> cercaLibroPerAutoreAnno());
        btnRegistrazione.addActionListener(e -> mostraDialogRegistrazione());
        btnLogin.addActionListener(e -> mostraDialogLogin());
        btnRecuperaPassword.addActionListener(e -> mostraDialogRecuperaPassword());
        btnEsci.addActionListener(e -> {
            try {
                System.exit(interfaceBook.esci("USCITA"));
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante l'uscita: " + ex.getMessage());
            }
        });
        
        revalidate();
        repaint();
    }

    private void mostraMenuUtenteLoggato() {
        getContentPane().removeAll();
        setTitle("Book Recommender - Menu Utente");
        
        // Layout per utenti loggati
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        
        // Sezione Visualizzazione e Ricerca
        JLabel lblVisualizzazione = new JLabel("📚 VISUALIZZAZIONE E RICERCA");
        lblVisualizzazione.setHorizontalAlignment(SwingConstants.CENTER);
        lblVisualizzazione.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblVisualizzazione);
        
        JButton btnVisualizzaLibri = new JButton("Visualizza Tutti i Libri");
        JButton btnCercaTitolo = new JButton("Cerca Libro per Titolo");
        JButton btnCercaAutore = new JButton("Cerca Libro per Autore");
        JButton btnCercaAutoreAnno = new JButton("Cerca Libro per Autore e Anno");
        
        panel.add(btnVisualizzaLibri);
        panel.add(btnCercaTitolo);
        panel.add(btnCercaAutore);
        panel.add(btnCercaAutoreAnno);
        
        // Sezione Gestione Librerie
        JLabel lblLibrerie = new JLabel("📖 GESTIONE LIBRERIE");
        lblLibrerie.setHorizontalAlignment(SwingConstants.CENTER);
        lblLibrerie.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLibrerie);
        
        JButton btnCreaLibreria = new JButton("Crea Nuova Libreria");
        JButton btnAggiungiLibro = new JButton("Aggiungi Libro a Libreria");
        JButton btnVisualizzaLibreria = new JButton("Visualizza Libreria");
        JButton btnRimuoviLibro = new JButton("Rimuovi Libro da Libreria");
   
        
        panel.add(btnCreaLibreria);
        panel.add(btnAggiungiLibro);
        panel.add(btnVisualizzaLibreria);
        panel.add(btnRimuoviLibro);

        
        // Sezione Valutazioni e Consigli
        JLabel lblValutazioni = new JLabel("⭐ VALUTAZIONI E CONSIGLI");
        lblValutazioni.setHorizontalAlignment(SwingConstants.CENTER);
        lblValutazioni.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblValutazioni);
        
        JButton btnInserisciValutazione = new JButton("Inserisci Valutazione Libro");
        JButton btnInserisciConsiglio = new JButton("Inserisci Consiglio Libro");
        JButton btnRecuperaPassword = new JButton("Recupera Password");
        JButton btnCambiaPassword = new JButton("Cambia Password");
        
        panel.add(btnInserisciValutazione);
        panel.add(btnInserisciConsiglio);
        panel.add(btnRecuperaPassword);
        panel.add(btnCambiaPassword);
        
        // Sezione Account
        JLabel lblAccount = new JLabel("👤 ACCOUNT");
        lblAccount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAccount.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblAccount);
        
        JButton btnLogout = new JButton("Logout");
        JButton btnTornaMenu = new JButton("Torna al Menu Principale");
        JButton btnEsci = new JButton("Esci");
        
        panel.add(btnLogout);
        panel.add(btnTornaMenu);
        panel.add(btnEsci);

        // Scroll pane per gestire molti pulsanti
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Azioni pulsanti
        btnVisualizzaLibri.addActionListener(e -> mostraLibri());
        btnCercaTitolo.addActionListener(e -> cercaLibroPerTitolo());
        btnCercaAutore.addActionListener(e -> cercaLibroPerAutore());
        btnCercaAutoreAnno.addActionListener(e -> cercaLibroPerAutoreAnno());
        
        btnCreaLibreria.addActionListener(e -> creaLibreria());
        btnAggiungiLibro.addActionListener(e -> aggiungiLibroLibreria());
        btnRimuoviLibro.addActionListener(e -> rimuoviLibroLibreria());
        btnVisualizzaLibreria.addActionListener(e -> visualizzaLibreria());
        
        btnInserisciValutazione.addActionListener(e -> inserisciValutazione());
        btnInserisciConsiglio.addActionListener(e -> inserisciConsiglio());
        btnRecuperaPassword.addActionListener(e -> mostraDialogRecuperaPassword());
        btnCambiaPassword.addActionListener(e -> mostraDialogCambiaPassword());
       
        btnLogout.addActionListener(e -> logout());
        btnTornaMenu.addActionListener(e -> {
            isLoggedIn = false;
            mostraMenuPrincipale();
        });
        btnEsci.addActionListener(e -> {
            try { 
                System.exit(interfaceBook.esci(userId.getUserID()));
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante l'uscita: " + ex.getMessage());
            }
        });

        revalidate();
        repaint();
    }

    private void mostraDialogLogin() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        Object[] fields = {
            "Username:", userField,
            "Password:", passField
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                userId = interfaceBook.login(userField.getText(), new String(passField.getPassword()));

                if (userId != null && userId.getUserID() != null && !userId.getUserID().isEmpty()) {
                    isLoggedIn = true;
                    JOptionPane.showMessageDialog(this, "Login effettuato con successo.");
                    mostraMenuUtenteLoggato();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore durante il login. Controlla le credenziali.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void mostraDialogRegistrazione() {
        JTextField nameField = new JTextField();
        JTextField cfField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        
        Object[] fields = {
            "Nome e Cognome:", nameField,
            "Codice Fiscale:", cfField,
            "Email:", emailField,
            "Username:", userField,
            "Password:", passField
        };
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Registrazione", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String result = interfaceBook.registrazione(nameField.getText(), cfField.getText(), 
                                                         emailField.getText(), userField.getText(), new String(passField.getPassword()));
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("Registrazione completata con successo")) {
                    isLoggedIn = true;
                    mostraMenuUtenteLoggato();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private String getSessionId() {
        return (userId != null ? userId.getUserID() : null);
    }

    private void logout() {
        try {
            userId = interfaceBook.logout(userId.getUserID());
            JOptionPane.showMessageDialog(this, "Logout effettuato con successo.");
            isLoggedIn = false;
            mostraMenuPrincipale();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
        }
    }

    private void mostraLibri() {
        try {
            // Mostra un messaggio di caricamento
            JOptionPane.showMessageDialog(this, "Caricamento libri in corso...", "Caricamento", JOptionPane.INFORMATION_MESSAGE);
            
            String libri = interfaceBook.visualizzaLibri();
            
            if (libri == null || libri.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nessun libro disponibile nel database.", "Libri", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Crea una finestra più grande e meglio formattata
            JTextArea area = new JTextArea(libri);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(800, 600));

            JFrame frame = new JFrame("📚 Libri Disponibili nel Sistema");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(scroll);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
            
            // Mostra un messaggio di successo
            JOptionPane.showMessageDialog(this, "Libri caricati con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Errore durante il caricamento dei libri:\n" + ex.getMessage() + 
                "\n\nVerifica che:\n1. Il server sia in esecuzione\n2. Il database sia configurato\n3. Ci siano libri nel database", 
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cercaLibroPerTitolo() {
        String titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro:");
        if (titolo != null && !titolo.isEmpty()) {
            try {
                String result = interfaceBook.cercaLibroConTitolo(titolo);
                JOptionPane.showMessageDialog(this, result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void cercaLibroPerAutore() {
        String autore = JOptionPane.showInputDialog(this, "Inserisci l'autore:");
        if (autore != null && !autore.isEmpty()) {
            try {
                String result = interfaceBook.cercaLibroConAutore(autore);
                JOptionPane.showMessageDialog(this, result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void cercaLibroPerAutoreAnno() {
        String autore = JOptionPane.showInputDialog(this, "Inserisci l'autore:");
        if (autore != null && !autore.isEmpty()) {
            String annoStr = JOptionPane.showInputDialog(this, "Inserisci l'anno:");
            if (annoStr != null && !annoStr.isEmpty()) {
                try {
                    int anno = Integer.parseInt(annoStr);
                    String result = interfaceBook.cercaLibroConAutoreAnno(autore, anno);
                    JOptionPane.showMessageDialog(this, result);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Errore: Anno non valido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }

    private void creaLibreria() {
        String nomeLibreria = JOptionPane.showInputDialog(this, "Inserisci il nome della libreria:");
        if (nomeLibreria != null && !nomeLibreria.isEmpty()) {
            try {
                String result = interfaceBook.creaLibreria(userId.getUserID(), nomeLibreria);
                JOptionPane.showMessageDialog(this, result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void aggiungiLibroLibreria() {
        String nomeLibreria = JOptionPane.showInputDialog(this, "Inserisci il nome della libreria:");
        if (nomeLibreria != null && !nomeLibreria.isEmpty()) {
            String titoloLibro = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro:");
            if (titoloLibro != null && !titoloLibro.isEmpty()) {
                try {
                    String result = interfaceBook.aggiungiLibroLibreria(userId.getUserID(), nomeLibreria, titoloLibro);
                    JOptionPane.showMessageDialog(this, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }

    private void visualizzaLibreria() {
        String nomeLibreria = JOptionPane.showInputDialog(this, "Inserisci il nome della libreria:");
        if (nomeLibreria != null && !nomeLibreria.isEmpty()) {
            try {
                String result = interfaceBook.visualizzaLibriInLibreria(userId.getUserID(), nomeLibreria);
                JOptionPane.showMessageDialog(this, result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void rimuoviLibroLibreria() {
        String nomeLibreria = JOptionPane.showInputDialog(this, "Inserisci il nome della libreria:");
        if (nomeLibreria != null && !nomeLibreria.isEmpty()) {
            String titoloLibro = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro:");
            if (titoloLibro != null && !titoloLibro.isEmpty()) {
                try {
                    String result = interfaceBook.rimuoviLibroLibreria(userId.getUserID(), nomeLibreria, titoloLibro);
                    JOptionPane.showMessageDialog(this, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }
   
    private void inserisciValutazione() {
        String titoloLibro = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro:");
        if (titoloLibro != null && !titoloLibro.isEmpty()) {
            try {
                // Input per le valutazioni
                String stileStr = JOptionPane.showInputDialog(this, "Valutazione Stile (1-5):");
                String contenutoStr = JOptionPane.showInputDialog(this, "Valutazione Contenuto (1-5):");
                String gradevolezzaStr = JOptionPane.showInputDialog(this, "Valutazione Gradevolezza (1-5):");
                String originalitaStr = JOptionPane.showInputDialog(this, "Valutazione Originalità (1-5):");
                String edizioneStr = JOptionPane.showInputDialog(this, "Valutazione Edizione (1-5):");
                
                if (stileStr != null && contenutoStr != null && gradevolezzaStr != null && 
                    originalitaStr != null && edizioneStr != null) {
                    
                    int stile = Integer.parseInt(stileStr);
                    int contenuto = Integer.parseInt(contenutoStr);
                    int gradevolezza = Integer.parseInt(gradevolezzaStr);
                    int originalita = Integer.parseInt(originalitaStr);
                    int edizione = Integer.parseInt(edizioneStr);
                    
                    String result = interfaceBook.inserisciValutazioneLibro(userId.getUserID(), titoloLibro, 
                                                                          String.valueOf(stile), String.valueOf(contenuto), 
                                                                          String.valueOf(gradevolezza), String.valueOf(originalita), 
                                                                          String.valueOf(edizione));
                    JOptionPane.showMessageDialog(this, result);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Errore: Inserisci numeri validi per le valutazioni");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void inserisciConsiglio() {
        String libroReferenziale = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro referenziale:");
        if (libroReferenziale != null && !libroReferenziale.isEmpty()) {
            String libriConsigliati = JOptionPane.showInputDialog(this, "Inserisci i libri consigliati (separati da virgola):");
            if (libriConsigliati != null && !libriConsigliati.isEmpty()) {
                try {
                    String result = interfaceBook.inserisciConsiglioLibro(userId.getUserID(), libroReferenziale, libriConsigliati);
                    JOptionPane.showMessageDialog(this, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }

    private void mostraDialogRecuperaPassword() {
        try{
            JOptionPane.showMessageDialog(this, "Per verificare la tua identità, "+
            "rispondi alla questione di sicurezza configurata durante la registrazione.");
            JOptionPane.showMessageDialog(this, interfaceBook.recuperoQuestione(userId.getUserID()));
            String risposta = JOptionPane.showInputDialog(this, "Inserisci la risposta alla questione di sicurezza:");
            String nuovaPassword = JOptionPane.showInputDialog(this, "Inserisci la nuova password:");
            String result = interfaceBook.recuperaPassword(userId.getUserID(), risposta, nuovaPassword);
            JOptionPane.showMessageDialog(this, result);                            
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
        }
    }

    private void mostraDialogProfilo() {
        try {
            String profilo = interfaceBook.visualizzaProfilo(userId.getUserID());
            JOptionPane.showMessageDialog(this, profilo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
        }
    }

    private void mostraDialogCambiaPassword() {

        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        
        Object[] fields = {
            "Password Attuale:", oldPasswordField,
            "Nuova Password:", newPasswordField,
            "Conferma Nuova Password:", confirmPasswordField
        };
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Cambia Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                if (newPassword.equals(confirmPassword)) {
                    String result = interfaceBook.cambiaPassword(userId.getUserID(), oldPassword, newPassword);
                    JOptionPane.showMessageDialog(this, result);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ ERRORE: Le password non coincidono!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> 
        new BookRecommenderGUI().setVisible(true));
    }
}