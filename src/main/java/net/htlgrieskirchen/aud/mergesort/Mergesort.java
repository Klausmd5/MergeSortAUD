package net.htlgrieskirchen.aud.mergesort;

import net.htlgrieskirchen.aud.IArray;

public class Mergesort {

    public Mergesort() {

    }

    public <T extends Comparable<T>> void sort(IArray<T> a) {
        if(a.size() > 1) {
            // Teilen
            for(int i = 0; i < ((a.size() / 2) -1); i++) {

            }

            //IArray<T> eins = a.get(); // hälfte finden
        } else {
            //zusammenbauen
        }
    }
}
