import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Create service object
            AddService service = new AddServiceImpl();

            // Connect to already running registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Bind service
            registry.rebind("CalcService", service);

            System.out.println("RMI Calculator Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
