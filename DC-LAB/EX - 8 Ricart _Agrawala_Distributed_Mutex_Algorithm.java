import java.util.*;

public class RicartAgrawala {

    enum State { IDLE, REQUESTING, EXECUTING }
    static int numProcesses;
    static int[] timestamps;
    static State[] processState;
    static boolean[][] deferredReplies;
    static int[] replyCount;

    // Display system status
    static void displayStatus() {
        System.out.println("\n--- System Status ---");
        System.out.println("PID | State      | Timestamp | Replies");
        for (int i = 0; i < numProcesses; i++) {
            String st = (processState[i] == State.IDLE) ? "IDLE" :
                        (processState[i] == State.REQUESTING) ? "REQUEST" : "EXEC";

            System.out.printf("P%d  | %-10s | %-9d | %d/%d\n",
                    i, st, timestamps[i], replyCount[i], numProcesses - 1);
        }
        System.out.println("---------------------");
    }

    // Send reply
    static void sendReply(int from, int to) {
        System.out.println(">> [P" + from + "] sends REPLY to P" + to);
        replyCount[to]++;

        if (replyCount[to] == numProcesses - 1) {
            processState[to] = State.EXECUTING;
            System.out.println(">> [P" + to + "] received all replies! Entering Critical Section.");
        }
    }

    // Request critical section
    static void requestCS(int i) {
        if (processState[i] != State.IDLE) {
            System.out.println("!! [P" + i + "] is already busy/requesting.");
            return;
        }

        processState[i] = State.REQUESTING;
        timestamps[i]++;
        replyCount[i] = 0;

        System.out.println("\n>> [P" + i + "] requesting CS with Timestamp: " + timestamps[i]);

        for (int j = 0; j < numProcesses; j++) {
            if (i == j) continue;

            if (processState[j] == State.IDLE) {
                sendReply(j, i);
            }
            else if (processState[j] == State.EXECUTING) {
                System.out.println(">> [P" + j + "] is busy. P" + j + " defers reply to P" + i);
                deferredReplies[j][i] = true;
            }
            else if (processState[j] == State.REQUESTING) {
                if (timestamps[i] < timestamps[j] ||
                   (timestamps[i] == timestamps[j] && i < j)) {
                    sendReply(j, i);
                } else {
                    System.out.println(">> [P" + j + "] has higher priority. P" + j + " defers reply to P" + i);
                    deferredReplies[j][i] = true;
                }
            }
        }
    }

    // Release critical section
    static void releaseCS(int i) {
        if (processState[i] != State.EXECUTING) {
            System.out.println("!! [P" + i + "] is not in Critical Section.");
            return;
        }

        System.out.println("\n>> [P" + i + "] Finished Execution. Releasing CS...");
        processState[i] = State.IDLE;

        for (int j = 0; j < numProcesses; j++) {
            if (deferredReplies[i][j]) {
                deferredReplies[i][j] = false;
                sendReply(i, j);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        numProcesses = sc.nextInt();

        timestamps = new int[numProcesses];
        processState = new State[numProcesses];
        replyCount = new int[numProcesses];
        deferredReplies = new boolean[numProcesses][numProcesses];

        // Initialize states
        Arrays.fill(processState, State.IDLE);

        int choice, proc;

        while (true) {
            System.out.print("\n1. Request CS | 2. Release CS | 3. View Status | 4. Exit\nChoice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Process ID: ");
                    proc = sc.nextInt();
                    if (proc >= 0 && proc < numProcesses)
                        requestCS(proc);
                    break;

                case 2:
                    System.out.print("Process ID: ");
                    proc = sc.nextInt();
                    if (proc >= 0 && proc < numProcesses)
                        releaseCS(proc);
                    break;

                case 3:
                    displayStatus();
                    break;

                case 4:
                    System.exit(0);
            }
        }
    }
}
