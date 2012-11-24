package org.zooper.becuz.restmote.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getName());

	/**
	 * Runtime root path, parent of dirs db/, ui/, etc..
	 */
	private static String restmoteRootDirAbsolutePath;

	/**
	 * Operating systems
	 */
	public enum OS {
		WINDOWS, LINUX, MAC
	}

	/**
	 * Print some memory info
	 */
	public static void printUsageMemory() {
		log.info("totalMemory "
				+ Utils.getSizeHR(Runtime.getRuntime().totalMemory()));
		log.info("maxMemory "
				+ Utils.getSizeHR(Runtime.getRuntime().maxMemory()));
		log.info("freeSize "
				+ Utils.getSizeHR(Runtime.getRuntime().freeMemory()));
	}

	/**
	 * <p>
	 * isEmpty.
	 * </p>
	 * 
	 * @param s
	 *            a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isEmpty(String s) {
		return (s == null || s.length() == 0);
	}

	/**
	 * Copied from Arduino src. Re-maps a number from one range to another. That
	 * is, a value of fromLow would get mapped to toLow, a value of fromHigh to
	 * toHigh, values in-between to values in-between, etc
	 * 
	 * @param x
	 * @param in_min
	 * @param in_max
	 * @param out_min
	 * @param out_max
	 * @return
	 */
	public static int map(int x, int in_min, int in_max, int out_min,
			int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	/**
	 * Create a human readable representation of a byte size
	 * 
	 * @param bytesSize
	 *            a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getSizeHR(long bytesSize) {
		int kb = (int) (bytesSize / 1024);
		int mb = kb / 1024;
		return (mb >= 1 ? (mb + "Mb") : kb >= 1 ? (kb + "Kb")
				: (bytesSize + "b"));
	}

	/**
	 * Return the {@link OS}
	 * 
	 * @see http://mindprod.com/jgloss/properties.html#OSNAME
	 * @see http://www.javaneverdie.com/java/java-os-name-property-values/
	 * @return
	 */
	public static OS getOs() {
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")) {
			return OS.MAC;
		}
		if (osName.startsWith("Linux")) {
			return OS.LINUX;
		}
		return OS.WINDOWS;
	}

	/**
	 * Return the default home of the current user
	 * 
	 * @return
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * @return the absolute root path of the project
	 */
	public static String getRootDir() {
		if (restmoteRootDirAbsolutePath == null) {
			restmoteRootDirAbsolutePath = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			restmoteRootDirAbsolutePath = restmoteRootDirAbsolutePath.substring(
					/* TODO check why 1 */ getOs().equals(OS.WINDOWS) ? 1 : 0, restmoteRootDirAbsolutePath.length() - 1);
			restmoteRootDirAbsolutePath = restmoteRootDirAbsolutePath.substring(0, restmoteRootDirAbsolutePath.lastIndexOf("/") + 1);
			String[] binPaths = {"build/", "bin/"}; //each IDE and OS has its own mechanisms
			for(String binPath : binPaths){
				if (restmoteRootDirAbsolutePath.endsWith(binPath)) { // netbeans bin dir is build/
					restmoteRootDirAbsolutePath = restmoteRootDirAbsolutePath
							.substring(0, restmoteRootDirAbsolutePath.length() - binPath.length());
				}
			}
		}
		return restmoteRootDirAbsolutePath;
	}

	public static String getThemeDir(String theme) {
		return Utils.getRootDir() + "/client/images/" + theme;
	}

	/**
	 * 
	 * @return
	 */
	public static String getFileExtension(String path) {
		try {
			return path.substring(path.lastIndexOf('.') + 1).toLowerCase();
		} catch (Exception e) {
			return null;
		}
	}

	public static String join(Set<String> set, String j) {
		StringBuffer stringBuffer = new StringBuffer();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String s = it.next();
			stringBuffer.append(s);
			if (it.hasNext()) {
				stringBuffer.append(j);
			}
		}
		return stringBuffer.toString();
	}

	static public String getContents(File aFile) {
		StringBuilder contents = new StringBuilder();
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contents.toString();
	}

}
