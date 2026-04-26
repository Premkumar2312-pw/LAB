import java.util.*;

public class SuzukiKasami {
    static class Token {
        Queue<Integer> queue = new LinkedList<>();
        int[] LN;
        Token(int n) {
            LN = new int[n];
        }
    }
    static int[][] RN;
    static boolean[] hasToken;
    static int numProcesses;
    static int currentTokenHolder;
    static Token systemToken;

    // Display system state
    static void displayStatus() {
        System.out.println("\n--- System State ---");
        System.out.print("Token Holder: P" + currentTokenHolder + " | Token Queue: [");
        for (int p : systemToken.queue) {
            System.out.print(" P" + p);
        }
        System.out.println(" ]");
        System.out.println("Process | RN Array");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + i + " | [");
            for (int j = 0; j < numProcesses; j++) {
                System.out.print(" " + RN[i][j]);
            }
            System.out.println(" ]");
        }

        System.out.print("Token LN: [");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print(" " + systemToken.LN[i]);
        }
        System.out.println(" ]\n--------------------");
    }
    // Execute Critical Section
    static void executeCS(int i) {
        System.out.println(">> [P" + i + "] Entering Critical Section...");
        System.out.println(">> [P" + i + "] Executing... Done.");

        // Update LN
        systemToken.LN[i] = RN[i][i];

        // Check for pending requests
        for (int j = 0; j < numProcesses; j++) {
            if (j == i) continue;
            boolean inQueue = systemToken.queue.contains(j);
            if (RN[i][j] == systemToken.LN[j] + 1 && !inQueue) {
                systemToken.queue.add(j);
                System.out.println(">> [System] Request from P" + j + " added to Token Queue.");
            }
        }
        // Pass token if queue not empty
        if (!systemToken.queue.isEmpty()) {
            int next = systemToken.queue.poll();
            hasToken[i] = false;
            hasToken[next] = true;
            currentTokenHolder = next;
            System.out.println(">> [System] Token passed to P" + next + ".");
            executeCS(next);   // recursive execution
        }
    }

    // Request Token
    static void requestToken(int i) {
        RN[i][i]++;
        System.out.println("\n>> [P" + i + "] Requesting Token (SN: " + RN[i][i] + ")");
        // Broadcast request
        for (int j = 0; j < numProcesses; j++) {
            RN[j][i] = Math.max(RN[j][i], RN[i][i]);
        }
        int holder = currentTokenHolder;
        if (holder == i) {
            System.out.println(">> [P" + i + "] already has the token. Executing...");
            executeCS(i);
        }
        else if (hasToken[holder]) {
            if (RN[holder][i] == systemToken.LN[i] + 1) {
                System.out.println(">> [System] Condition satisfied. Token sent to P" + i);
                hasToken[holder] = false;
                hasToken[i] = true;
                currentTokenHolder = i;
                executeCS(i);
            } else {
                System.out.println(">> [System] Condition not satisfied yet for P" + i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        numProcesses = sc.nextInt();

        RN = new int[numProcesses][numProcesses];
        hasToken = new boolean[numProcesses];
        systemToken = new Token(numProcesses);

        System.out.print("Which process holds the token first? ");
        int startNode = sc.nextInt();

        hasToken[startNode] = true;
        currentTokenHolder = startNode;

        while (true) {
            System.out.print("\n1. Request Token | 2. View Status | 3. Exit\nChoice: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Process ID: ");
                int proc = sc.nextInt();
                if (proc >= 0 && proc < numProcesses)
                    requestToken(proc);
            }
            else if (choice == 2) {
                displayStatus();
            }
            else {
                break;
            }
        }
    }
}
