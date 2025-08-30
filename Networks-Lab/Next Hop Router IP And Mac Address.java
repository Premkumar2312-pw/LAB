import java.io.*;
import java.util.Scanner;

public class NextHopSimple {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a target IP or hostname: ");
        String target = sc.nextLine();

        Process p1 = new ProcessBuilder("tracert", "-d", "-h", "1", target).start();
        BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        String ip = null, line;
        while ((line = r1.readLine()) != null) {
            if (line.contains("1") && line.contains("ms")) {
                for (String part : line.trim().split("\\s+")) {
                    if (part.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                        ip = part;
                        break;
                    }
                }
                if (ip != null) break;
            }
        }
        if (ip == null) {
            System.out.println("Next hop not found.");
            return;
        }

       
        new ProcessBuilder("ping", "-n", "1", ip).start().waitFor();

       
        Process p2 = new ProcessBuilder("arp", "-a").start();
        BufferedReader r2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        String mac = null;
        while ((line = r2.readLine()) != null) {
            if (line.contains(ip)) {
                for (String part : line.trim().split("\\s+")) {
                    if (part.matches("([0-9a-fA-F]{2}-){5}[0-9a-fA-F]{2}")) {
                        mac = part;
                        break;
                    }
                }
                if (mac != null) break;
            }
        }

        System.out.println("Next hop IP: " + ip);
        if (mac != null) {
            System.out.println("Next hop MAC: " + mac);
        } else {
            System.out.println("MAC address not found.");
        }

        sc.close();
    }
}