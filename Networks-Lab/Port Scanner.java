import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PortScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter host (e.g., localhost or IP): ");
        String host = sc.nextLine();

        System.out.print("Enter starting port: ");
        int startPort = sc.nextInt();

        System.out.print("Enter ending port: ");
        int endPort = sc.nextInt();

        System.out.println("\nScanning ports on " + host + " from " + startPort + " to " + endPort + "...\n");

        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket(host, port)) {
                System.out.println("Port " + port + " is OPEN (in use).");
            } catch (IOException e) {
                // Port is closed
            }
        }

        System.out.println("\nScan complete.");
        sc.close();
    }
}