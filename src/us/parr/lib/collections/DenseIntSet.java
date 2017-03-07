/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

// TODO: should probably flip hierarchy for this class due to UnsupportedOperationException stuff
public class DenseIntSet extends CountingDenseIntSet implements Set<Integer> {
	public static class MyIterator implements Iterator<Integer> {
		protected final DenseIntSet data;
		protected int i = 0;

		public MyIterator(DenseIntSet data) {
			this.data = data;
		}

		@Override
		public boolean hasNext() {
			return i<data.set.length;
		}

		@Override
		public Integer next() {
			return data.set[i++];
		}
	}

	public DenseIntSet(int maxSetValue) {
		super(maxSetValue);
	}

	@Override
	public boolean add(Integer key) {
		boolean isnew = set[key]==0;
		set[key] = 1;
		return isnew;
	}

	@Override
	public int count(Integer key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Integer> counts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public CountingSet<Integer> minus(CountingSet<Integer> x) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer argmax() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int total() {
		return size();
	}

	// Set<Integer> stuff


	@Override
	public boolean isEmpty() {
		return keySet().size()==0;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator(this);
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Spliterator<Integer> spliterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeIf(Predicate<? super Integer> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<Integer> stream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<Integer> parallelStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forEach(Consumer<? super Integer> action) {
		throw new UnsupportedOperationException();
	}
}
