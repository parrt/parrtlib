/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import us.parr.lib.collections.CountingHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static us.parr.lib.ParrtMath.log2;

public class ParrtStats {
	public static final int SEED = 777111333; // need randomness but use same seed to get reproducibility
	public static final Random random = new Random(SEED);

	public static double variance(List<Integer> data) {
		int n = data.size();
		double sum = 0;
		double avg = sum(data)/((double) n);
		for (int d : data) {
			sum += (d-avg)*(d-avg);
		}
		return sum/n;
	}

	public static double varianceFloats(List<Float> data) {
		int n = data.size();
		double sum = 0;
		double avg = sumFloats(data)/((double) n);
		for (float d : data) {
			sum += (d-avg)*(d-avg);
		}
		return sum/n;
	}

	public static int sum(Collection<Integer> data) {
		int sum = 0;
		for (int d : data) {
			sum += d;
		}
		return sum;
	}

	public static int sum(int[] data) {
		int sum = 0;
		for (int d : data) {
			sum += d;
		}
		return sum;
	}

	public static float sumFloats(Collection<Float> data) {
		float sum = 0;
		for (float d : data) {
			sum += d;
		}
		return sum;
	}

	public static float sumDoubles(Collection<Double> data) {
		float sum = 0;
		for (double d : data) {
			sum += d;
		}
		return sum;
	}

	public static List<Double> diffFloats(List<Float> a, List<Float> b) {
		List<Double> diffs = new ArrayList<>();
		for (int i = 0; i<a.size(); i++) {
			diffs.add((double) a.get(i)-b.get(i));
		}
		return diffs;
	}

	public static int max(Collection<Integer> data) {
		if ( data==null ) {
			return Integer.MIN_VALUE;
		}
		int m = Integer.MIN_VALUE;
		for (int d : data) {
			if ( d>m ) m = d;
		}
		return m;
	}

	public static double max(double[] data) {
		if ( data==null ) {
			return Double.MIN_VALUE;
		}
		double m = Double.MIN_VALUE;
		for (double d : data) {
			if ( d>m ) m = d;
		}
		return m;
	}

	public static double mean(Collection<? extends Number> data) {
		double sum = 0.0;
		for (Number d : data) {
			sum += d.doubleValue();
		}
		return sum / data.size();
	}

	public static double mean(double[] data) {
		double sum = 0.0;
		for (double d : data) {
			sum += d;
		}
		return sum / data.length;
	}

	public static double mean(int[] data) {
		int sum = 0;
		for (int d : data) {
			sum += d;
		}
		return ((float)sum) / data.length;
	}

	/** r = a - b; truncate results to length of r */
	public static void minus(int[] a, int[] b, int[] r) {
		if ( a==null || b==null || r==null ) return;
		if ( a.length<r.length || b.length<r.length ) return;
		for (int i = 0; i<r.length; i++) {
			r[i] = a[i] - b[i];
		}
	}

	/** Compute the gini impurity from a collection of counts */
	public static double gini(Collection<Integer> counts) {
		double impurity = 0.0;
		int n = sum(counts);
		for (Integer v : counts) {
			double p = ((double)v) / n;
			impurity += p * (1-p);
		}
		return impurity;
	}

	/** Compute the entropy from a collection of counts */
	public static double entropy(Collection<Integer> counts) {
		double entropy = 0.0;
		int n = sum(counts);
		for (Integer v : counts) {
			if ( v==0 ) continue; // avoid log(0), which is undefined
			double p = ((double)v) / n;
			entropy += p * log2(p);
		}
		entropy = -entropy;
		return entropy;
	}

	public static double entropy(int[] counts) {
		double entropy = 0.0;
		int n = sum(counts);
		for (int v : counts) {
			if ( v==0 ) continue; // avoid log(0), which is undefined
			double p = ((double)v) / n;
			entropy += p * log2(p);
		}
		entropy = -entropy;
		return entropy;
	}

	/** Produce an array of n random integers in [0..highvalue) */
	public static int[] randint(int n, int highvalue) {
		int[] values = new int[n];
		for (int i = 0; i<n; i++) {
			values[i] = random.nextInt(highvalue);
		}
		return values;
	}

	/** From data, grab data.size() records at random with replacement */
	public static List<int[]> bootstrapWithRepl(List<int[]> data) {
		return bootstrapWithRepl(data, data.size(), null);
	}

	/** From data, grab n records at random with replacement */
	public static List<int[]> bootstrapWithRepl(List<int[]> data, int n) {
		return bootstrapWithRepl(data, n, null);
	}

	/** From data, grab n records at random with replacement, fill in oob with
	 *  data NOT in returned bootstrap (if non-null).
	 */
	public static List<int[]> bootstrapWithRepl(List<int[]> data, int n, Set<Integer> oob) {
		int[] indexes = randint(n, data.size());
		if ( oob!=null ) {
			for (int i = 0; i<n; i++) {
				oob.add(i);
			}
		}
		List<int[]> bootstrap = new ArrayList<>(indexes.length);
		for (int i : indexes) {
			bootstrap.add(data.get(i));
			if ( oob!=null ) {
				oob.remove(i); // make sure bootstrap records are not in oob
			}
		}
		return bootstrap;
	}

	public static int majorityVote(Collection<Integer> data) {
		if ( data.size()==0 ) return 0;
		CountingHashSet<Integer> valueCounts = new CountingHashSet<>();
		for (Integer d : data) {
			valueCounts.add(d);
		}
		return valueCounts.argmax();
	}
}
