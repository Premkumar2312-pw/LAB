import java.util.Scanner;

public class ScalarClock {

    static final int MAX_P = 10;
    static final int MAX_E = 20;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int P;
        int[] events = new int[MAX_P];
        int[][] clock = new int[MAX_P][MAX_E];
        System.out.print("Enter number of processes: ");
        P = sc.nextInt();
        // INITIALIZATION
        for (int i = 0; i < P; i++) {
            System.out.println("\nProcess P" + (i + 1));
            System.out.print("Enter number of events: ");
            events[i] = sc.nextInt();
            // First event
            clock[i][0] = 1;
            // Internal increments
            for (int j = 1; j < events[i]; j++) {
                clock[i][j] = clock[i][j - 1] + 1;
            }
        }
        // DISPLAY INITIAL CLOCKS
        System.out.println("\nInitial Scalar Clocks:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++) {
                System.out.println("E" + (j + 1) + " -> " + clock[i][j]);
            }
        }

        int msg;
        System.out.print("\nEnter number of message transfers: ");
        msg = sc.nextInt();
        for (int m = 0; m < msg; m++) {
            int sp, se, rp, re;

            System.out.println("\nMessage " + (m + 1));
            System.out.print("Sender process number: ");
            sp = sc.nextInt();
            System.out.print("Sender event number: ");
            se = sc.nextInt();
            System.out.print("Receiver process number: ");
            rp = sc.nextInt();
            System.out.print("Receiver event number: ");
            re = sc.nextInt();

            // Convert to 0-based index
            sp--; se--;
            rp--; re--;

            System.out.println("\nBefore update at P" + (rp + 1) +
                               " E" + (re + 1) + ": " + clock[rp][re]);

            System.out.println("Sender clock (P" + (sp + 1) +
                               " E" + (se + 1) + "): " + clock[sp][se]);

            // Lamport receive rule
            if (clock[rp][re] < clock[sp][se]) {
                clock[rp][re] = clock[sp][se];
            }

            clock[rp][re] += 1;

            System.out.println("After receive update: " + clock[rp][re]);

            // Propagate forward
            for (int j = re + 1; j < events[rp]; j++) {
                clock[rp][j] = clock[rp][j - 1] + 1;

                System.out.println("Propagated to P" + (rp + 1) +
                                   " E" + (j + 1) + ": " + clock[rp][j]);
            }
        }

        // FINAL OUTPUT
        System.out.println("\nFinal Scalar Clocks After Synchronization:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++) {
                System.out.println("E" + (j + 1) + " -> " + clock[i][j]);
            }
        }

        sc.close();
    }
}
