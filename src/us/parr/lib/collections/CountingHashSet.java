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

	public CountingHashSet(CountingHashSet<T> old) {
		for (T key : old.keySet()) {
			put(key, new MutableInt(old.get(key).v)); // make sure MutableInts are copied deeply
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
	public void add(T key) {
		MutableInt value = get(key);
		if (value == null) {
			value = new MutableInt(1);
			put(key, value);
		}
		else {
			value.v++;
		}
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
