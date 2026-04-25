import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            AddService service = (AddService) registry.lookup("CalcService");

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n1.Add  2.Sub  3.Mul  4.Div  5.Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                if (ch == 5) break;

                System.out.print("Enter a: ");
                int a = sc.nextInt();
                System.out.print("Enter b: ");
                int b = sc.nextInt();

                switch (ch) {
                    case 1:
                        System.out.println("Result: " + service.add(a, b));
                        break;
                    case 2:
                        System.out.println("Result: " + service.sub(a, b));
                        break;
                    case 3:
                        System.out.println("Result: " + service.mul(a, b));
                        break;
                    case 4:
                        System.out.println("Result: " + service.div(a, b));
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
