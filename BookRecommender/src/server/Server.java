package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;

/*
 *  Classe Server RMI che gestisce la comunicazione remoto
 *  con clienti sulla stessa rete WIFI
 *  PROGETTO REALIZZATO DA:
 *
 *  * MOUHAMMAD TOURE  
 */
public class Server {

    private static final String ipAdress = "192.168.43.94";
    private static String localIp;
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "BookRecommender";
    
    public static void main(String[] args) { 

         // Force la JVM a utilizzare IPv4 per evitare problemi di compatibilità con IPv6
        System.setProperty("java.rmi.server.hostname", "192.168.43.94");
     
        try  {
            localIp = InetAddress.getLocalHost().getHostAddress();

            System.out.println("IP de cette machine: " + localIp);

            // Verifica se l'indirizzo IP è raggiungibile
            if (!InetAddress.getByName(ipAdress).isReachable(5000)) {
                System.err.println("❌ Errore: L'indirizzo IP " + ipAdress + " non è raggiungibile.");
                System.exit(1);
            }
        } catch (UnknownHostException e) {
            System.err.println("❌ Errore: Indirizzo IP sconosciuto - " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("❌ Errore durante la verifica dell'indirizzo IP: " + e.getMessage());
            System.exit(1);
        }
       
        try {
            System.out.println("=== BookRecommender Server ===");
            System.out.println("Avvio del server...");
            
            // Crea il registry RMI
            LocateRegistry.createRegistry(PORT);
            InterfaceImpl interfaceImpl = new InterfaceImpl();
            Naming.rebind("rmi://" + ipAdress + ":" + PORT + "/" + SERVICE_NAME, interfaceImpl);
            
            System.out.println("✅ Server avviato con successo!");
            System.out.println("📍 Server in ascolto alla porta " + PORT);
            System.out.println("Serveur démarré sur: " + ipAdress);
            System.out.println("IP de cette machine: " + localIp);
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
