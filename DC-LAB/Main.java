import java.util.*;

public class Main {
    static int n;
    static int[][] W;
    static boolean deadlock = false;

    static void probe(int initiator, int sender, int receiver) {
        if (deadlock) return;

        // If probe comes back to initiator
        if (receiver == initiator) {
            deadlock = true;
            return;
        }

        // Forward probe to dependent processes
        for (int k = 0; k < n; k++) {
            if (W[receiver][k] == 1) {
                probe(initiator, receiver, k);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        W = new int[n][n];

        System.out.println("Enter Wait-For Graph matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                W[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter initiator process (0 to " + (n-1) + "): ");
        int initiator = sc.nextInt();

        // Send initial probes
        for (int j = 0; j < n; j++) {
            if (W[initiator][j] == 1) {
                probe(initiator, initiator, j);
            }
        }

        if (deadlock)
            System.out.println("Deadlock Detected");
        else
            System.out.println("No Deadlock");

        sc.close();
    }
}
