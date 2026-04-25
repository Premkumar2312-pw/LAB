import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            AddService service = new AddServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("CalcService", service);

            System.out.println("RMI Calculator Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
