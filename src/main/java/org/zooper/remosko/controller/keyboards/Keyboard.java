package org.zooper.remosko.controller.keyboards;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import org.zooper.remosko.controller.PcControllerAbstract;
import org.zooper.remosko.controller.PcControllerFactory;

/**
 * Class used to type characters on the screen 
 * @author bebo
 */
public abstract class Keyboard {

	/**
	 * Map containing special characters
	 */
	public static Map<String, Integer> SPECIAL_CHARS = new HashMap<String, Integer>();
	
	static {
		//TODO, find a better way than defined string as $RES_BACK_SPACE 
		SPECIAL_CHARS.put("$RES_BACK_SPACE" , KeyEvent.VK_BACK_SPACE); //Backspace char
	}
	
	/**
	 * Type the characters on the characters params, if are some special ones defined in {@link #SPECIAL_CHARS}
	 * @param characters
	 * @return true if the characters are handled correctly
	 * @throws Exception 
	 */
	public boolean type(String characters) throws Exception {
		Integer keycode = SPECIAL_CHARS.get(characters);
		if (keycode != null){
			PcControllerAbstract.getMyRobot().keyPress(keycode);
			PcControllerAbstract.getMyRobot().keyRelease(keycode);
			return true;
		}
		return false;
	}
	
}
