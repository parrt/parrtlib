package us.parr.lib.collections;

import java.util.Collection;
import java.util.Set;

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
