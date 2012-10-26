package org.zooper.remosko.controller.mouses;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Logger;

import org.zooper.remosko.controller.PcControllerAbstract;
import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.utils.Utils;

/**
 * Implementation of {@link Mouse} through {@link Robot}
 * @author bebo
 *
 */
public class MouseRobot implements Mouse {
	
	private static final Logger log = Logger.getLogger(MouseRobot.class.getName());
	
	@Override
	public boolean mouseMove(String move) throws Exception {
		Point pointerLocation = MouseInfo.getPointerInfo().getLocation();
		int x = (int) pointerLocation.getX();
		int y = (int) pointerLocation.getY();
		int[] screenSize = PcControllerFactory.getPcController().getScreenSizes()[0];
		int screenWidth = screenSize[0];
		int screenHeight = screenSize[1];
		log.severe("mouse is in positions: " + x + " " + y);
		String[] coordinates = move.split("x");
		for (int i = 0; i < coordinates.length; i++) {
			if (Utils.isEmpty(coordinates[i])) continue;
			if (coordinates[i].startsWith("+") || coordinates[i].startsWith("-")){
				int amount = Integer.parseInt(coordinates[i]);
				if (coordinates.length == 1 || i == 0){ //w
					x += amount;
				} else {
					y += amount;
				}
			} else if (coordinates[i].endsWith("%")){
				int amount = Integer.parseInt(coordinates[i].substring(0, coordinates[i].length()-1));
				if (coordinates.length == 1 || i == 0){ //w
					x =  screenWidth / 100 * amount;
				} else {
					y = screenHeight / 100 * amount;
				}
			} else {
				int amount = Integer.parseInt(coordinates[i]);
				if (coordinates.length == 1 || i == 0){ //w
					x = amount;
				} else {
					y = amount;
				}
			}
		}
		x = Math.max(0, Math.min(x, screenWidth));
		y = Math.max(0, Math.min(y, screenHeight));
		log.severe("putting mouse in positions: " + x + " " + y);
		PcControllerAbstract.getMyRobot().mouseMove(x, y);
		return true;
	}
	
	@Override
	public boolean mouseButtons(int button, BTN_ACTION btnAction) throws Exception {
		int btnMask = -1;
		switch (button) {
		case 1:
			btnMask = InputEvent.BUTTON1_MASK;
			break;
		case 2:
			btnMask = InputEvent.BUTTON2_MASK;
			break;
		case 3:
			btnMask = InputEvent.BUTTON3_MASK;
			break;
		default:
			break;
		}
		if (btnMask > 0){
			if (btnAction.equals(BTN_ACTION.CLICK) || btnAction.equals(BTN_ACTION.HOLD)){
				PcControllerAbstract.getMyRobot().mousePress(btnMask);
			}
			if (btnAction.equals(BTN_ACTION.CLICK) || btnAction.equals(BTN_ACTION.RELEASE)){
				PcControllerAbstract.getMyRobot().mouseRelease(btnMask);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseWheel(int wheel) throws Exception {
		PcControllerAbstract.getMyRobot().mouseWheel(wheel);
		return true;
	}

}
