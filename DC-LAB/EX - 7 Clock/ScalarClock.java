import java.util.Scanner;

public class ScalarClock {

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

        int[][] clock = new int[P][maxE];

        // Initialization
        for (int i = 0; i < P; i++) {
            clock[i][0] = 1;
            for (int j = 1; j < events[i]; j++)
                clock[i][j] = clock[i][j - 1] + 1;
        }

        // Initial display
        System.out.println("\nInitial Scalar Clocks:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++)
                System.out.println("E" + (j + 1) + " -> " + clock[i][j]);
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

            clock[rp][re] = Math.max(clock[rp][re], clock[sp][se]) + 1;

            for (int j = re + 1; j < events[rp]; j++)
                clock[rp][j] = clock[rp][j - 1] + 1;
        }

        // Final output
        System.out.println("\nFinal Scalar Clocks:");
        for (int i = 0; i < P; i++) {
            System.out.println("P" + (i + 1) + ":");
            for (int j = 0; j < events[i]; j++)
                System.out.println("E" + (j + 1) + " -> " + clock[i][j]);
        }

        sc.close();
    }
}
