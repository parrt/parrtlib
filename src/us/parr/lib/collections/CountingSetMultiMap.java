/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;

/** An implementation of a MultiMap that tracks the multiple values in
 *  a set. The order of keys added to this map is preserved
 *  through an implementation using {@see LinkedHashMap}.
 */
public class CountingSetMultiMap<K,V> extends BaseMultiMap<K, V> {
	protected Collection<V> createValueCollection() {
		return new CountingHashSet<V>();
	}

	@Override
	public CountingSet<V> get(K key) {
		return (CountingSet<V>)super.get(key);
	}
}

