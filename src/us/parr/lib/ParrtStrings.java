/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import java.util.Arrays;
import java.util.List;

public class ParrtStrings {
	public static String spaces(int n) {
		return sequence(n, " ");
	}

	public static String sequence(int n, String s) {
		StringBuilder buf = new StringBuilder();
		for (int sp=1; sp<=n; sp++) buf.append(s);
		return buf.toString();
	}

	public static int count(String s, char x) {
		int n = 0;
		for (int i = 0; i<s.length(); i++) {
			if ( s.charAt(i)==x ) {
				n++;
			}
		}
		return n;
	}

	public static String expandTabs(String s, int tabSize) {
		if ( s==null ) return null;
		StringBuilder buf = new StringBuilder();
		int col = 0;
		for (int i = 0; i<s.length(); i++) {
			char c = s.charAt(i);
			switch ( c ) {
				case '\n' :
					col = 0;
					buf.append(c);
					break;
				case '\t' :
					int n = tabSize-col%tabSize;
					col+=n;
					buf.append(spaces(n));
					break;
				default :
					col++;
					buf.append(c);
					break;
			}
		}
		return buf.toString();
	}

	public static String sortLinesInString(String s) {
		String lines[] = s.split("\n");
		Arrays.sort(lines);
		List<String> linesL = Arrays.asList(lines);
		StringBuilder buf = new StringBuilder();
		for (String l : linesL) {
			buf.append(l);
			buf.append('\n');
		}
		return buf.toString();
	}

	public static String capitalize(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public static String decapitalize(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}
}
