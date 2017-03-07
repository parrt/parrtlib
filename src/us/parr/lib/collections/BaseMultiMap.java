/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BaseMultiMap<K, V> implements MultiMap<K, V> {
	protected LinkedHashMap<K,Collection<V>> data = new LinkedHashMap<>();

	@Override
	public Collection<V> get(K key) {
		return data.get(key);
	}

	@Override
	public void put(K key, V value) {
		Collection<V> elements = data.computeIfAbsent(key, k -> createValueCollection());
		elements.add(value);
	}

	@Override
	public MultiMap<K,V> filterByKey(Predicate<K> pred) {
		BaseMultiMap<K,V> output = createCollection();
		if ( data!=null ) {
			for (K x : data.keySet()) {
				if ( pred.test(x) ) {
					output.data.put(x, data.get(x));
				}
			}
		}
		return output;
	}

	protected abstract Collection<V> createValueCollection();

	/** Return a fresh instance of the subclass */
	protected abstract BaseMultiMap<K,V> createCollection();

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean containsKey(K key) {
		return data.containsKey(key);
	}

	@Override
	public Collection<V> remove(K key) {
		return data.remove(key);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Set<K> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<Collection<V>> values() {
		return data.values();
	}
}
