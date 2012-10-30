package org.zooper.becuz.restmote.controller.keyboards;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import org.zooper.becuz.restmote.controller.PcControllerFactory;

/**
 * Implementation of {@link Keyboard} using {@link Robot}
 * @author bebo
 *
 */
public class KeyboardRobot extends Keyboard {

	private static final Logger log = Logger.getLogger(KeyboardRobot.class.getName());
	
	@Override
    public boolean type(String characters) throws Exception {
		if (!super.type(characters)){
			int length = characters.length();
	        for (int i = 0; i < length; i++) {
	            char character = characters.charAt(i);
	            type(character);
	        }
        }
        return true;
    }
	
    public static int[] getKeyEvents(char character) {
        switch (character) {
        case 'a': return new int[]{KeyEvent.VK_A};
        case 'b': return new int[]{KeyEvent.VK_B};
        case 'c': return new int[]{KeyEvent.VK_C};
        case 'd': return new int[]{KeyEvent.VK_D};
        case 'e': return new int[]{KeyEvent.VK_E};
        case 'f': return new int[]{KeyEvent.VK_F};
        case 'g': return new int[]{KeyEvent.VK_G};
        case 'h': return new int[]{KeyEvent.VK_H};
        case 'i': return new int[]{KeyEvent.VK_I};
        case 'j': return new int[]{KeyEvent.VK_J};
        case 'k': return new int[]{KeyEvent.VK_K};
        case 'l': return new int[]{KeyEvent.VK_L};
        case 'm': return new int[]{KeyEvent.VK_M};
        case 'n': return new int[]{KeyEvent.VK_N};
        case 'o': return new int[]{KeyEvent.VK_O};
        case 'p': return new int[]{KeyEvent.VK_P};
        case 'q': return new int[]{KeyEvent.VK_Q};
        case 'r': return new int[]{KeyEvent.VK_R};
        case 's': return new int[]{KeyEvent.VK_S};
        case 't': return new int[]{KeyEvent.VK_T};
        case 'u': return new int[]{KeyEvent.VK_U};
        case 'v': return new int[]{KeyEvent.VK_V};
        case 'w': return new int[]{KeyEvent.VK_W};
        case 'x': return new int[]{KeyEvent.VK_X};
        case 'y': return new int[]{KeyEvent.VK_Y};
        case 'z': return new int[]{KeyEvent.VK_Z};
        case 'A': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A};
        case 'B': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_B};
        case 'C': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_C};
        case 'D': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_D};
        case 'E': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_E};
        case 'F': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_F};
        case 'G': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_G};
        case 'H': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_H};
        case 'I': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_I};
        case 'J': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_J};
        case 'K': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_K};
        case 'L': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_L};
        case 'M': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_M};
        case 'N': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_N};
        case 'O': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_O};
        case 'P': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_P};
        case 'Q': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Q};
        case 'R': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_R};
        case 'S': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_S};
        case 'T': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_T};
        case 'U': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_U};
        case 'V': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_V};
        case 'W': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_W};
        case 'X': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_X};
        case 'Y': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Y};
        case 'Z': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_Z};
        case '`': return new int[]{KeyEvent.VK_BACK_QUOTE};
        case '0': return new int[]{KeyEvent.VK_0};
        case '1': return new int[]{KeyEvent.VK_1};
        case '2': return new int[]{KeyEvent.VK_2};
        case '3': return new int[]{KeyEvent.VK_3};
        case '4': return new int[]{KeyEvent.VK_4};
        case '5': return new int[]{KeyEvent.VK_5};
        case '6': return new int[]{KeyEvent.VK_6};
        case '7': return new int[]{KeyEvent.VK_7};
        case '8': return new int[]{KeyEvent.VK_8};
        case '9': return new int[]{KeyEvent.VK_9};
        case '-': return new int[]{KeyEvent.VK_MINUS};
        case '=': return new int[]{KeyEvent.VK_EQUALS};
        case '~': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE};
        case '!': return new int[]{KeyEvent.VK_EXCLAMATION_MARK};
        case '@': return new int[]{KeyEvent.VK_AT};
        case '#': return new int[]{KeyEvent.VK_NUMBER_SIGN};
        case '$': return new int[]{KeyEvent.VK_DOLLAR};
        case '%': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_5};
        case '^': return new int[]{KeyEvent.VK_CIRCUMFLEX};
        case '&': return new int[]{KeyEvent.VK_AMPERSAND};
        case '*': return new int[]{KeyEvent.VK_ASTERISK};
        case '(': return new int[]{KeyEvent.VK_LEFT_PARENTHESIS};
        case ')': return new int[]{KeyEvent.VK_RIGHT_PARENTHESIS};
        case '_': return new int[]{KeyEvent.VK_UNDERSCORE};
        case '+': return new int[]{KeyEvent.VK_PLUS};
        case '\t': return new int[]{KeyEvent.VK_TAB};
        case '\n': return new int[]{KeyEvent.VK_ENTER};
        case '[': return new int[]{KeyEvent.VK_OPEN_BRACKET};
        case ']': return new int[]{KeyEvent.VK_CLOSE_BRACKET};
        case '\\': return new int[]{KeyEvent.VK_BACK_SLASH};
        case '{': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET};
        case '}': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET};
        case '|': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH};
        case ';': return new int[]{KeyEvent.VK_SEMICOLON};
        case ':': return new int[]{KeyEvent.VK_COLON};
        case '\'': return new int[]{KeyEvent.VK_QUOTE};
        case '"': return new int[]{KeyEvent.VK_QUOTEDBL};
        case ',': return new int[]{KeyEvent.VK_COMMA};
        case '<': return new int[]{KeyEvent.VK_LESS};
        case '.': return new int[]{KeyEvent.VK_PERIOD};
        case '>': return new int[]{KeyEvent.VK_GREATER};
        case '/': return new int[]{KeyEvent.VK_SLASH};
        case '?': return new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH};
        case ' ': return new int[]{KeyEvent.VK_SPACE};
        }
        return null;
    }
    

