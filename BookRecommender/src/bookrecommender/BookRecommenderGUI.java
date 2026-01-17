package bookrecommender;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

/**
 * Interfaccia grafica per il client BookRecommender.
 * Permette agli utenti di interagire con il sistema tramite un'interfaccia utente.
 * Supporta funzionalità di visualizzazione, ricerca, gestione librerie, valutazioni, consigli e gestione account.
 * Utilizza Swing per l'interfaccia grafica e RMI per la comunicazione con il server.
 * @author Mouhammad Toure
 */
public class BookRecommenderGUI extends JFrame {
    static final int PORT = 1099;
    private InterfaceBook interfaceBook;
    private String sessionId; // Session ID per questo client GUI
    private boolean isLoggedIn = false; // Stato di login

    public BookRecommenderGUI() {
        setTitle("Book Recommender - Sistema di Raccomandazione Libri");
        setSize(900, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Imposta un look and feel moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback al look and feel di default
        }
        
        // Imposta colori e stili personalizzati
        setupCustomStyles();

        // Genera un Session ID univoco per questo client GUI
        this.sessionId = UUID.randomUUID().toString();

        // Connessione RMI
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", PORT);
            interfaceBook = (InterfaceBook) registry.lookup("BookRecommender");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server RMI: " + e.getMessage());
            System.exit(1);
        }
        if(!isLoggedIn){
            mostraMenuPrincipale();
        } else {
            mostraMenuUtenteLoggato();
        }
    }

    /**
     * Configura gli stili personalizzati per la GUI
     */
    private void setupCustomStyles() {
        // Colori del tema
        Color primaryColor = new Color(52, 152, 219);      // Blu principale
        Color secondaryColor = new Color(46, 204, 113);    // Verde
        Color accentColor = new Color(155, 89, 182);       // Viola
        Color dangerColor = new Color(231, 76, 60);        // Rosso
        Color warningColor = new Color(241, 196, 15);      // Giallo
        Color backgroundColor = new Color(248, 249, 250);  // Grigio chiaro
        Color cardColor = Color.WHITE;                     // Bianco per le card
        
        // Imposta il colore di sfondo della finestra
        getContentPane().setBackground(backgroundColor);
        
        // Configura i colori per i componenti Swing
        UIManager.put("Button.background", primaryColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.border", BorderFactory.createRaisedBevelBorder());
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
        
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 11));
        UIManager.put("Label.foreground", new Color(44, 62, 80));
        
        UIManager.put("Panel.background", backgroundColor);
        
        UIManager.put("ScrollPane.background", backgroundColor);
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
    }

    private void mostraMenuPrincipale() {
        getContentPane().removeAll();
        setTitle("Book Recommender - Menu Principale");

        // Pannello principale con sfondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header con titolo
        JPanel headerPanel = createHeaderPanel("Book Recommender", "Sistema di Raccomandazione Libri", 
                                              "Menu Principale - Accesso Pubblico");

        // Pannello centrale con i pulsanti
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Sezione Visualizzazione e Ricerca
        JButton mainVisualizzaButton = createStyledButton(
            "Visualizza Libri", "Visualizza tutti i libri disponibili", new Color(52, 152, 219));
        JButton mainTitoloButton = createStyledButton(
            "Cerca per Titolo", "Trova libri per titolo", new Color(46, 204, 113));
        JButton mainAutoreButton = createStyledButton(
            "Cerca per Autore", "Trova libri per autore", new Color(155, 89, 182));
        JButton mainAutoreAnnoButton = createStyledButton(
            "Cerca per Autore e Anno", "Trova libri specifici", new Color(241, 196, 15));

        mainVisualizzaButton.setForeground(Color.BLACK);
        mainTitoloButton.setForeground(Color.BLACK);
        mainAutoreButton.setForeground(Color.BLACK);
        mainAutoreAnnoButton.setForeground(Color.BLACK);

        JPanel searchSection = createSectionPanel("VISUALIZZAZIONE E RICERCA", 
            mainVisualizzaButton,
            mainTitoloButton,
            mainAutoreButton,
            mainAutoreAnnoButton
        );

        // Sezione Account
        JButton mainRegistrazioneButton = createStyledButton(
            "Registrazione", "Crea un nuovo account", new Color(46, 204, 113));
        JButton mainLoginButton = createStyledButton(
            "Login", "Accedi al tuo account", new Color(52, 152, 219));
        JButton mainRecuperaPasswordButton = createStyledButton(
            "Recupera Password", "Recupera la tua password", new Color(155, 89, 182));
        JButton mainEsciButton = createStyledButton(
            "Esci", "Chiudi l'applicazione", new Color(231, 76, 60));

        mainRegistrazioneButton.setForeground(Color.BLACK);
        mainLoginButton.setForeground(Color.BLACK);
        mainRecuperaPasswordButton.setForeground(Color.BLACK);
        mainEsciButton.setForeground(Color.BLACK);

        JPanel accountSection = createSectionPanel("GESTIONE ACCOUNT",
            mainRegistrazioneButton,
            mainLoginButton,
            mainRecuperaPasswordButton,
            mainEsciButton
        );

        // Layout delle sezioni
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 1.0;
        centerPanel.add(searchSection, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        centerPanel.add(accountSection, gbc);

        // Footer con informazioni
        JPanel footerPanel = createFooterPanel("Consiglio: Puoi visualizzare e cercare libri senza registrarti!");

        // Assembla il layout principale
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Configura gli event listeners per i pulsanti
        setupButtonListeners(searchSection, accountSection);
        
        revalidate();
        repaint();
    }

    /**
     * Crea un pannello header con titolo e sottotitolo
     */
    private JPanel createHeaderPanel(String title, String subtitle, String description) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(149, 165, 166));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        textPanel.setBackground(new Color(248, 249, 250));
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        textPanel.add(descLabel);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        return headerPanel;
    }

    /**
     * Crea un pannello sezione con titolo e pulsanti
     */
    private JPanel createSectionPanel(String sectionTitle, JButton... buttons) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Titolo della sezione
        JLabel sectionLabel = new JLabel(sectionTitle);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sectionLabel.setForeground(new Color(44, 62, 80));
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(buttons.length, 1, 8, 8));
        buttonPanel.setBackground(Color.WHITE);
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        sectionPanel.add(sectionLabel, BorderLayout.NORTH);
        sectionPanel.add(buttonPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    /**
     * Crea un pulsante stilizzato
     */
    private JButton createStyledButton(String text, String tooltip, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        
        // Effetto hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    /**
     * Crea un pannello footer con informazioni
     */
    private JPanel createFooterPanel(String message) {
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel footerLabel = new JLabel(message);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerLabel.setForeground(new Color(127, 140, 141));
        
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    /**
     * Crea un campo di testo stilizzato
     */
    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return textField;
    }

    /**
     * Crea un campo password stilizzato
     */
    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return passwordField;
    }

    /**
     * Configura gli event listeners per tutti i pulsanti
     */
    private void setupButtonListeners(JPanel searchSection, JPanel accountSection) {
        // Trova i pulsanti nelle sezioni e assegna gli event listeners
        JPanel searchButtonPanel = (JPanel) searchSection.getComponent(1);
        JPanel accountButtonPanel = (JPanel) accountSection.getComponent(1);
        
        Component[] searchButtons = searchButtonPanel.getComponents();
        Component[] accountButtons = accountButtonPanel.getComponents();

        // Pulsanti di ricerca
        if (searchButtons.length >= 4) {
            ((JButton)searchButtons[0]).addActionListener(e -> mostraLibri());
            ((JButton)searchButtons[1]).addActionListener(e -> cercaLibroPerTitolo());
            ((JButton)searchButtons[2]).addActionListener(e -> cercaLibroPerAutore());
            ((JButton)searchButtons[3]).addActionListener(e -> cercaLibroPerAutoreAnno());
        }

        // Pulsanti di account
        if (accountButtons.length >= 4) {
            ((JButton)accountButtons[0]).addActionListener(e -> mostraDialogRegistrazione());
            ((JButton)accountButtons[1]).addActionListener(e -> mostraDialogLogin());
            ((JButton)accountButtons[2]).addActionListener(e -> mostraDialogRecuperaPassword());
            ((JButton)accountButtons[3]).addActionListener(e -> System.exit(0));
        }
    }

    private void mostraMenuUtenteLoggato() {
        getContentPane().removeAll();
        setTitle("Book Recommender - Menu Utente Loggato");
        
        // Pannello principale con sfondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header con titolo
        JPanel headerPanel = createHeaderPanel("Book Recommender", "Sistema di Raccomandazione Libri", 
                                              "Menu Utente - Funzionalità Complete");

        // Pannello centrale con scroll
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Sezione Visualizzazione e Ricerca
        JButton loggedVisualizzaButton = createStyledButton(
            "Visualizza Tutti i Libri", "Visualizza tutti i libri disponibili", new Color(52, 152, 219));
        JButton loggedTitoloButton = createStyledButton(
            "Cerca per Titolo", "Trova libri per titolo", new Color(46, 204, 113));
        JButton loggedAutoreButton = createStyledButton(
            "Cerca per Autore", "Trova libri per autore", new Color(155, 89, 182));
        JButton loggedAutoreAnnoButton = createStyledButton(
            "Cerca per Autore e Anno", "Trova libri specifici", new Color(241, 196, 15));

        loggedVisualizzaButton.setForeground(Color.BLACK);
        loggedTitoloButton.setForeground(Color.BLACK);
        loggedAutoreButton.setForeground(Color.BLACK);
        loggedAutoreAnnoButton.setForeground(Color.BLACK);

        JPanel searchSection = createSectionPanel("VISUALIZZAZIONE E RICERCA", 
            loggedVisualizzaButton,
            loggedTitoloButton,
            loggedAutoreButton,
            loggedAutoreAnnoButton
        );

        // Sezione Gestione Librerie
        JButton loggedCreaLibreriaButton = createStyledButton(
            "Crea Nuova Libreria", "Crea una nuova libreria personale", new Color(46, 204, 113));
        JButton loggedAggiungiLibroButton = createStyledButton(
            "Aggiungi Libro", "Aggiungi un libro alla tua libreria", new Color(52, 152, 219));
        JButton loggedVisualizzaLibreriaButton = createStyledButton(
            "Visualizza Libreria", "Visualizza i libri nella tua libreria", new Color(155, 89, 182));
        JButton loggedRimuoviLibroButton = createStyledButton(
            "Rimuovi Libro", "Rimuovi un libro dalla tua libreria", new Color(231, 76, 60));

        loggedCreaLibreriaButton.setForeground(Color.BLACK);
        loggedAggiungiLibroButton.setForeground(Color.BLACK);
        loggedVisualizzaLibreriaButton.setForeground(Color.BLACK);
        loggedRimuoviLibroButton.setForeground(Color.BLACK);

        JPanel librarySection = createSectionPanel("GESTIONE LIBRERIE",
            loggedCreaLibreriaButton,
            loggedAggiungiLibroButton,
            loggedVisualizzaLibreriaButton,
            loggedRimuoviLibroButton
        );

        // Sezione Valutazioni e Consigli
        JButton loggedValutazioneButton = createStyledButton(
            "Inserisci Valutazione", "Valuta un libro che hai letto", new Color(241, 196, 15));
        JButton loggedConsiglioButton = createStyledButton(
            "Inserisci Consiglio", "Consiglia libri ad altri utenti", new Color(155, 89, 182));

        loggedValutazioneButton.setForeground(Color.BLACK);
        loggedConsiglioButton.setForeground(Color.BLACK);

        JPanel ratingSection = createSectionPanel("VALUTAZIONI E CONSIGLI",
            loggedValutazioneButton,
            loggedConsiglioButton
        );

        // Sezione Account
        JButton loggedLogoutButton = createStyledButton(
            "Logout", "Esci dal tuo account", new Color(231, 76, 60));
        JButton loggedEsciButton = createStyledButton(
            "Esci", "Chiudi l'applicazione", new Color(44, 62, 80));

        loggedLogoutButton.setForeground(Color.BLACK);
        loggedEsciButton.setForeground(Color.BLACK);

        JPanel accountSection = createSectionPanel("GESTIONE ACCOUNT",
            //createStyledButton("Torna al Menu Principale", "Torna al menu pubblico", new Color(127, 140, 141)),
            loggedLogoutButton,
            loggedEsciButton
        );

        // Layout delle sezioni
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 1.0;
        centerPanel.add(searchSection, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weighty = 1.0;
        centerPanel.add(librarySection, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        centerPanel.add(ratingSection, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.weighty = 1.0;
        centerPanel.add(accountSection, gbc);

        // Footer con informazioni
        JPanel footerPanel = createFooterPanel("Benvenuto! Ora hai accesso a tutte le funzionalità avanzate!");

        // Assembla il layout principale
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Configura gli event listeners per i pulsanti
        setupLoggedInButtonListeners(searchSection, librarySection, ratingSection, accountSection);
        
        revalidate();
        repaint();
    }

    /**
     * Configura gli event listeners per il menu utente loggato
     */
    private void setupLoggedInButtonListeners(JPanel searchSection, JPanel librarySection, 
                                            JPanel ratingSection, JPanel accountSection) {
        
        // Pulsanti di ricerca
        JPanel searchButtonPanel = (JPanel) searchSection.getComponent(1);
        Component[] searchButtons = searchButtonPanel.getComponents();
        if (searchButtons.length >= 4) {
            ((JButton)searchButtons[0]).addActionListener(e -> mostraLibri());
            ((JButton)searchButtons[1]).addActionListener(e -> cercaLibroPerTitolo());
            ((JButton)searchButtons[2]).addActionListener(e -> cercaLibroPerAutore());
            ((JButton)searchButtons[3]).addActionListener(e -> cercaLibroPerAutoreAnno());
        }

        // Pulsanti di libreria
        JPanel libraryButtonPanel = (JPanel) librarySection.getComponent(1);
        Component[] libraryButtons = libraryButtonPanel.getComponents();
        if (libraryButtons.length >= 4) {
            ((JButton)libraryButtons[0]).addActionListener(e -> creaLibreria());
            ((JButton)libraryButtons[1]).addActionListener(e -> aggiungiLibroLibreria());
            ((JButton)libraryButtons[2]).addActionListener(e -> visualizzaLibreria());
            ((JButton)libraryButtons[3]).addActionListener(e -> rimuoviLibroLibreria());
        }

        // Pulsanti di valutazione
        JPanel ratingButtonPanel = (JPanel) ratingSection.getComponent(1);
        Component[] ratingButtons = ratingButtonPanel.getComponents();
        if (ratingButtons.length >= 2) {
            ((JButton)ratingButtons[0]).addActionListener(e -> inserisciValutazione());
            ((JButton)ratingButtons[1]).addActionListener(e -> inserisciConsiglio());
        }

        // Pulsanti di account
        JPanel accountButtonPanel = (JPanel) accountSection.getComponent(1);
        Component[] accountButtons = accountButtonPanel.getComponents();
        if (accountButtons.length == 2) {
            ((JButton)accountButtons[0]).addActionListener(e -> logout());
            ((JButton)accountButtons[1]).addActionListener(e -> System.exit(0));
        } else if (accountButtons.length >= 3) {
            ((JButton)accountButtons[0]).addActionListener(e -> logout());
            ((JButton)accountButtons[1]).addActionListener(e -> {
                isLoggedIn = false;
                mostraMenuPrincipale();
            });
            ((JButton)accountButtons[2]).addActionListener(e -> System.exit(0));
        }
    }

    private void mostraDialogLogin() {
        // Crea un pannello personalizzato per il login
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header del login
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("LOGIN");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(52, 152, 219));
        headerPanel.add(headerLabel);

        // Pannello dei campi
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo Username
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField userField = new JTextField(15);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        fieldsPanel.add(userField, gbc);

        // Campo Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPasswordField passField = new JPasswordField(15);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        fieldsPanel.add(passField, gbc);

        // Footer con info
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("Inserisci le tue credenziali per accedere");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerPanel.add(footerLabel);

        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);
        loginPanel.add(footerPanel, BorderLayout.SOUTH);

        int option = JOptionPane.showConfirmDialog(this, loginPanel, "Accesso al Sistema", 
                                                  JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String result = interfaceBook.login(sessionId, userField.getText(), new String(passField.getPassword()));
                JOptionPane.showMessageDialog(this, result);
                if (result.startsWith("OK:")) {
                    isLoggedIn = true;
                    mostraMenuUtenteLoggato();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void mostraDialogRegistrazione() {
        // Crea un pannello personalizzato per la registrazione
        JPanel regPanel = new JPanel(new BorderLayout());
        regPanel.setBackground(Color.WHITE);
        regPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header della registrazione
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("REGISTRAZIONE");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(46, 204, 113));
        headerPanel.add(headerLabel);

        // Pannello dei campi con scroll
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome e Cognome
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Nome e Cognome:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField nameField = createStyledTextField(20);
        fieldsPanel.add(nameField, gbc);

        // Codice Fiscale
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel cfLabel = new JLabel("Codice Fiscale:");
        cfLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        cfLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(cfLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField cfField = createStyledTextField(20);
        fieldsPanel.add(cfField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        emailLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField emailField = createStyledTextField(20);
        fieldsPanel.add(emailField, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField userField = createStyledTextField(20);
        fieldsPanel.add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(new Color(44, 62, 80));
        fieldsPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPasswordField passField = createStyledPasswordField(20);
        fieldsPanel.add(passField, gbc);

        // Footer con info
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("Inserisci i tuoi dati per creare un nuovo account");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerPanel.add(footerLabel);

        regPanel.add(headerPanel, BorderLayout.NORTH);
        regPanel.add(fieldsPanel, BorderLayout.CENTER);
        regPanel.add(footerPanel, BorderLayout.SOUTH);

        int option = JOptionPane.showConfirmDialog(this, regPanel, "Registrazione Nuovo Account", 
                                                  JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String result = interfaceBook.registrazione(sessionId, nameField.getText(), cfField.getText(), 
                                                         emailField.getText(), userField.getText(), new String(passField.getPassword()));
                JOptionPane.showMessageDialog(this, result);
                if (result.startsWith("OK:")) {
                    isLoggedIn = true;
                    mostraMenuUtenteLoggato();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void logout() {
        try {
            String result = interfaceBook.logout(sessionId);
            JOptionPane.showMessageDialog(this, result);
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
            
            // Verifica la dimensione del testo per evitare OutOfMemoryError
            if (libri.length() > 100000) { // Se il testo è molto grande (>100KB)
                mostraLibriPaginati(libri);
            } else {
                mostraLibriNormali(libri);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            String errorMessage = "Errore durante il caricamento dei libri:\n" + ex.getMessage() + 
                "\n\nPOSSIBILI SOLUZIONI:\n" +
                "1. Verifica che il server RMI sia in esecuzione\n" +
                "2. Controlla che PostgreSQL sia attivo\n" +
                "3. Assicurati che la password del database sia corretta\n" +
                "4. Verifica che il database sia stato inizializzato\n" +
                "5. Controlla che ci siano libri nel database\n" +
                "\nPROCEDURA DI AVVIO:\n" +
                "1. Esegui: setup_database.bat\n" +
                "2. Esegui: populate_db.bat\n" +
                "3. Esegui: start_server.bat\n" +
                "4. Esegui: start_gui.bat"+
                "\n\n Oppure esegui: run_all.bat";
            
            JOptionPane.showMessageDialog(this, errorMessage, "Errore Caricamento Libri", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostra i libri in formato normale per dataset piccoli
     */
    private void mostraLibriNormali(String libri) {
        // Crea una finestra più grande e meglio formattata
        JFrame frame = new JFrame("Libri Disponibili nel Sistema");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(this);
        
        // Pannello principale con sfondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel headerLabel = new JLabel("CATALOGO LIBRI");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(52, 152, 219));
        headerPanel.add(headerLabel);

        // Area di testo con stile moderno
        JTextArea area = new JTextArea(libri);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Consolas", Font.PLAIN, 11));
        area.setBackground(Color.WHITE);
        area.setForeground(new Color(44, 62, 80));
        area.setCaretColor(new Color(52, 152, 219));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scroll.setBackground(new Color(248, 249, 250));

        // Footer con statistiche
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        JLabel footerLabel = new JLabel("Usa Ctrl+F per cercare nel testo | Dataset piccolo - Visualizzazione completa");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerPanel.add(footerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        
        // Mostra un messaggio di successo
        JOptionPane.showMessageDialog(this, "Libri caricati con successo!", "Catalogo Caricato", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Mostra i libri con paginazione per dataset grandi
     */
    private void mostraLibriPaginati(String libri) {
        // Divide il testo in pagine più piccole
        String[] lines = libri.split("\n");
        int linesPerPage = 50; // 50 righe per pagina
        int totalPages = (lines.length + linesPerPage - 1) / linesPerPage;
        
        JFrame frame = new JFrame("Libri Disponibili nel Sistema (Paginati)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(this);
        
        // Pannello principale con sfondo
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel headerLabel = new JLabel("CATALOGO LIBRI - VISUALIZZAZIONE PAGINATA");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(new Color(52, 152, 219));
        headerPanel.add(headerLabel);
        
        // Area di testo per i libri con stile moderno
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Consolas", Font.PLAIN, 11));
        area.setBackground(Color.WHITE);
        area.setForeground(new Color(44, 62, 80));
        area.setCaretColor(new Color(52, 152, 219));
        
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scroll.setBackground(new Color(248, 249, 250));
        
        // Pannello di controllo per la paginazione
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel pageLabel = new JLabel("Pagina:");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pageLabel.setForeground(new Color(44, 62, 80));
        
        JLabel currentPageLabel = new JLabel("1");
        currentPageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        currentPageLabel.setForeground(new Color(52, 152, 219));
        
        JLabel totalPagesLabel = new JLabel("di " + totalPages);
        totalPagesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalPagesLabel.setForeground(new Color(127, 140, 141));
        
        JButton firstButton = createStyledButton("⏮ Prima", "Vai alla prima pagina", new Color(52, 152, 219));
        JButton prevButton = createStyledButton("◀ Precedente", "Vai alla pagina precedente", new Color(46, 204, 113));
        JButton nextButton = createStyledButton("Successiva ▶", "Vai alla pagina successiva", new Color(155, 89, 182));
        JButton lastButton = createStyledButton("Ultima ⏭", "Vai all'ultima pagina", new Color(241, 196, 15));
        
        controlPanel.add(firstButton);
        controlPanel.add(prevButton);
        controlPanel.add(pageLabel);
        controlPanel.add(currentPageLabel);
        controlPanel.add(totalPagesLabel);
        controlPanel.add(nextButton);
        controlPanel.add(lastButton);
        
        // Pannello di informazioni
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setBackground(new Color(248, 249, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel infoLabel = new JLabel("Totale libri: " + lines.length + " | Pagine: " + totalPages + " | Righe per pagina: " + linesPerPage);
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        infoLabel.setForeground(new Color(127, 140, 141));
        infoPanel.add(infoLabel);
        
        // Variabile per tracciare la pagina corrente
        final int[] currentPage = {1};
        
        // Funzione per aggiornare la pagina
        Runnable updatePage = () -> {
            int start = (currentPage[0] - 1) * linesPerPage;
            int end = Math.min(start + linesPerPage, lines.length);
            
            StringBuilder pageContent = new StringBuilder();
            pageContent.append("=== PAGINA ").append(currentPage[0]).append(" DI ").append(totalPages).append(" ===\n");
            pageContent.append("Libri ").append(start + 1).append("-").append(end).append(" di ").append(lines.length).append("\n");
            pageContent.append("=".repeat(50)).append("\n\n");
            
            for (int i = start; i < end; i++) {
                pageContent.append(lines[i]).append("\n");
            }
            
            area.setText(pageContent.toString());
            currentPageLabel.setText(String.valueOf(currentPage[0]));
            
            // Abilita/disabilita i pulsanti
            prevButton.setEnabled(currentPage[0] > 1);
            firstButton.setEnabled(currentPage[0] > 1);
            nextButton.setEnabled(currentPage[0] < totalPages);
            lastButton.setEnabled(currentPage[0] < totalPages);
        };
        
        // Event listeners per i pulsanti
        prevButton.addActionListener(e -> {
            if (currentPage[0] > 1) {
                currentPage[0]--;
                updatePage.run();
            }
        });
        
        nextButton.addActionListener(e -> {
            if (currentPage[0] < totalPages) {
                currentPage[0]++;
                updatePage.run();
            }
        });
        
        firstButton.addActionListener(e -> {
            currentPage[0] = 1;
            updatePage.run();
        });
        
        lastButton.addActionListener(e -> {
            currentPage[0] = totalPages;
            updatePage.run();
        });
        
        // Pannello inferiore per controlli + info
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(248, 249, 250));
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(infoPanel, BorderLayout.SOUTH);

        // Aggiungi i componenti al frame
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.getContentPane().add(mainPanel);
        
        // Carica la prima pagina
        updatePage.run();
        
        frame.setVisible(true);
        
        // Mostra un messaggio di successo
        JOptionPane.showMessageDialog(this, 
            "Libri caricati con successo!\n\n" +
            "Totale: " + lines.length + " libri\n" +
            "Visualizzazione paginata: " + totalPages + " pagine\n" +
            "Usa i pulsanti per navigare tra le pagine", 
            "Successo", JOptionPane.INFORMATION_MESSAGE);
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
                String result = interfaceBook.creaLibreria(sessionId, nomeLibreria);
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
                    String result = interfaceBook.aggiungiLibroLibreria(sessionId, nomeLibreria, titoloLibro);
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
                String result = interfaceBook.visualizzaLibreria(sessionId, nomeLibreria);
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
                    String result = interfaceBook.rimuoviLibroLibreria(sessionId, nomeLibreria, titoloLibro);
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
                    
                    String result = interfaceBook.inserisciValutazioneLibro(sessionId, titoloLibro, 
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
                    String result = interfaceBook.inserisciConsiglioLibro(sessionId, libroReferenziale, libriConsigliati);
                    JOptionPane.showMessageDialog(this, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }

    private void mostraDialogRecuperaPassword() {
        String username = JOptionPane.showInputDialog(this, "Inserisci l'username per il recupero password:");
        if (username != null && !username.isEmpty()) {
            try {
                String[] options = {"Visualizza Informazioni", "Genera Password Temporanea", "Cambia Password"};
                int choice = JOptionPane.showOptionDialog(this, 
                    "Scegli un'opzione per il recupero password:", 
                    "Recupero Password", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    options, 
                    options[0]);
                
                if (choice == 0) {
                    String result = interfaceBook.recuperaPassword(username);
                    JOptionPane.showMessageDialog(this, result);
                } else if (choice == 1) {
                    String result = interfaceBook.generaPasswordTemporanea(username);
                    JOptionPane.showMessageDialog(this, result);
                } else if (choice == 2) {
                    mostraDialogCambiaPassword();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    private void mostraDialogCambiaPassword() {
        JTextField usernameField = new JTextField();
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        
        Object[] fields = {
            "Username:", usernameField,
            "Password Attuale:", oldPasswordField,
            "Nuova Password:", newPasswordField,
            "Conferma Nuova Password:", confirmPasswordField
        };
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Cambia Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String username = usernameField.getText();
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                if (newPassword.equals(confirmPassword)) {
                    String result = interfaceBook.cambiaPassword(sessionId, username, oldPassword, newPassword);
                    JOptionPane.showMessageDialog(this, result);
                } else {
                    JOptionPane.showMessageDialog(this, "ERRORE: Le password non coincidono!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookRecommenderGUI().setVisible(true));
    }
}