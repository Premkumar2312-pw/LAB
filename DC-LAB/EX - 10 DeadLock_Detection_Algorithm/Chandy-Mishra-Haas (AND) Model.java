import java.util.Scanner;

public class DeadlockDetectionAND {
    static int[][] wfg;   // Wait-For Graph
    static int n;
    // Recursive probe function
    static void detectDeadlockAND(int initiator, int current, boolean[] visited) {
        visited[current] = true;

        for (int next = 0; next < n; next++) {
            if (wfg[current][next] == 1) {
                // Cycle found (probe returned to initiator)
                if (next == initiator) {
                    System.out.println("\n>> [RESULT] Deadlock Detected! Probe returned to P" + initiator);
                    System.exit(0);
                }
                // Forward probe if not visited
                else if (!visited[next]) {
                    System.out.println("P" + current +
                            " sending probe (" + initiator + ", " + current + ", " + next + ") to P" + next);

                    detectDeadlockAND(initiator, next, visited);
                }
                // Already visited node
                else {
                    System.out.println("P" + current +
                            " saw probe (" + initiator + ", " + current + ", " + next +
                            ") but P" + next + " is already part of a known cycle.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Chandy-Misra-Haas (AND Model) ---");

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        wfg = new int[n][n];
        System.out.print("Enter number of edges: ");
        int edges = sc.nextInt();

        System.out.println("Enter edges (u v):");
        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            if (u < n && v < n) {
                wfg[u][v] = 1;
            }
        }

        System.out.print("\nInitiator: ");
        int start = sc.nextInt();
        boolean[] visited = new boolean[n];
        detectDeadlockAND(start, start, visited);
        System.out.println("\n>> [RESULT] No deadlock detected for P" + start);
        sc.close();
    }
}
