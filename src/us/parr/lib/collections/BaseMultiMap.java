/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseMultiMap<K, V> implements MultiMap<K, V> {
	protected LinkedHashMap<K,Collection<V>> data = new LinkedHashMap<>();

	public BaseMultiMap() {
	}

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
	public void set(K key, Collection<V> values) {
		data.put(key, values);
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
	public Set<Map.Entry<K, Collection<V>>> entrySet() {
		return data.entrySet();
	}

	@Override
	public Collection<Collection<V>> values() {
		return data.values();
	}
}
