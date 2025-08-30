import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSResolver {
    public static void main(String[] args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Local Hostname: " + localHost.getHostName());
            System.out.println("Local IP Address: " + localHost.getHostAddress());

            System.out.print("Enter a hostname to resolve: ");
            String hostname = new java.util.Scanner(System.in).nextLine();

            InetAddress[] addresses = InetAddress.getAllByName(hostname);

            System.out.println("IP address(es) for " + hostname + ":");
            for (InetAddress addr : addresses) {
                String ipType = addr.getHostAddress().contains(":") ? "IPv6" : "IPv4";
                System.out.println(" - " + addr.getHostAddress() + " (" + ipType + ")");
            }

        } catch (UnknownHostException e) {
            System.out.println("Error: Unable to resolve hostname.");
        }
    }
}