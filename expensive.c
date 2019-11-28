//
// Created by Fabian on 28.11.2019.
//

#include <stdio.h>
#include <limits.h>
#include <stdbool.h>
#include "expensive.h"
#include "insertionsort.h"
#include "mergesort.h"

int array[ARRAY_SIZE];

void prepareCalculation() {
    for (int i = 0; i < ARRAY_SIZE; ++i) {
        array[i] = INT_MAX-i;
    }
}

void expensiveCalculation() {
    insertionsort(array);
}

void finishCalculation() {
    bool correct = true;
    int min = INT_MIN;

    for (int i = 0; i < ARRAY_SIZE; ++i) {
        if(min <= array[i]) {
            min = array[i];
        } else {
            correct = false;
            break;
        }
    }

    if(!correct)
        fprintf(stderr, "\nOUTPUT WRONG!\n\n");
}