package bookrecommender;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


/**
 * Classe principale del server BookRecommender.
 * Si occupa di avviare il server RMI, creare il registry e gestire la connessione al database.
 * Richiede la password del database all'avvio e gestisce eventuali errori.
 * @version 1.0
 * @author Mouhammad Toure
 */
public class Server {

    static final int PORT = 1099;
    private static final String SERVICE_NAME = "BookRecommender";

    /**
     * Metodo principale per avviare il server.
     * @param args
     */
    public static void main(String[] args) {    
        try {
            System.out.println("=== BookRecommender Server ===");
            System.out.println("Avvio del server...");
            
            // Richiedi la password del database all'avvio
            boolean passwordSet = false;
            try (Scanner scanner = new Scanner(System.in)) {
                while (!passwordSet) {
                    passwordSet = BookRecommender.initializeDatabasePassword();
                    if (!passwordSet) {
                        System.out.println("\nVuoi riprovare? (s/n): ");
                        String retry = scanner.nextLine().toLowerCase();
                        if (!retry.equals("s") && !retry.equals("si") && !retry.equals("y") && !retry.equals("yes")) {
                            System.out.println("Server non avviato. Password del database non impostata.");
                            System.exit(1);
                        }
                    }
                }
            }
            
            // Crea il registry RMI
            Registry registry = LocateRegistry.createRegistry(PORT);
            InterfaceImpl interfaceImpl = new InterfaceImpl();
            registry.rebind(SERVICE_NAME, interfaceImpl);
            
            System.out.println("Server avviato con successo!");
            System.out.println("Server in ascolto alla porta " + PORT);
            System.out.println("Database connesso correttamente");
            System.out.println("Pronto per accettare connessioni client...");
            System.out.println("\nPer fermare il server, premi Ctrl+C");
            
        } catch (Exception e) {
            System.err.println("Errore nel server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }   
}
