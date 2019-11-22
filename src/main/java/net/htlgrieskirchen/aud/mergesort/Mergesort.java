package net.htlgrieskirchen.aud.mergesort;

import net.htlgrieskirchen.aud.Array;

import java.util.Arrays;

public class Mergesort {

    public static <T extends Comparable<T>> Array<Integer> sort(Array<T> a) {

        int half = (a.size() /2);

        if(a.size() > 2) {
            // Teilen
            Array<Integer> first = (Array<Integer>) a.subArray(0, half);
            Array<Integer> sec = (Array<Integer>) a.subArray(half, a.size()- 1);

            first = sort(first);
            sec = sort(sec);

            first.append(sec);

            return first;
        } else {
            //zusammenbauen
            Integer[] i = (Integer[]) a.getArray();
            Arrays.sort(i);
            return new Array<Integer>(i);
        }
    }
}
