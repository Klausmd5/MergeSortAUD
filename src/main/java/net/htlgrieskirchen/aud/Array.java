package net.htlgrieskirchen.aud;

import java.util.Arrays;

public class Array<T extends Comparable<T>> implements IArray<T> {
	private T[] array;

	public Array(T[] array) {
		this.array = array;
	}

	@Override
	public void swap(int i, int j) {
		T tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	@Override
	public int size() {
		return array.length;
	}

	/**
	 * @see Comparable#compareTo
	 */
	@Override
	public int compare(int i, int j) {
		return array[i].compareTo(array[j]);
	}

	@Override
	public IArray<T> subArray(int from, int toExclusive) {
		return new Array<T>(Arrays.copyOfRange(array, from, toExclusive));
	}
}
