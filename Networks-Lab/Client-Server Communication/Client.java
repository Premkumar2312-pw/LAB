import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;

        while (true) {
            try (Socket socket = new Socket(serverAddress, port)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String serverTime = in.readLine();
                System.out.println("Server time: " + serverTime);

                Thread.sleep(1000);
            } catch (IOException e) {
                System.out.println("Server closed. Exiting...");
                break;
            } catch (InterruptedException e) {
                System.err.println("Error: Thread was interrupted.");
            }
        }
    }
}