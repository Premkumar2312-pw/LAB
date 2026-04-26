import java.util.Scanner;

public class VectorClock {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int P = sc.nextInt();

        int[] events = new int[P];
        int maxE = 0;

        for (int i = 0; i < P; i++) {
            System.out.print("Events for P" + (i + 1) + ": ");
            events[i] = sc.nextInt();
            if (events[i] > maxE) maxE = events[i];
        }

        int[][][] clock = new int[P][maxE][P];

        // Initialization
        for (int i = 0; i < P; i++) {
            clock[i][0][i] = 1;

            for (int j = 1; j < events[i]; j++) {
                for (int k = 0; k < P; k++)
                    clock[i][j][k] = clock[i][j - 1][k];

                clock[i][j][i]++;
            }
        }

        // Initial display
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

        System.out.print("\nEnter number of messages: ");
        int msg = sc.nextInt();

        for (int m = 0; m < msg; m++) {
            System.out.println("\nMessage " + (m + 1));

            System.out.print("Sender (process event): ");
            int sp = sc.nextInt() - 1;
            int se = sc.nextInt() - 1;

            System.out.print("Receiver (process event): ");
            int rp = sc.nextInt() - 1;
            int re = sc.nextInt() - 1;

            for (int k = 0; k < P; k++)
                clock[rp][re][k] = Math.max(clock[rp][re][k], clock[sp][se][k]);

            clock[rp][re][rp]++;

            for (int j = re + 1; j < events[rp]; j++) {
                for (int k = 0; k < P; k++)
                    clock[rp][j][k] = clock[rp][j - 1][k];

                clock[rp][j][rp]++;
            }
        }

        // Final output
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
