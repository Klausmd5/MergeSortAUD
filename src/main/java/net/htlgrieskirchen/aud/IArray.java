package net.htlgrieskirchen.aud;

public interface IArray<T extends Comparable<T>> {
	public void swap(int i, int j);
	public int size();
	/**
	 * @see Comparable#compareTo
	 */
	public int compare(int i, int j);
	public IArray<T> subArray(int from, int toExclusive);
	public void append(IArray<T> array);
}
