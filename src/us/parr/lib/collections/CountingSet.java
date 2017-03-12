/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountingSet<T> extends Set<T> {
	Set<T> keySet();

	boolean contains(Object o);

	boolean add(T key);

	void set(T key, int count);

	int count(T key);

	Set<Map.Entry<T,Integer>> entrySet();

	/** How many total elements added to set including repeats?
	 *  Note that size() returns number of keys.
	 */
	int total();

	int size();

	List<Integer> counts();

	CountingSet<T> minus(CountingSet<T> a);

	@Override
	default boolean addAll(Collection<? extends T> c) {
		return addAll((CountingSet)c);
	}

	default boolean addAll(CountingSet<? extends T> c) {
		boolean isNew = false;
		for (Map.Entry<? extends T, Integer> entry : c.entrySet()) {
			T key = entry.getKey();
			if ( !this.contains(key) ) isNew = true;
			int existingCount = count(key);
			int newCount = existingCount+entry.getValue();
			set(key, newCount);
		}
		return isNew;
	}

	/** Return the key with the max count; tie goes to first cat at max found. */
	T argmax();

	double entropy();
}
