import java.net.InetAddress;

public class HostInfo {
    public static void main(String[] args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String ipAddress = localHost.getHostAddress();
            System.out.println("Host Name: " + hostName);
            System.out.println("IP Address: " + ipAddress);
        } catch (Exception e) {
            System.out.println("Error retrieving host information: " + e.getMessage());
        }
    }
}
