package net.htlgrieskirchen.aud;

import net.htlgrieskirchen.aud.insertionsort.InsertionSort;
import net.htlgrieskirchen.aud.mergesort.Mergesort;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        Integer[] ints = new Random().ints(10, 0, 10).boxed().toArray(Integer[]::new);

        System.out.println("---- Insertion Sort ----");

        Array<Integer> array = new Array<>(ints.clone());
        InsertionSort.sort(array);
        Integer[] sorted = array.getArray();

        print(sorted, ints);

        System.out.println("---- Merge Sort ----");

        Array<Integer> arr2 = new Array<>(ints.clone());
        Mergesort.sort2(arr2);
        Integer[] sorted2 = arr2.getArray();

        print(sorted2, ints);

    }

    public static void print(Integer[] sorted, Integer[] ints) {
        for(int i = 0; i < ints.length; i++) {
            System.out.println(ints[i] + "\t" + sorted[i]);
        }
    }
}