package us.parr.lib.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class ListMultiMap<K,V> implements MultiMap<K,V> {
	protected LinkedHashMap<K,Collection<V>> data = new LinkedHashMap<>();

	@Override
	public Collection<V> get(K key) {
		return data.get(key);
	}

	@Override
	public void put(K key, V value) {
		Collection<V> elements = data.computeIfAbsent(key, k -> new ArrayList<V>());
		elements.add(value);
	}

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
