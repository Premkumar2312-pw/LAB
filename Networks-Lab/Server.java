import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000;
        int clientCount = 0;
        int maxClients = 10;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Time Server is running on port " + port);

            while (clientCount < maxClients) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                System.out.println("Client " + clientCount + " connected: " + clientSocket.getInetAddress());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Current time: " + new java.util.Date());

                clientSocket.close();
            }

            System.out.println("Server has served " + maxClients + " clients. Server is closing.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}