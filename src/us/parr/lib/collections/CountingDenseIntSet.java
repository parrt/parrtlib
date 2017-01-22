package us.parr.lib.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static us.parr.lib.ParrtMath.log2;

/** A set of positive (or zero-valued) integers. */
public class CountingDenseIntSet implements CountingSet<Integer> {
	/** The set[i] is the number of times i appears in set; 0 implies not there. */
	protected final int[] set;

	public CountingDenseIntSet(int maxSetValue) {
		set = new int[maxSetValue+1];
	}

	public CountingDenseIntSet(CountingSet<Integer> old) {
		if ( old instanceof CountingDenseIntSet ) {
			int[] oldset = ((CountingDenseIntSet) old).set;
			this.set = Arrays.copyOf(oldset, oldset.length);
		}
		else {
			this.set = new int[old.size()];
			for (Integer key : old.keySet()) {
				set[key] = old.count(key);
			}
		}
	}

	@Override
	public Set<Integer> keySet() {
		DenseIntSet keys = new DenseIntSet(set.length-1);
		for (int i = 0; i<set.length; i++) {
			if ( set[i]>0 ) {
				keys.add(i);
			}
		}
		return keys;
	}

	@Override
	public boolean contains(Object o) {
		if ( o==null || !(o instanceof Integer) ) return false;
		int v = (Integer)o;
		if ( v<0 ) return false;
		return set[v]>0;
	}

	@Override
	public boolean add(Integer key) {
		boolean isnew = set[key]==0;
		set[key]++;
		return isnew;
	}

	@Override
	public int count(Integer key) {
		return set[key];
	}

	@Override
	public int total() {
		int n = 0;
		for (int i = 0; i<set.length; i++) {
			n += set[i];
		}
		return n;
	}

	@Override
	public int size() {
		int n = 0;
		for (int i = 0; i<set.length; i++) {
			if ( set[i]>0 ) n++;
		}
		return n;
	}

	@Override
	public List<Integer> counts() {
		List<Integer> c = new ArrayList<>();
		for (int i = 0; i<set.length; i++) {
			if ( set[i]>0 ) {
				c.add(set[i]);
			}
		}
		return c;
	}

	public CountingDenseIntSet minus(CountingDenseIntSet x) {
		CountingDenseIntSet r = new CountingDenseIntSet(this.set.length-1);
		for (int i = 0; i<set.length; i++) {
			if ( this.set[i]>0 && x.set[i]>0 ) {
				r.set[i] = this.set[i] - x.set[i];
			}
		}
		return r;
	}

	@Override
	public CountingSet<Integer> minus(CountingSet<Integer> x) {
		if ( x instanceof CountingDenseIntSet ) {
			return minus((CountingDenseIntSet)x);
		}
		CountingDenseIntSet r = new CountingDenseIntSet(this.set.length-1);
		for (int i = 0; i<set.length; i++) {
			if ( set[i]>0 ) {
				if ( x.contains(set[i]) ) {
					set[i] -= x.count(i);
				}
			}
		}
		return r;
	}

	@Override
	public Integer argmax() {
		int m = 0;
		int mi = -1;
		// find first non-zero entry from right
		for (int i = set.length-1; i>=0; --i) {
			if ( set[i]>m ) {
				m = set[i];
				mi = i;
			}
		}
		return mi;
	}

	@Override
	public double entropy() {
		double entropy = 0.0;
		int n = total();
		for (int i = 0; i<set.length; i++) {
			if ( set[i]==0 ) continue; // avoid log(0), which is undefined
			double p = ((double)set[i]) / n;
			entropy += p * log2(p);
		}
		entropy = -entropy;
		return entropy;
	}
}
