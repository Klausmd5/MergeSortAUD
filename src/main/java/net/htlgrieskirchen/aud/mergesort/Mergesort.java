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

            Array<Integer> sort = new Array<>(new Integer[first.size() + sec.size()]);
            for(int y = 0; y < first.size(); y++) {
                sort.add(y,first.get(y));
            }
            for(int y = first.size() -1; y < sec.size(); y++) {
                sort.add(y,sec.get(y));
            }

            return sort;
        } else {
            //zusammenbauen
            Integer[] i = (Integer[]) a.getArray();
            Arrays.sort(i);
            return new Array<Integer>(i);
        }
    }
}
