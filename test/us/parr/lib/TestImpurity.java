/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import org.junit.Test;
import us.parr.lib.collections.CountingHashSet;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static us.parr.lib.ParrtMath.isClose;
import static us.parr.lib.ParrtMath.log2;
import static us.parr.lib.ParrtStats.entropy;
import static us.parr.lib.ParrtStats.gini;

public class TestImpurity {
	public static final int HEADS = 0;
	public static final int TAILS = 1;

	@Test public void testEntropyFairCoinOneEach() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(1); // 1 head
		valueCounts.add(1); // 1 tail
		assertEquals(1.0, entropy(valueCounts));
	}

	@Test public void testEntropyFairCoinOneEach2() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(50); // 1 head
		valueCounts.add(50); // 1 tail
		assertEquals(1.0, entropy(valueCounts));
	}

	@Test public void testEntropyUnFairCoinOneEach() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(99); // 99 heads
		valueCounts.add(1);  // 1 tail
		double expected = -(.99*log2(.99) + .01*log2(.01)); // ~0.081
		assertTrue(isClose(expected, entropy(valueCounts)));
	}

	@Test public void testEntropyZeroProbability() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(100);
		valueCounts.add(0);
		assertTrue(isClose(0.0, entropy(valueCounts)));
	}

	@Test public void testCategoriesFromRestaurant() {
		int[] willwait = new int[] {1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1};
		CountingHashSet<Object> valueToCounts = new CountingHashSet<>();
		for (int wait : willwait) {
			valueToCounts.add(wait);
		}
		float n = willwait.length;
		double p1 = 6/n; // {0=6, 1=6}
		double p2 = 6/n;

		double expected = -(p1 * log2(p1) + p2 * log2(p2)); // 1.0
		double result = entropy(valueToCounts.counts());
		assertTrue(isClose(expected, result));
	}

	@Test public void testCategoriesFromSignups() {
		int[] signups = new int[] {1, 3, 2, 2, 3, 1, 2, 3, 1, 1, 1, 1, 2, 1, 2, 2};
		CountingHashSet<Object> valueToCounts = new CountingHashSet<>();
		for (int signup : signups) {
			valueToCounts.add(signup);
		}
		System.out.println(valueToCounts);
		float n = signups.length;
		double p1 = 7/n; // {1=7, 2=6, 3=3}
		double p2 = 6/n;
		double p3 = 3/n;

		double expected = -(p1 * log2(p1) + p2 * log2(p2) + p3 * log2(p3)); // ~1.51
		double result = entropy(valueToCounts.counts());
		assertTrue(isClose(expected, result));
	}

	@Test public void testGiniFairCoinOneEach() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(1); // 1 head
		valueCounts.add(1); // 1 tail
		assertEquals(0.5, gini(valueCounts));
	}

	@Test public void testGiniUnFairCoinOneEach() {
		List<Integer> valueCounts = new ArrayList<>();
		valueCounts.add(99); // 99 heads
		valueCounts.add(1);  // 1 tail
		double expected = .99*(1-.99)+.01*(1-.01);
		assertTrue(isClose(expected, gini(valueCounts)));
	}
}
