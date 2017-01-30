/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static us.parr.lib.ParrStrings.expandTabs;

public class ParrtIO {
	public static String load(String fileName, int tabSize)
		throws Exception
	{
		byte[] filearray = Files.readAllBytes(Paths.get(fileName));
		String content = new String(filearray);
		String notabs = expandTabs(content, tabSize);
		return notabs;
	}

	public static List<String> getFilenames(File f, String inputFilePattern) throws Exception {
		List<String> files = new ArrayList<>();
		getFilenames_(f, inputFilePattern, files);
		return files;
	}

	public static void getFilenames_(File f, String inputFilePattern, List<String> files) {
		// If this is a directory, walk each file/dir in that directory
		if (f.isDirectory()) {
			String flist[] = f.list();
			for (String aFlist : flist) {
				getFilenames_(new File(f, aFlist), inputFilePattern, files);
			}
		}

		// otherwise, if this is an input file, load it!
		else if ( inputFilePattern==null || f.getName().matches(inputFilePattern) ) {
		  	files.add(f.getAbsolutePath());
		}
	}

	public static void save(String fileName, String content) {
		save(fileName, content, null);
	}

	public static void save(String fileName, String content, String encoding) {
		OutputStreamWriter osw = null;
		try {
			File f = new File(fileName);
			FileOutputStream fos = new FileOutputStream(f);
			if (encoding != null) {
				osw = new OutputStreamWriter(fos, encoding);
			}
			else {
				osw = new OutputStreamWriter(fos);
			}
			osw.write(content);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			if ( osw!=null ) {
				try {
					osw.close();
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}
	}

	public static char[] read(String fileName) {
		return read(fileName, null);
	}

	public static char[] read(String fileName, String encoding) {
		InputStreamReader isr = null;
		char[] data = null;
		try {
			File f = new File(fileName);
			int size = (int)f.length();
			FileInputStream fis = new FileInputStream(fileName);
			if ( encoding!=null ) {
				isr = new InputStreamReader(fis, encoding);
			}
			else {
				isr = new InputStreamReader(fis);
			}
			data = new char[size];
			int n = isr.read(data);
			if (n < data.length) {
				data = Arrays.copyOf(data, n);
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			if ( isr!=null ) {
				try {
					isr.close();
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}
		return data;
	}
}
