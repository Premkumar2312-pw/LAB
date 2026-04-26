import java.util.*;
public class MitchellMerritt {
    static int n;
    static int[] sn;                  // sequence numbers
    static List<Integer>[] waitFor;   // dependency list
    static boolean[] visited;
    static boolean deadlockFound = false;

    // DFS-based cycle detection using sequence numbers
    static void detect(int start, int current, int startSN) {
        if (deadlockFound) return;
        visited[current] = true;
        for (int next : waitFor[current]) {
            System.out.println("P" + current + " depends on P" + next +
                    " (SN[" + next + "]=" + sn[next] + ")");
            // If we return to initiator with valid dependency chain
            if (next == start && sn[next] >= startSN) {
                deadlockFound = true;
                System.out.println("\n>> DEADLOCK DETECTED: Cycle returned to P" + start);
                return;
            }

            if (!visited[next]) {
                detect(start, next, startSN);
            }
        }
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        sn = new int[n];
        waitFor = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            waitFor[i] = new ArrayList<>();
        }

        System.out.println("\nEnter initial sequence numbers:");
        for (int i = 0; i < n; i++) {
            sn[i] = sc.nextInt();
        }
        System.out.print("\nEnter number of dependency edges: ");
        int e = sc.nextInt();
        System.out.println("Enter edges (u v) meaning P u waits for P v:");
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            waitFor[u].add(v);
        }
        System.out.print("\nEnter initiator process: ");
        int start = sc.nextInt();
        // Increment SN for initiator (request event)
        sn[start]++;
        visited = new boolean[n];
        System.out.println("\n--- Detection Started ---");
        detect(start, start, sn[start]);
        if (!deadlockFound) {
            System.out.println("\n>> NO DEADLOCK DETECTED");
        }
        sc.close();
    }
}
