import java.util.Scanner;

public class VectorClock {

    static final int MAX_P = 10;
    static final int MAX_E = 20;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int P;
        int[] events = new int[MAX_P];
        int[][][] clock = new int[MAX_P][MAX_E][MAX_P];

        System.out.print("Enter number of processes: ");
        P = sc.nextInt();
        // Initialization
        for (int i = 0; i < P; i++) {
            System.out.print("Events for P" + (i + 1) + ": ");
            events[i] = sc.nextInt();

            clock[i][0][i] = 1;

            for (int j = 1; j < events[i]; j++) {
                for (int k = 0; k < P; k++)
                    clock[i][j][k] = clock[i][j - 1][k];

                clock[i][j][i]++;
            }
        }
        // ✅ Initial Vector Clocks
        System.out.println("\nInitial Vector Clocks:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++) {
                System.out.print("E" + (j + 1) + " -> ");
                for (int k = 0; k < P; k++)
                    System.out.print(clock[i][j][k] + " ");
                System.out.println();
            }
        }

        int msg;
        System.out.print("\nEnter number of messages: ");
        msg = sc.nextInt();
        for (int m = 0; m < msg; m++) {
            int sp, se, rp, re;
            System.out.println("\nMessage " + (m + 1));
            System.out.print("Sender (process event): ");
            sp = sc.nextInt() - 1;
            se = sc.nextInt() - 1;

            System.out.print("Receiver (process event): ");
            rp = sc.nextInt() - 1;
            re = sc.nextInt() - 1;
            // Receive rule
            for (int k = 0; k < P; k++)
                clock[rp][re][k] = Math.max(clock[rp][re][k], clock[sp][se][k]);

            clock[rp][re][rp]++;
            // Propagation
            for (int j = re + 1; j < events[rp]; j++) {
                for (int k = 0; k < P; k++)
                    clock[rp][j][k] = clock[rp][j - 1][k];

                clock[rp][j][rp]++;
            }
        }
        // Final Output
        System.out.println("\nFinal Vector Clocks:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++) {
                System.out.print("E" + (j + 1) + " -> ");
                for (int k = 0; k < P; k++)
                    System.out.print(clock[i][j][k] + " ");
                System.out.println();
            }
        }

        sc.close();
    }
}
