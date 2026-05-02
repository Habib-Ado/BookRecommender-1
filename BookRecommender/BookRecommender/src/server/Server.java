package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              -Matricola:     758051      -Sede: VA
 *  * Daniel Viny Kamdem Tagne     -Matricola:     759563      -Sede: VA
 *  * Agnes Balkaire Makouwe       -Matricola:     759700      -Sede: VA
 *  * Maercel Precieux Moukoko     -Matricola:     759674      -Sede: VA
 */
public class Server {

    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "BookRecommender";
    
    public static void main(String[] args) {    
        try {
            System.out.println("=== BookRecommender Server ===");
            System.out.println("Avvio del server...");
            
            // Crea il registry RMI
            Registry registry = LocateRegistry.createRegistry(PORT);
            InterfaceImpl interfaceImpl = new InterfaceImpl();
            registry.rebind(SERVICE_NAME, interfaceImpl);
            
            System.out.println("✅ Server avviato con successo!");
            System.out.println("📍 Server in ascolto alla porta " + PORT);
            System.out.println("🌐 Database connesso correttamente");
            System.out.println("🚀 Pronto per accettare connessioni client...");
            System.out.println("\nPer fermare il server, premi Ctrl+C");
            
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'avvio del server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
