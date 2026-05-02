package server;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/*
 * 
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE              
 */
public class Server {

    private static final String IpAdress = EnvConfig.getRmiHost();
    private static String localIpAddress;
    private static final int TIMEOUT = EnvConfig.getRmiTimeout(); // Timeout in millisecondi per la connessione
    private static final int PORT = EnvConfig.getRmiPort();
    private static final String SERVICE_NAME = EnvConfig.getRmiServiceName();
    
    public static void main(String[] args) {  
        System.setProperty("java.rmi.server.hostname", IpAdress);
        try {
            localIpAddress = java.net.InetAddress.getLocalHost().getHostAddress();
            if (!InetAddress.getByName(IpAdress).isReachable(TIMEOUT)) {
                System.err.println("❌ Errore: L'indirizzo IP " + IpAdress + " non è raggiungibile.");
                System.err.println("Assicurati che il server sia connesso alla rete e che l'indirizzo IP sia corretto.");
                System.exit(1);                
            }
            System.out.println("=== BookRecommender Server ===");
            System.out.println("Avvio del server...");
            
            // Crea il registry RMI
            LocateRegistry.createRegistry(PORT);
            InterfaceImpl interfaceImpl = new InterfaceImpl();
            Naming.rebind("rmi://" + IpAdress + ":" + PORT + "/" + SERVICE_NAME, interfaceImpl);
            
            System.out.println("✅ Server avviato con successo!");
            System.out.println("📍 Server in ascolto alla porta " + PORT);
            System.out.println("📡 Indirizzo IP del server: " + IpAdress);
            System.out.println("📂 Servizio RMI registrato con nome: " + SERVICE_NAME);
            System.out.println("💾 Indirizzo IP locale: " + localIpAddress);
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
