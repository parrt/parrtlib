/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.ArrayList;
import java.util.Collection;

/** An implementation of a MultiMap that tracks the multiple values in
 *  an array list. The order of keys added to this map is preserved
 *  through an implementation using {@see LinkedHashMap}.
 */
public class ListMultiMap<K,V> extends BaseMultiMap<K,V> {
	protected Collection<V> createValueCollection() {
		return new ArrayList<V>();
	}

	@Override
	public ArrayList<V> get(K key) {
		return (ArrayList<V>)super.get(key);
	}
}
