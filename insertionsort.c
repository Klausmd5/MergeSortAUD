//
// Created by Fabian on 28.11.2019.
//

#include "insertionsort.h"
#include "expensive.h"

#include <stdio.h>

void insertionsort(int* array) {
    for (int i = 1; i < ARRAY_SIZE; ++i) {
        if(array[i] < array[i-1]) {
            for (int j = i; j > 0 && array[j] < array[j-1]; j--) {
                int tmp = array[j];
                array[j] = array[j-1];
                array[j-1] = tmp;
            }
        }
    }
}