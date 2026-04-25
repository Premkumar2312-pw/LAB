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

        // INITIALIZATION
        for (int i = 0; i < P; i++) {
            System.out.println("\nProcess P" + (i + 1));
            System.out.print("Enter number of events: ");
            events[i] = sc.nextInt();

            // First event
            for (int k = 0; k < P; k++)
                clock[i][0][k] = 0;

            clock[i][0][i] = 1;

            // Internal events
            for (int j = 1; j < events[i]; j++) {
                for (int k = 0; k < P; k++)
                    clock[i][j][k] = clock[i][j - 1][k];

                clock[i][j][i] += 1;
            }
        }

        // DISPLAY INITIAL
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

            sp--; se--;
            rp--; re--;

            System.out.print("\nBefore update at P" + (rp + 1) + " E" + (re + 1) + ": ");
            for (int k = 0; k < P; k++)
                System.out.print(clock[rp][re][k] + " ");
            System.out.println();

            System.out.print("Sender clock (P" + (sp + 1) + " E" + (se + 1) + "): ");
            for (int k = 0; k < P; k++)
                System.out.print(clock[sp][se][k] + " ");
            System.out.println();

            // ✅ Correct Receive Rule
            for (int k = 0; k < P; k++) {
                clock[rp][re][k] = Math.max(clock[rp][re][k], clock[sp][se][k]);
            }

            // ✅ Increment own component
            clock[rp][re][rp] += 1;

            System.out.print("After receive update: ");
            for (int k = 0; k < P; k++)
                System.out.print(clock[rp][re][k] + " ");
            System.out.println();

            // Propagate forward
            for (int j = re + 1; j < events[rp]; j++) {
                for (int k = 0; k < P; k++)
                    clock[rp][j][k] = clock[rp][j - 1][k];

                clock[rp][j][rp] += 1;

                System.out.print("Propagated to P" + (rp + 1) + " E" + (j + 1) + ": ");
                for (int k = 0; k < P; k++)
                    System.out.print(clock[rp][j][k] + " ");
                System.out.println();
            }
        }

        // FINAL OUTPUT
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
