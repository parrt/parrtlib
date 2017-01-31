/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import java.util.Collection;

import static java.lang.Math.log;

public class ParrtMath {
	public static boolean isClose(double a, double b) {
		boolean result = Math.abs(a-b)<0.000000001;
		return result;
	}

	public static double log2(double p) {
		return log(p) / log(2.0); // log2(x) = log(x)/log(2)
	}

	public static int[] add(int[] a, int[] b) {
		int n = Math.min(a.length, b.length);
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] + b[i];
		}
		return result;
	}

	public static void addTo(double[] a, double[] b) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			a[i] += b[i];
		}
	}

	public static void addTo(int[] a, int[] b) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			a[i] += b[i];
		}
	}

	public static double mag(double[] a) {
		double[] m = mul(a, a);
		double s = ParrtStats.sum(m);
		return Math.sqrt(s);
	}

	public static double[] mul(double[] a, double[] b) {
		int n = Math.min(a.length, b.length);
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] * b[i];
		}
		return result;
	}

	public static double[] div(double[] a, double[] b) {
		int n = Math.min(a.length, b.length);
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] / b[i];
		}
		return result;
	}

	public static double[] div(double[] a, double b) {
		int n = a.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] / b;
		}
		return result;
	}

	public static double[] minus(double[] a, double[] b) {
		int n = Math.min(a.length, b.length);
		double[] result = new double[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] - b[i];
		}
		return result;
	}

	public static int[] minus(int[] a, int[] b) {
		int n = Math.min(a.length, b.length);
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = a[i] - b[i];
		}
		return result;
	}

	/** r = a - b; truncate results to length of r */
	public static void minus(int[] a, int[] b, int[] r) {
		if ( a==null || b==null || r==null ) return;
		if ( a.length<r.length || b.length<r.length ) return;
		for (int i = 0; i<r.length; i++) {
			r[i] = a[i] - b[i];
		}
	}

	public static int max(Collection<Integer> values) {
		int m = Integer.MIN_VALUE;
		for (Integer i : values) {
			m = Math.max(m, i);
		}
		return m;
	}
}
