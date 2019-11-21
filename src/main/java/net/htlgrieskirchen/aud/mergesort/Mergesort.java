package net.htlgrieskirchen.aud.mergesort;

import net.htlgrieskirchen.aud.Array;
import net.htlgrieskirchen.aud.IArray;

public class Mergesort {

    public Mergesort() {

    }

    public <T extends Comparable<T>> void sort(Array<T> a) {
        if(a.size() > 1) {
            // Teilen
            int half = a.size() /2;
            Array<Integer> first = (Array<Integer>) a.subArray(0, half);
            Array<Integer> sec = (Array<Integer>) a.subArray(half, a.size()- 1);

            sort(first);
            sort(sec);

        } else {
            //zusammenbauen

        }
    }
}
