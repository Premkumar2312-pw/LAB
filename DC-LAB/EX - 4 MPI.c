#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>

#define REQ_TAG 1
#define REP_TAG 2
int main(int argc, char **argv) {
    int rank, size;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int my_timestamp = 0;
    int replies_received = 0;
    bool requesting = false;
    // 🔹 Process 1 requests Critical Section
    if (rank == 1) {
        requesting = true;
        my_timestamp = 10;
        printf(">> [P%d] Requesting CS with Timestamp %d\n", rank, my_timestamp);
        // Send request to all other processes
        for (int i = 0; i < size; i++) {
            if (i != rank) {
                MPI_Send(&my_timestamp, 1, MPI_INT, i, REQ_TAG, MPI_COMM_WORLD);
            }
        }
        // Wait for replies
        while (replies_received < size - 1) {
            int dummy;
            MPI_Recv(&dummy, 1, MPI_INT, MPI_ANY_SOURCE, REP_TAG,
                     MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            replies_received++;
            printf(">> [P%d] Received Reply %d/%d\n",
                   rank, replies_received, size - 1);
        }
        // Enter Critical Section
        printf(">> [P%d] Entering CRITICAL SECTION!\n", rank);
        sleep(2);
        printf(">> [P%d] Leaving CRITICAL SECTION.\n", rank);
    }
    // 🔹 Other processes respond
    else {
        int incoming_ts;
        MPI_Status status;
        MPI_Recv(&incoming_ts, 1, MPI_INT, MPI_ANY_SOURCE,
                 REQ_TAG, MPI_COMM_WORLD, &status);

        int requester = status.MPI_SOURCE;
        printf(">> [P%d] Received Request from P%d (TS: %d). Sending Reply...\n",
               rank, requester, incoming_ts);
        int reply = 1;
        MPI_Send(&reply, 1, MPI_INT, requester, REP_TAG, MPI_COMM_WORLD);
    }
    MPI_Finalize();
    return 0;
}
