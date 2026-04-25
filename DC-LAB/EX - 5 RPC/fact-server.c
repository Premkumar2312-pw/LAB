#include <stdio.h>
#include <stdlib.h>
#include "fact.h"

output_data *calculate_factorial_1_svc(input_data *argp, struct svc_req *rqstp) {
    static output_data result;

    int n = argp->n;
    long f = 1;

    if (n < 0) {
        result.result = -1;
    } else {
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        result.result = f;
    }

    return &result;
}
