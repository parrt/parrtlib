package us.parr.lib.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class DenseIntMap<T> implements Map<Integer, T> {
	protected T[] buckets;

	public DenseIntMap(int initialMaxKeyValue) {
		buckets = (T[])new Object[initialMaxKeyValue];
	}

	public DenseIntMap() {
		this(10);
	}

	@Override
	public int size() {
		int n = 0;
		for (int i = 0; i<buckets.length; i++) {
			if ( buckets[i]!=null ) n++;
		}
		return n;
	}

	@Override
	public boolean isEmpty() {
		return size()>0;
	}

	@Override
	public boolean containsKey(Object key) {
		return buckets[(Integer)key]!=null;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i<buckets.length; i++) {
			if ( buckets[i].equals(value) ) return true;
		}
		return false;
	}

	@Override
	public T get(Object key) {
		return buckets[(Integer)key];
	}

	public T get(int key) { return buckets[key]; }

	@Override
	public T put(Integer key, T value) {
		T old = buckets[key];
		buckets[key] = value;
		return old;
	}

	public void put(int key, T value) { buckets[key] = value; }

	@Override
	public T remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		buckets = (T[])new Object[buckets.length];
	}

	@Override
	public Set<Integer> keySet() {
		return new DenseIntSet(buckets.length);
	}

	@Override
	public Collection<T> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<Integer, T>> entrySet() {
		throw new UnsupportedOperationException();
	}

	public void forEach(BiConsumer<? super Integer, ? super T> action) {
		Objects.requireNonNull(action);
		for (int i = 0; i<buckets.length; i++) {
			action.accept(i, buckets[i]);
		}
	}
}
