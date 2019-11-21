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
            Array<Integer> first = new Array<Integer>(new Integer[half]);
            Array<Integer> sec = new Array<Integer>(new Integer[half]);

            for(int i = 0; i < (half -1); i++) {
                first.add(i, (Integer) a.get(i));
            }

            for(int i = half -1; i < a.size(); i++) {
                sec.add(i, (Integer) a.get(i));
            }

            sort(first);
            sort(sec);

        } else {
            //zusammenbauen

        }
    }
}
