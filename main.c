#include <stdio.h>
#include <time.h>
#include <sys/time.h>

#include "expensive.h"

int main() {
    double sum = 0;

    for (int i = 0; i < ITERATION_COUNT; ++i) {
        struct timeval  tv1, tv2;

        prepareCalculation();

        gettimeofday(&tv1, NULL);

        expensiveCalculation();

        gettimeofday(&tv2, NULL);

        finishCalculation();

        sum += (double) (tv2.tv_usec - tv1.tv_usec) / 1000000 + (double) (tv2.tv_sec - tv1.tv_sec);
    }

    printf("Executed %d times, average time: %.20f",ITERATION_COUNT,sum/ITERATION_COUNT);

    return 0;
}
