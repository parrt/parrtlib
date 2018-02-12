/*
 * Copyright (c) 2017 Terence Parr. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE file in the project root.
 */

package us.parr.lib;

import us.parr.lib.util.StreamVacuum;

import java.io.File;

public class ParrtSys {
	/** Given an executable command name and arguments, execute the process
	 *  and return the {stdout, stderr} in a String array.
	 *
	 *  The returned output strings are "" if empty, not null.
	 */
	public static String[] execInDir(String execPath, String... args) {
		try {
			Process process;
			if ( execPath!=null ) {
				process = Runtime.getRuntime().exec(args, null, new File(execPath));
			}
			else {
				process = Runtime.getRuntime().exec(args, null);
			}
			StreamVacuum stdoutVacuum = new StreamVacuum(process.getInputStream());
			StreamVacuum stderrVacuum = new StreamVacuum(process.getErrorStream());
			stdoutVacuum.start();
			stderrVacuum.start();
			process.waitFor();
			stdoutVacuum.join();
			stderrVacuum.join();
			return new String[]{stdoutVacuum.toString(), stderrVacuum.toString()};
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] exec(String... args) {
		return execInDir(null, args);
	}

	public static void execCommandLine(String cmd) {
		String[] exec = ParrtSys.exec("bash", "-c", cmd);
		if ( exec[1]!=null && exec[1].length()>0 ) {
			System.err.println(exec[1]);
		}
	}
}
