/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import us.parr.lib.collections.CountingHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ParrtCollections {
	public static CountingHashSet<Integer> valueCountsInColumn(List<int[]> X, int colIndex) {
		CountingHashSet<Integer> valueCounts = new CountingHashSet<>();
		for (int i = 0; i<X.size(); i++) { // for each row, count different values for col colIndex
			int[] row = X.get(i);
			int col = row[colIndex];
			valueCounts.add(col);
		}
		return valueCounts;
	}

	public static <T> List<T> filter(List<T> data, Predicate<T> pred) {
		List<T> output = new ArrayList<>();
		if ( data!=null ) for (T x : data) {
			if ( pred.test(x) ) {
				output.add(x);
			}
		}
		return output;
	}

	public static <T, R> List<R> map(Collection<T> data, Function<T, R> getter) {
		List<R> output = new ArrayList<>();
		if ( data!=null ) for (T x : data) {
			output.add(getter.apply(x));
		}
		return output;
	}

	public static <T, R> List<R> map(T[] data, Function<T, R> getter) {
		List<R> output = new ArrayList<>();
		if ( data!=null ) for (T x : data) {
			output.add(getter.apply(x));
		}
		return output;
	}

	public static <T> T findFirst(Collection<T> data, Predicate<T> pred) {
		if ( data!=null ) {
			for (T x : data) {
				if ( pred.test(x) ) {
					return x;
				}
			}
		}
		return null;
	}

	public static <T> int indexOf(Collection<? extends T> elems, Predicate<T> pred) {
		if ( elems!=null ) {
			int i = 0;
			for (T elem : elems) {
				if ( pred.test(elem) ) return i;
				i++;
			}
		}
		return -1;
	}

	public static <T> int indexOf(T[] elems, Predicate<T> pred) {
		if ( elems!=null ) {
			int i = 0;
			for (T elem : elems) {
				if ( pred.test(elem) ) return i;
				i++;
			}
		}
		return -1;
	}

	public static <T> int indexOf(T[] elems, T value) {
		if ( elems!=null ) {
			int i = 0;
			for (T elem : elems) {
				if ( elem.equals(value) ) return i;
				i++;
			}
		}
		return -1;
	}

	public static <T> int lastIndexOf(Collection<? extends T> elems, Predicate<T> pred) {
		if ( elems!=null ) {
			int i = elems.size()-1;
			for (T elem : elems) {
				if ( pred.test(elem) ) return i;
			}
		}
		return -1;
	}

	public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		Set<T> inter = new HashSet<T>();
		for (T v : a) {
			if ( b.contains(v) ) inter.add(v);
		}
		return inter;
	}

	public static <T> Set<T> difference(Set<T> a, Set<T> b) { // 1,2,3 - 2 = 1,3
		Set<T> diff = new HashSet<T>();
		for (T v : a) {
			if ( !b.contains(v) ) diff.add(v);
		}
		return diff;
	}

	public static String join(Collection<?> a, String separator) {
		StringBuilder buf = new StringBuilder();
		int i = 0;
		for (Object o : a) {
			if ( o!=null ) {
				buf.append(o.toString());
			}
			if ( (i+1)<a.size() ) {
				buf.append(separator);
			}
			i++;
		}
		return buf.toString();
	}

	public static String join(Object[] a, String separator) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<a.length; i++) {
			Object o = a[i];
			if ( o!=null ) {
				buf.append(o.toString());
			}
			if ( (i+1)<a.length ) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	public static String join(int[] a, String separator) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<a.length; i++) {
			buf.append(a[i]);
			if ( (i+1)<a.length ) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}


	public static String join(float[] a, String separator) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<a.length; i++) {
			buf.append(a[i]);
			if ( (i+1)<a.length ) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	public static String join(double[] a, String separator, int numDecPlaces) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<a.length; i++) {
			buf.append(String.format("%."+numDecPlaces+"f",a[i]));
			if ( (i+1)<a.length ) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	/** Set the size of a list, filling with null if necessary.
	 *  Trim to size if less than current size.
	 */
	public static void setSize(List<?> list, int size) {
		if (size < list.size()) {
			list.subList(size, list.size()).clear();
		}
		else {
			while (size > list.size()) {
				list.add(null);
			}
		}
	}

	public static List<Integer> add(Collection<Integer> a, Collection<Integer> b) {
		List<Integer> result = new ArrayList<>();
		Iterator<Integer> ita = b.iterator();
		Iterator<Integer> itb = b.iterator();
		while ( ita.hasNext() && itb.hasNext() ) {
			result.add(ita.next() + itb.next());
		}
		return result;
	}

	/** Sort a list into a new list; don't alter data argument */
	public <T extends Comparable<? super T>> List<T> sorted(List<T> data) {
		List<T> dup = new ArrayList<T>();
		dup.addAll(data);
		Collections.sort(dup);
		return dup;
	}

	/** Return a LinkedHashMap sorted by key */
	public <K extends Comparable<? super K>,V> LinkedHashMap<K,V> sorted(Map<K,V> data) {
		LinkedHashMap<K,V> dup = new LinkedHashMap<K, V>();
		List<K> keys = new ArrayList<K>();
		keys.addAll(data.keySet());
		Collections.sort(keys);
		for (K k : keys) {
			dup.put(k, data.get(k));
		}
		return dup;
	}
}