//  private void typeNumPad(int digit) {
//      switch (digit) {
//      case 0: doType(KeyEvent.VK_NUMPAD0); break;
//      case 1: doType(KeyEvent.VK_NUMPAD1); break;
//      case 2: doType(KeyEvent.VK_NUMPAD2); break;
//      case 3: doType(KeyEvent.VK_NUMPAD3); break;
//      case 4: doType(KeyEvent.VK_NUMPAD4); break;
//      case 5: doType(KeyEvent.VK_NUMPAD5); break;
//      case 6: doType(KeyEvent.VK_NUMPAD6); break;
//      case 7: doType(KeyEvent.VK_NUMPAD7); break;
//      case 8: doType(KeyEvent.VK_NUMPAD8); break;
//      case 9: doType(KeyEvent.VK_NUMPAD9); break;
//      }
//  }
    
    private void type(char character) throws AWTException {
    	int[] keyEvents = getKeyEvents(character);
    	if (keyEvents == null){
            throw new IllegalArgumentException("Cannot type character " + character);
    	}
    	doType(keyEvents);
    }
    
    private void doType(int... keyCodes) throws AWTException {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) throws AWTException {
        if (length == 0) {
            return;
        }
        Robot robot = PcControllerFactory.getPcController().getMyRobot();
        robot.keyPress(keyCodes[offset]);
        try {
        	doType(keyCodes, offset + 1, length - 1);
        } catch (IllegalArgumentException e){
        	log.severe(e.getMessage());
        	for (int j = offset; j >= 0; j--) {
        		robot.keyRelease(keyCodes[offset]);
			}
        	return;
        }
        robot.keyRelease(keyCodes[offset]);
    }
    

}
