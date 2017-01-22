package us.parr.lib.collections;

import java.util.List;
import java.util.Set;

public interface CountingSet<T> {
	Set<T> keySet();

	boolean contains(Object o);

	boolean add(T key);

	int count(T key);

	/** How many total elements added to set including repeats?
	 *  Note that size() returns number of keys.
	 */
	int total();

	int size();

	List<Integer> counts();

	CountingSet<T> minus(CountingSet<T> a);

	/** Return the key with the max count; tie goes to first cat at max found. */
	T argmax();

	double entropy();
}
