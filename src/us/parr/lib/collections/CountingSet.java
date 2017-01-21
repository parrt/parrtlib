package us.parr.lib.collections;

import java.util.List;
import java.util.Set;

public interface CountingSet<T> {
	/** Return a new set containing a[i]-b[i] for all keys i. Values in b
	 *  but not in a are ignored.  Values in a but not in b yield a's same value
	 *  in the result.
	 */
	static <T> CountingHashSet<T> minus(CountingHashSet<T> a, CountingHashSet<T> b) {
		CountingHashSet<T> r = new CountingHashSet<T>(a);
		for (T key : r.keySet()) {
			MutableInt bI = b.get(key);
			if ( bI!=null ) {
				r.put(key, new MutableInt(r.get(key).v - bI.v)); // can't alter any MutableInts
			}
		}
		return r;
	}

	Set<T> keySet();

	boolean contains(Object o);

	public void add(T key);

	int count(T key);

	/** How many total elements added to set including repeats?
	 *  Note that size() returns number of keys.
	 */
	int total();

	int size();

	List<Integer> counts();

	/** Return the key with the max count; tie goes to first cat at max found. */
	T argmax();

	double entropy();
}
