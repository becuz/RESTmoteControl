package org.zooper.becuz.restmote.controller.keyboards;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.zooper.becuz.restmote.controller.PcControllerAbstract;

/**
 * Implementation of {@link Keyboard} using {@link Clipboard} 
 * @author bebo
 *
 */
public class KeyboardClipboard extends Keyboard{

//	private static final Logger log = Logger.getLogger(KeyboardClipboard.class.getName());

    @Override
    public boolean type(String characters) throws Exception {
        if (!super.type(characters)){
	    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        StringSelection stringSelection = new StringSelection( characters );
	        clipboard.setContents(stringSelection, stringSelection);
			Robot robot = PcControllerAbstract.getMyRobot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
        }
        return true;
    }

}
