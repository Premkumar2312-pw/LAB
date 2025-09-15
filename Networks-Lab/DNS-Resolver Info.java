import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class DNSInfoResolver {
    public static void main(String[] args) {
        try {
            String dnsServers = System.getProperty("sun.net.spi.nameservice.nameservers");
            if (dnsServers == null || dnsServers.isEmpty()) {
                dnsServers = getDnsFromIpConfig();
            }
            if (dnsServers != null && !dnsServers.isEmpty()) {
                String localDnsIp = dnsServers.split(",")[0].trim();
                InetAddress dnsServer = InetAddress.getByName(localDnsIp);
                String dnsHostName = dnsServer.getHostName();
                System.out.println("Local DNS Server IP: " + localDnsIp);
                System.out.println("Local DNS Server Host Name: " + dnsHostName);
            } else {
                System.out.println("Could not determine local DNS server IP");
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a host name to resolve: ");
            String hostNameToResolve = sc.nextLine();

            InetAddress resolvedAddress = InetAddress.getByName(hostNameToResolve);
            System.out.println("Resolved IP of " + hostNameToResolve + ": " + resolvedAddress.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDnsFromIpConfig() {
        try {
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern dnsPattern = Pattern.compile("DNS Servers[. ]*:\\s*(.*)");
            while ((line = br.readLine()) != null) {
                line = line.trim();
                Matcher m = dnsPattern.matcher(line);
                if (m.find()) {
                    return m.group(1);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
}