/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import us.parr.lib.collections.CountingHashSet;
import us.parr.lib.collections.CountingSet;
import us.parr.lib.collections.MultiMap;
import us.parr.lib.collections.MultiMapOfSets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ParrtCollections {
	@SafeVarargs
	public static <T> List<T> list(T... values) {
		ArrayList<T> list = new ArrayList<T>();
		Collections.addAll(list, values);
		return list;
	}

	@SafeVarargs
	public static <T> Set<T> set(T... values) {
		Set<T> s = new HashSet<T>();
		Collections.addAll(s, values);
		return s;
	}

	public static <T> List<T> repeat(T o, int n) {
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i<n; i++) {
			list.add(o);
		}
		return list;
	}

	/** Return a map from data element T to its index */
	public static <T> Map<T,Integer> asMap(T[] data) {
		Map<T,Integer> m = new HashMap<>();
		for (int i = 0; i<data.length; i++) {
			m.put(data[i], i);
		}
		return m;
	}

	/** Return a map from data element T to its index */
	public static <T> Map<T,Integer> asMap(List<T> data) {
		Map<T,Integer> m = new HashMap<>();
		for (int i = 0; i<data.size(); i++) {
			m.put(data.get(i), i);
		}
		return m;
	}

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

	public static <K,V> Map<K,V> filterByKey(Map<K,V> data, Predicate<K> pred) {
		Map<K,V> output = (Map<K,V>)dupObject(data);
		if ( data!=null ) {
			for (K x : data.keySet()) {
				if ( pred.test(x) ) {
					output.put(x, data.get(x));
				}
			}
		}
		return output;
	}

	public static <K,V> Map<K,V> filterByValue(Map<K,V> data, Predicate<V> pred) {
		Map<K,V> output = (Map<K,V>)dupObject(data);
		if ( data!=null ) {
			for (K x : data.keySet()) {
				if ( pred.test(data.get(x)) ) {
					output.put(x, data.get(x));
				}
			}
		}
		return output;
	}


	public static <K,V> MultiMap<K,V> filterByKey(MultiMap<K,V> data, Predicate<K> pred) {
		MultiMap<K,V> output = (MultiMap<K,V>)dupObject(data);
		if ( data!=null ) {
			for (K x : data.keySet()) {
				if ( pred.test(x) ) {
					output.set(x, data.get(x));
				}
			}
		}
		return output;
	}

	public static <K,V> MultiMap<K,V> filterByValue(MultiMap<K,V> data, Predicate<Collection<V>> pred) {
		MultiMap<K,V> output = (MultiMap<K,V>)dupObject(data);
		if ( data!=null ) {
			for (K x : data.keySet()) {
				if ( pred.test(data.get(x)) ) {
					output.set(x, data.get(x));
				}
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

	public static <T> CountingSet<T> intersection(CountingSet<T> a, CountingSet<T> b) {
		CountingSet<T> inter = new CountingHashSet<T>();
		for (T v : a) {
			if ( b.contains(v) ) inter.add(v);
		}
		return inter;
	}

	public static <T> Set<T> union(Set<T> a, Set<T> b) {
		Set<T> u = new HashSet<T>();
		u.addAll(a);
		u.addAll(b);
		return u;
	}

	public static <T> CountingSet<T> union(CountingSet<T> a, CountingSet<T> b) {
		CountingSet<T> u = new CountingHashSet<T>();
		u.addAll(a);
		u.addAll(b);
		return u;
	}

	public static <T> List<T> union(List<T> a, List<T> b) {
		List<T> u = new ArrayList<T>();
		u.addAll(a);
		u.addAll(b);
		return u;
	}

	/** Return a MultiMap with merged values for keys in common to
	 *  arguments a and b. Returns null if either argument is null.
	 */
	public static <K,V> MultiMap<K,V> merged(Map<K,V> a, Map<K,V> b) {
		MultiMap<K,V> u = new MultiMapOfSets<>();
		if ( a==null || b==null ) {
			return null;
		}
		for (K key : a.keySet()) {
			u.put(key, a.get(key));
		}
		for (K key : b.keySet()) {
			u.put(key, b.get(key));
		}
		return u;
	}

	/** Return a Map with all key-value pairs from arguments a and b.
	 *  For keys common to both, the result Map gives precedence to b values,
	 *  meaning that result[key] = b[key] if key in a and key in b.
	 *
	 *  Returns null if either argument is null.
	 */
	public static <K,V> Map<K,V> union(Map<K,V> a, Map<K,V> b) {
		Map<K,V> u = new HashMap<K, V>();
		if ( a==null || b==null ) {
			return null;
		}
		for (K key : a.keySet()) {
			u.put(key, a.get(key));
		}
		for (K key : b.keySet()) {
			u.put(key, b.get(key));
		}
		return u;
	}

	public static <T> Set<T> difference(Set<T> a, Set<T> b) { // 1,2,3 - 2 = 1,3
		Set<T> diff = new HashSet<T>();
		for (T v : a) {
			if ( !b.contains(v) ) diff.add(v);
		}
		return diff;
	}

	public static <T> CountingSet<T> difference(CountingSet<T> a, CountingSet<T> b) { // 1,2,3 - 2 = 1,3
		CountingSet<T> diff = new CountingHashSet<T>();
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
	public static <T extends Comparable<? super T>> List<T> sorted(List<T> data) {
		List<T> dup = new ArrayList<T>();
		dup.addAll(data);
		Collections.sort(dup);
		return dup;
	}

	/** Return a LinkedHashMap from data map sorted by key */
	public static <K extends Comparable<? super K>,V> LinkedHashMap<K,V> sortedByKey(Map<K,V> data) {
		LinkedHashMap<K, V> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<K,V>comparingByKey())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	/** Return a LinkedHashMap from data map sorted by value */
	public static <K,V extends Comparable<? super V>> LinkedHashMap<K,V> sortedByValue(Map<K,V> data) {
		LinkedHashMap<K, V> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<K,V>comparingByValue())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	public static <K extends Comparable<? super K>,V> LinkedHashMap<K,Collection<V>> sortedByKey(MultiMap<K,V> data) {
		LinkedHashMap<K, Collection<V>> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<K,Collection<V>>comparingByKey())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	public static <T> LinkedHashMap<T, Integer> sortedByValueReverse(CountingSet<T> data) {
		LinkedHashMap<T, Integer> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<T, Integer> comparingByValue().reversed())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	public static <T> LinkedHashMap<T, Integer> sortedByValue(CountingSet<T> data) {
		LinkedHashMap<T, Integer> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<T, Integer> comparingByValue())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	public static <T extends Comparable<? super T>> LinkedHashMap<T, Integer> sortedByKey(CountingSet<T> data) {
		LinkedHashMap<T, Integer> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<T, Integer> comparingByKey())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	public static <T extends Comparable<? super T>> LinkedHashMap<T, Integer> sortedByKeyReverse(CountingSet<T> data) {
		LinkedHashMap<T, Integer> sorted = new LinkedHashMap<>();
		data.entrySet().stream()
			.sorted(Map.Entry.<T, Integer> comparingByKey().reversed())
			.forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		return sorted;
	}

	private static Object dupObject(Object o) {
		try {
			return o.getClass().newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
