package org.zooper.remosko.controller.mouses;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 * 
 * @author bebo
 *
 */
public interface Mouse {

	/**
	 *
	 */
	public enum BTN_ACTION {
		CLICK,
		HOLD,
		RELEASE
	}
	
	/**
	 * Example move values:
	 * 
	 * 912x22		full coordinates 
	 * x22			just y coordinate	
	 * 912			just x coordinate
	 * 32%x22%		x & t coordinate through percentage
	 * +1x-2		delta from current mouse position
	 * @param move
	 * @return
	 * @throws Exception
	 */
	public boolean mouseMove(String move) throws Exception;
	
	/**
	 * 
	 * @param wheel
	 * @return
	 * @throws Exception
	 */
	public boolean mouseWheel(int wheel) throws Exception;
	
	/**
	 * 
	 * @param button number of buttons
	 * @param btnAction 
	 * @return
	 * @throws Exception
	 */
	public boolean mouseButtons(int button, BTN_ACTION btnAction) throws Exception;
	
}
