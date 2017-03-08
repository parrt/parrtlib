/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib.collections;

import java.util.Map;

public class CountingSetEntry<T> implements Map.Entry<T, Integer> {
	protected T t;
	protected int count;

	public CountingSetEntry(T t, int count) {
		this.t = t;
		this.count = count;
	}

	@Override
	public T getKey() {
		return t;
	}

	@Override
	public Integer getValue() {
		return count;
	}

	@Override
	public Integer setValue(Integer value) {
		Integer old = count;
		count = value;
		return old;
	}
}
