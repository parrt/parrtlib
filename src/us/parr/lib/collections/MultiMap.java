/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.Set;

/** A Map that tracks multiple values for each key.
 *  The order of the values for each key is unspecified.
 */
public interface MultiMap<K, V> {
	Collection<V> get(K key);

	void put(K key, V value);

	int size();

	boolean containsKey(K key);

	Collection<V> remove(K key);

	void clear();

	Set<K> keySet();

	Collection<Collection<V>> values();
}
