/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static us.parr.lib.ParrtStrings.expandTabs;

public class ParrtIO {
	public static String load(String fileName, int tabSize) {
		try {
			byte[] filearray = Files.readAllBytes(Paths.get(fileName));
			String content = new String(filearray);
			String notabs = expandTabs(content, tabSize);
			return notabs;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	/** Load an ascii file into a string */
	public static String load(String fileName) {
		try {
			byte[] filearray = Files.readAllBytes(Paths.get(fileName));
			return new String(filearray);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	/** Load a file using an encoding into a char[]. If encoding==null,
	 *  use whatever the default locale says the encoding is.
	 */
	public static char[] load(String fileName, String encoding) {
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
			return data;
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
	}

	public static String read(InputStream stream) {
		return read(stream, null);
	}

	public static String read(InputStream stream, String encoding) {
		InputStreamReader isr = null;
		try {
			if ( encoding!=null ) {
				isr = new InputStreamReader(stream, encoding);
			}
			else {
				isr = new InputStreamReader(stream);
			}
			BufferedReader br = new BufferedReader(isr);
			StringWriter sw = new StringWriter();
			int n;
			char[] data = new char[4096];
			while ((n = br.read(data, 0, data.length)) > 0) {
				sw.write(data, 0, n);
			}
			return sw.toString();
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

	public static void mkdir(String dir) {
		File f = new File(dir);
		f.mkdirs();
	}

	public static String stripFileExtension(String name) {
		if ( name==null ) return null;
		int lastDot = name.lastIndexOf('.');
		if ( lastDot<0 ) return name;
		return name.substring(0, lastDot);
	}

	/** e.g., replaceFileSuffix("foo.java", ".class") */
	public static String replaceFileSuffix(String s, String suffix) {
		if ( s==null || suffix==null ) return s;
		int dot = s.lastIndexOf('.');
		return s.substring(0,dot)+suffix;
	}

	public static String basename(String fullyQualifiedFileName) {
		Path path = Paths.get(fullyQualifiedFileName);
		return basename(path);
	}

	public static String dirname(String fullyQualifiedFileName) {
		Path path = Paths.get(fullyQualifiedFileName);
		return dirname(path);
	}

	public static String basename(Path path) {
		return path.getName(path.getNameCount()-1).toString();
	}

	public static String dirname(Path path) {
		return path.getName(0).toString();
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

	protected void rmFilesIn(final String dir, final String filePattern) {
        File tmpdirF = new File(dir);
        String[] files = tmpdirF.list();
        for(int i = 0; files!=null && i < files.length; i++) {
            if ( filePattern==null || files[i].matches(filePattern) ) {
                new File(dir+"/"+files[i]).delete();
            }
        }
    }

    protected void rmFilesIn(final String dir) { rmFilesIn(dir, null); }

    public void rmdir(final String dir) {
        File f = new File(dir);
        if ( f.exists() ) {
            rmFilesIn(dir);
            f.delete();
        }
    }
}
