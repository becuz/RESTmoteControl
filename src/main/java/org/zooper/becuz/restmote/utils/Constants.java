package org.zooper.becuz.restmote.utils;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.rest.resources.PcResource;


/**
 * Defined constants used in all application 
 * @author bebo
 */
public class Constants {

	/**
	 * Name of the root {@link MediaCategory} 
	 */
	public static final String MEDIA_ROOT = "root";
	
	/**
	 * Name of the subfolder in a icons theme, containing system icons (not belonging to the controls)
	 */
	public static final String NAME_DIR_ICON_SYSTEM = "system";
	
	/**
	 * Default theme (img dir in client/images/)
	 */
	public static final String DEFAULT_THEME = "iconic";
	
	/**
	 * Default value for {@link Settings#getScanDepth()}
	 */
	public static final int SCAN_DEPTH = 2;
	
	/**
	 * Default number of pixels to move the mouse in {@link PcResource} 
	 */
	public static final int DEFAULT_MOUSE_DELTA_MOVE = 8;
	
	/**
	 * Default number of pixels to move the wheel mouse in {@link PcResource}
	 */
	public static final int DEFAULT_MOUSE_WHEEL = 10;
	
	/**
	 * Constants value for {@link App#setArgumentsFile(String)}
	 */
	public static final String APP_ARGUMENT_FILE = "%f";
	
	/**
	 * Constants value for {@link App#setArgumentsDir(String)}
	 */
	public static final String APP_ARGUMENT_DIR = "%f";
	
}
