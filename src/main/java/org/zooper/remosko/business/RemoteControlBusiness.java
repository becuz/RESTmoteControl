package org.zooper.remosko.business;

import java.awt.AWTException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.zooper.remosko.business.interfaces.BusinessAbstract;
import org.zooper.remosko.controller.PcControllerAbstract;
import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.controller.keyboards.KeyboardRobot;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.Control;
import org.zooper.remosko.model.Control.ControlDefaultTypeApp;
import org.zooper.remosko.model.Control.ControlDefaultTypeKeyboard;
import org.zooper.remosko.model.Control.ControlDefaultTypeMouse;
import org.zooper.remosko.model.KeysEvent;
import org.zooper.remosko.model.transport.ActiveApp;
import org.zooper.remosko.utils.Utils;

public class RemoteControlBusiness extends BusinessAbstract{

	protected static final Logger log = Logger.getLogger(RemoteControlBusiness.class.getName());
	
	public RemoteControlBusiness() {
		super();
	}
	
	public boolean openFile(String filePath, App app) throws Exception{
		if (app != null){
			if (app.isForceOneInstance()){
				PcControllerFactory.getPcController().close(app);
			}
		}
		return PcControllerFactory.getPcController().open(filePath, app);
	}
	
	public boolean closeMedia(App app) throws Exception{
		return PcControllerFactory.getPcController().close(app);
	}
	
	
	public void control(String appName, String controlName, Character c)  throws Exception {
		control(Utils.isEmpty(appName) ? null : getAppBusiness().getByName(appName), controlName, c);
	}
	
	public void control(String controlName, Character c)  throws Exception {
		controlByPid(null, controlName, c);
	}
	
	public void controlByPid(String pid, String controlName, Character c)  throws Exception {
		ActiveApp activeApp = null;
		if (!Utils.isEmpty(pid)){
			activeApp = PcControllerFactory.getPcController().focusApp(pid);
		} else {
			activeApp = getActiveAppBusiness().getActiveAppFocused(true);	//let's try with the app that has focus
		}
		App app = getAppBusiness().getByName(activeApp.getName());
		if (app == null){
			throw new IllegalArgumentException("App " + activeApp.getName() + " not configured");
		}
		control(app, controlName, c);
	}
	
	/**
	 * Execute the control on the pc.
	 * controlName can be a {@link ControlDefaultTypeKeyboard}, a {@link ControlDefaultTypeMouse}, or a {@link ControlDefaultTypeApp}
	 * If it's a ControlDefaultTypeApp, this method focus on the corresponding app, if available, or in the current one with focus. 
	 * If exists a mapping for the control for the app, send the mapped shortvut.
	 * @param controlName name, of the control, ex: KBD_ESC, MOUSE_CLICK1, or FULLSCREEN
	 * @param pid
	 * @param app
	 * @throws Exception 
	 */
	public void control(App app, String controlName, Character c) throws Exception {
		if (Utils.isEmpty(controlName) && c == null){
			throw new IllegalArgumentException("No control specified");
		}
		if (!getActiveAppBusiness().getActiveAppFocused(true).getName().equals(app.getName())){ //if a window of this App type is not already focused
			if (PcControllerFactory.getPcController().focusApp(app) == null){
				throw new IllegalArgumentException("No running instance of App " + app.getName());
			}
		}
		if (!Utils.isEmpty(controlName)){
			Control control = app.getControlsManager().getControl(controlName.toUpperCase());
			if (control == null){
				throw new IllegalArgumentException("No control " + controlName + " found for app " + app.getName());
			}
			control(control);
		} else {
			control(c);
		}
	}
	
	public void control(Control control) throws AWTException{
		control(control.getKeysEvents());
	}
	
	private void control(Character c) throws AWTException{
		int[] k = KeyboardRobot.getKeyEvents(c);
		Set<Integer> s = new HashSet<Integer>();
		for (int i: k){
			s.add(i);
		}
		Set<KeysEvent> keysEvents = new HashSet<KeysEvent>();
		keysEvents.add(new KeysEvent(s, 1));
		control(keysEvents);
	}
	
	private void control(Set<KeysEvent> keysEvents) throws AWTException{
		for(KeysEvent keysEvent: keysEvents){
			Integer repeatControl = keysEvent.getRepeat();
			int repeat = repeatControl == null ? 1 : repeatControl;
//				System.out.println("key pressed " + KeyEvent.VK_F + " " + key);
				for (int i = 0; i < repeat; i++) {
					for(int key: keysEvent.getKeys()){
						PcControllerAbstract.getMyRobot().keyPress(key);
					}
					for(int key: keysEvent.getKeys()){
						PcControllerAbstract.getMyRobot().keyRelease(key);
					}
				}
		}
	}
	
	public boolean poweroff() throws Exception{
		return PcControllerFactory.getPcController().poweroff();
	}
	
	public boolean suspend() throws Exception{
		return PcControllerFactory.getPcController().suspend();
	}
	
	public boolean mute() throws Exception{
		return PcControllerFactory.getPcController().mute();
	}
	
	public boolean setVolume(int volume) throws Exception{
		return PcControllerFactory.getPcController().setVolume(volume);
	}
	
}
