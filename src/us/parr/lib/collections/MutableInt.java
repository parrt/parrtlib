/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

public class MutableInt extends Number implements Comparable<Number> {
	public int v;

	public MutableInt(int v) { this.v = v; }

	@Override
	public boolean equals(Object o) {
		if ( o instanceof Number ) return v == ((Number)o).intValue();
		return false;
	}

	@Override public int hashCode() { return v; }

	@Override public int compareTo(Number o) { return v-o.intValue(); }
	@Override public int intValue() { return v; }
	@Override public long longValue() { return v; }
	@Override public float floatValue() { return v; }
	@Override public double doubleValue() { return v; }

	@Override
	public String toString() {
		return String.valueOf(v);
	}
}
