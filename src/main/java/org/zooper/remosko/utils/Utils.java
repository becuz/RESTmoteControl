package org.zooper.remosko.utils;

import java.util.logging.Logger;

import org.zooper.remosko.controller.mouses.MouseRobot;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getName());
	
	/**
	 * Runtime root path, parent of dirs db/, ui/, etc..
	 */
	private static String absoluteRootProjectPath;
	
	/**
	 * Operating systems 
	 */
	public enum OS{WINDOWS, LINUX, MAC}

	/**
	 * Print some memory info
	 */
	public static void printUsageMemory(){
		log.info("totalMemory " + Utils.getSizeHR(Runtime.getRuntime().totalMemory()));
		log.info("maxMemory " + Utils.getSizeHR(Runtime.getRuntime().maxMemory()));
		log.info("freeSize " + Utils.getSizeHR(Runtime.getRuntime().freeMemory()));
	}
	
	/**
	 * <p>isEmpty.</p>
	 *
	 * @param s a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isEmpty(String s){
		return (s == null || s.length() == 0);
	}
	
	/**
	 * Copied from Arduino src. 
	 * Re-maps a number from one range to another. That is, a value of fromLow would get mapped to toLow, a value of fromHigh to toHigh, values in-between to values in-between, etc
	 * @param x
	 * @param in_min
	 * @param in_max
	 * @param out_min
	 * @param out_max
	 * @return
	 */
	public static int map(int x, int in_min, int in_max, int out_min, int out_max) {
		  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	/**
	 * Create a human readable representation of a byte size
	 *
	 * @param bytesSize a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getSizeHR(long bytesSize) {
		int kb = (int) (bytesSize / 1024);
		int mb = kb / 1024;
		return (mb >= 1 ? (mb + "Mb") : kb >= 1 ? (kb + "Kb") : (bytesSize + "b"));
	}
	
	/**
	 * Return the {@link OS}
	 * @see http://mindprod.com/jgloss/properties.html#OSNAME
	 * @see http://www.javaneverdie.com/java/java-os-name-property-values/
	 * @return
	 */
	public static OS getOs(){
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")){
			return OS.MAC;
		}
		if (osName.startsWith("Linux")){
			return OS.LINUX;
		}
		return OS.WINDOWS;
	}

	/**
	 * Return the default home of the current user
	 * @return
	 */
	public static String getUserHome(){
		return System.getProperty("user.home");
	}
	
	/**
	 * Return the absolute root path of the project
	 * @return
	 */
	public static String getRootDir() {
		if (absoluteRootProjectPath == null){
			absoluteRootProjectPath = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			absoluteRootProjectPath = absoluteRootProjectPath.substring(1, absoluteRootProjectPath.length()-1);
			absoluteRootProjectPath = absoluteRootProjectPath.substring(0, absoluteRootProjectPath.lastIndexOf("/")+1);
		}
    	return absoluteRootProjectPath;
	}

	
}
