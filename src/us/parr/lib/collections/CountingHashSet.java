/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static us.parr.lib.ParrtMath.log2;

/** Count how many of each key we have; not thread safe */
public class CountingHashSet<T> extends HashMap<T, MutableInt> implements CountingSet<T> {
	public CountingHashSet() {
	}

	public CountingHashSet(CountingSet<T> old) {
		for (T key : old.keySet()) {
			put(key, new MutableInt(old.count(key))); // make sure MutableInts are copied deeply
		}
	}

	@Override
	public boolean contains(Object o) {
		return containsKey(o);
	}

	@Override
	public int count(T key) {
		MutableInt value = get(key);
		if (value == null) return 0;
		return value.v;
	}

	@Override
	public boolean add(T key) {
		MutableInt old = get(key);
		if (old == null) {
			put(key, new MutableInt(1));
		}
		else {
			old.v++;
		}
		return old==null;
	}

	@Override
	public boolean remove(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int total() {
		int n = 0;
		for (MutableInt i : values()) {
			n += i.v;
		}
		return n;
	}

	@Override
	public List<Integer> counts() {
		List<Integer> counts = new ArrayList<>();
		for (MutableInt i : values()) {
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
				r.put(key, new MutableInt(r.count(key) - x.count(key))); // can't alter any MutableInts
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
		for (MutableInt i : values()) {
			if ( i.v==0 ) continue; // avoid log(0), which is undefined
			double p = ((double)i.v) / n;
			entropy += p * log2(p);
		}
		entropy = -entropy;
		return entropy;
	}
}
