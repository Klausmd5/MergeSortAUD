//
// Created by Fabian on 28.11.2019.
//

#include "mergesort.h"

void mergesort(int* array, int from, int length) {
    if(length > 2) {
        int firstLength = length / 2;
        int secondLength = firstLength*2 == length ? firstLength : firstLength+1;

        mergesort(array,from,firstLength);
        mergesort(array,from+firstLength,secondLength);

        int resultArray[length];

        int firstIterator = 0;
        int secondIterator = 0;
        int resultIterator = 0;

        while(firstIterator < firstLength && secondIterator < secondLength) {
            if(array[from+firstIterator] < array[from+firstLength+secondIterator]) {
                //Copy from first
                resultArray[resultIterator] = array[from+firstIterator];
                firstIterator++;
            } else {
                //Copy from second
                resultArray[resultIterator] = array[from+firstLength+secondIterator];
                secondIterator++;
            }
            resultIterator++;
        }

        for (int i = firstIterator; i < firstLength; ++i) {
            resultArray[resultIterator] = array[from + firstIterator];
            resultIterator++;
        }

        for (int i = secondIterator; i < secondLength; ++i) {
            resultArray[resultIterator] = array[from + firstLength + secondIterator];
            resultIterator++;
        }

        for (int i = 0; i < length; ++i) {
            array[from+i] = resultArray[i];
        }
    } else if(length == 2 && array[from] > array[from + 1]) {
        int tmp = array[from];
        array[from] = array[from+1];
        array[from+1] = tmp;
    }
}