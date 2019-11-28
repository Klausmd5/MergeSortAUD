cmake_minimum_required(VERSION 3.10)
project(AUDMergeSortBenchmark C)

set(CMAKE_C_STANDARD 99)

add_executable(AUDMergeSortBenchmark main.c expensive.c expensive.h insertionsort.c insertionsort.h mergesort.c mergesort.h)