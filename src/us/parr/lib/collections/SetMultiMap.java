/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Predicate;

/** An implementation of a MultiMap that tracks the multiple values in
 *  a set. The order of keys added to this map is preserved
 *  through an implementation using {@see LinkedHashMap}.
 */
public class SetMultiMap<K,V> extends BaseMultiMap<K, V> {
	protected Collection<V> createValueCollection() {
		return new TreeSet<V>();
	}

	@Override
	protected BaseMultiMap<K, V> createCollection() {
		return new SetMultiMap<K, V>();
	}

	@Override
	public SetMultiMap<K,V> filterByKey(Predicate<K> pred) {
		return (SetMultiMap<K,V>)super.filterByKey(pred);
	}
}
