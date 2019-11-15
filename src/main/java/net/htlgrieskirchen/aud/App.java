package net.htlgrieskirchen.aud;

import net.htlgrieskirchen.aud.insertionsort.InsertionSort;

import java.util.Arrays;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        Integer[] ints = new Random().ints(10, 0, 10).boxed().toArray(Integer[]::new);

        Array<Integer> array = new Array<>(ints.clone());
        InsertionSort.sort(array);
        Integer[] sorted = array.getArray();

        for(int i = 0; i < ints.length; i++) {
            System.out.println(ints[i] + "\t" + sorted[i]);
        }
    }
}