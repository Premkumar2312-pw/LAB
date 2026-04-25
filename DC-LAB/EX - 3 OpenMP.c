#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
    int start, end;
    int i, j;
    int sum = 0;
    int global_count = 0;
    printf("Enter starting number: ");
    scanf("%d", &start);
    printf("Enter ending number: ");
    scanf("%d", &end);
    // Set OpenMP behavior
    omp_set_dynamic(0);
    omp_set_num_threads(4);

    printf("\nMaximum threads available: %d\n", omp_get_max_threads());
    #pragma omp parallel private(i, j) reduction(+:sum) shared(start, end, global_count)
    {
        int tid = omp_get_thread_num();
        // Print total threads once
        #pragma omp single
        {
            printf("\nTotal threads working: %d\n", omp_get_num_threads());
        }
        // Parallel loop
        #pragma omp for schedule(dynamic)
        for (i = start; i <= end; i++) {
            printf("\nThread %d printing table of %d\n", tid, i);
            for (j = 1; j <= 10; j++) {
                int result = i * j;
                // Atomic update
                #pragma omp atomic
                global_count++;
                // Critical section for printing
                #pragma omp critical
                {
                    printf("%d x %d = %d\n", i, j, result);
                }
                // Reduction variable
                sum += result;
            }
        }
        // Sections demonstration
        #pragma omp sections
        {
            #pragma omp section
            {
                printf("\nThread %d finished section 1\n", tid);
            }

            #pragma omp section
            {
                printf("Thread %d finished section 2\n", tid);
            }
        }
    }

    printf("\nTotal multiplication sum = %d\n", sum);
    printf("Total operations counted = %d\n", global_count);

    return 0;
}
