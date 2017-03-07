/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static us.parr.lib.ParrtMath.log2;

/** Count how many of each key we have; not thread safe */
public class CountingHashSet<T> implements CountingSet<T> {
	protected HashMap<T, MutableInt> data = new HashMap<>();

	public CountingHashSet() {
	}

	public CountingHashSet(CountingSet<T> old) {
		for (T key : old.keySet()) {
			data.put(key, new MutableInt(old.count(key))); // make sure MutableInts are copied deeply
		}
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public int count(T key) {
		MutableInt value = data.get(key);
		if (value == null) return 0;
		return value.v;
	}

	@Override
	public boolean add(T key) {
		MutableInt old = data.get(key);
		if (old == null) {
			data.put(key, new MutableInt(1));
		}
		else {
			old.v++;
		}
		return old==null;
	}

	@Override
	public int total() {
		int n = 0;
		for (MutableInt i : data.values()) {
			n += i.v;
		}
		return n;
	}

	@Override
	public List<Integer> counts() {
		List<Integer> counts = new ArrayList<>();
		for (MutableInt i : data.values()) {
			counts.add(i.v);
		}
		return counts;
	}

	public List<T> keys() {
		List<T> keys = new ArrayList<>();
		keys.addAll(keySet());
		return keys;
	}

	/** Return a new set containing this[i]-x[i] for all keys i. Values in x
	 *  but not in this are ignored.  Values in this but not in x yield this's same value
	 *  in the result.
	 */
	public CountingHashSet<T> minus(CountingSet<T> x) {
		CountingHashSet<T> r = new CountingHashSet<T>(this);
		for (T key : r.keySet()) {
			if ( x.contains(key) ) {
				r.data.put(key, new MutableInt(r.count(key) - x.count(key))); // can't alter any MutableInts
			}
		}
		return r;
	}

	@Override
	public T argmax() {
		T keyOfMax = null;
		for (T key : keySet()) {
			if ( keyOfMax==null ) { // initial condition
				keyOfMax = key;
				continue;
			}
			if ( count(key)>count(keyOfMax) ) keyOfMax = key;
		}
		return keyOfMax;
	}

	public double entropy() {
		double entropy = 0.0;
		int n = total();
		for (MutableInt i : data.values()) {
			if ( i.v==0 ) continue; // avoid log(0), which is undefined
			double p = ((double)i.v) / n;
			entropy += p * log2(p);
		}
		entropy = -entropy;
		return entropy;
	}

	// satisfy the Set interface

	@Override
	public Set<T> keySet() {
		return data.keySet();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean remove(Object o) {
		return data.remove(o) == null;
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Iterator<T> iterator() {
		return data.keySet().iterator();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}
