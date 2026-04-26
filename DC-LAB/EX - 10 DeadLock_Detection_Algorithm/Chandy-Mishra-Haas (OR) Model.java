import java.util.Scanner;
public class DeadlockDetectionOR {
    static int[][] wfg;
    static int n;
    static boolean isDeadlockedOR(int current, boolean[] visited) {
        // Check if process has any outgoing edges
        boolean hasSuccessors = false;
        for (int j = 0; j < n; j++) {
            if (wfg[current][j] == 1) {
                hasSuccessors = true;
                break;
            }
        }

        // No outgoing edges → active process
        if (!hasSuccessors) {
            System.out.println(">> P" + current + " is ACTIVE (Not waiting for anyone).");
            return false;
        }

        visited[current] = true;
        // Explore neighbors
        for (int next = 0; next < n; next++) {
            if (wfg[current][next] == 1) {
                System.out.println("P" + current + " sending QUERY to P" + next);
                if (visited[next]) {
                    continue;
                }
                // If any path leads to NO deadlock → return false
                if (!isDeadlockedOR(next, visited)) {
                    System.out.println(">> P" + current +
                            " received REPLY from P" + next + ": Path is CLEAR.");
                    return false;
                }
            }
        }
        return true; // all paths lead to deadlock
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Chandy-Misra-Haas (OR Model) ---");

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

        System.out.print("\nEnter process to initiate detection: ");
        int start = sc.nextInt();

        boolean[] visited = new boolean[n];

        System.out.println("\nInitiating Diffusion Search...\n");

        if (isDeadlockedOR(start, visited)) {
            System.out.println("\n>> [RESULT] DEADLOCK DETECTED for P" + start);
            System.out.println(">> All paths from P" + start +
                    " lead back to blocked processes or cycles.");
        } else {
            System.out.println("\n>> [RESULT] NO DEADLOCK for P" + start);
            System.out.println(">> P" + start +
                    " has at least one path to an active process.");
        }

        sc.close();
    }
}
