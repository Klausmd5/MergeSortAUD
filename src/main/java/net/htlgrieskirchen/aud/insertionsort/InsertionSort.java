package net.htlgrieskirchen.aud.insertionsort;

import net.htlgrieskirchen.aud.Array;
import net.htlgrieskirchen.aud.IArray;

public class InsertionSort {
	public static <T extends Comparable<T>> void sort(IArray<T> arrayObject) {
		for(int i = 1; i < arrayObject.size(); i++) {
			if(arrayObject.compare(i, i-1) < 0) {
				for(int j = i; j > 0 && arrayObject.compare(j,j-1) < 0; j--) {
					arrayObject.swap(j, j-1);
				}
			}
		}
	}
}
