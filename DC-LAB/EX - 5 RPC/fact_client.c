#include <stdio.h>
#include <stdlib.h>
#include "fact.h"

void fact_prog_1(char *host, int num) {
    CLIENT *clnt;
    output_data *result_1;
    input_data arg;

    clnt = clnt_create(host, FACT_PROG, FACT_VERS, "udp");
    if (clnt == NULL) {
        clnt_pcreateerror(host);
        exit(1);
    }

    arg.n = num;

    result_1 = calculate_factorial_1(&arg, clnt);

    if (result_1 == NULL) {
        clnt_perror(clnt, "RPC call failed");
    } else {
        printf("Factorial of %d is: %ld\n", num, result_1->result);
    }

    clnt_destroy(clnt);
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        printf("Usage: %s <server_ip> <number>\n", argv[0]);
        exit(1);
    }

    fact_prog_1(argv[1], atoi(argv[2]));
    return 0;
}
