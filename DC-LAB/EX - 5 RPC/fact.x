struct input_data {
    int n;
};

struct output_data {
    long result;
};

program FACT_PROG {
    version FACT_VERS {
        output_data CALCULATE_FACTORIAL(input_data) = 1;
    } = 1;
} = 0x23456789;
