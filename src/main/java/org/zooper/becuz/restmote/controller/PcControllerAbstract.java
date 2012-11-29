package org.zooper.becuz.restmote.controller;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.business.RemoteControlBusiness;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.controller.keyboards.Keyboard;
import org.zooper.becuz.restmote.controller.keyboards.KeyboardClipboard;
import org.zooper.becuz.restmote.controller.mouses.Mouse;
import org.zooper.becuz.restmote.controller.mouses.MouseRobot;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeKeyboard;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeMouse;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils;

public abstract class PcControllerAbstract {

	protected static final Logger log = Logger.getLogger(PcControllerAbstract.class.getName());
	
	/**
	 * TODO use them
	 */
	protected List<String> binDefaultPaths;
	
	/**
	 * Active {@link Process}es
	 */
	protected Map<App, Process> appProcesses = new HashMap<App, Process>();
	
	protected SettingsBusiness settingsBusiness = new SettingsBusiness();
	protected MediaBusiness mediaBusiness = new MediaBusiness();
	protected MediaCategoryBusiness mediaCategoryBusiness = new MediaCategoryBusiness();
	protected ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	protected AppBusiness appBusiness = new AppBusiness();
	protected RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	
	/**
	 * Used to send keyStrokes and mouse movements
	 */
	protected static Robot myRobot;
	
	/**
	 * Implementation to simulate keyboard
	 */
	protected Keyboard keyboard;

	/**
	 * Implementation to simulate mouse
	 */
	protected Mouse mouse;
	
	/**
	 * 
	 */
	private ControlsManager mouseControlsManager;

	/**
	 * 
	 */
	private ControlsManager keyboardControlsManager;
	
	//*****************************************************************************************
	
	protected PcControllerAbstract() {
	}
	
	//*****************************************************************************************
	
	/**
	 * @return the executable that open a file with its default application.
	 */
	public abstract String getCommandOpenFile();
	
	/**
	 * @param handle of the app to put on focus (active)
	 * @return the full command to executes to make the app with the handle argument active
	 */
	public abstract String getCommandFocusApp(String handle);
	
	/**
	 * @param handle of the app to put on focus (active)
	 * @return the full command to executes to close the app with the handle argument
	 */
	public abstract String getCommandKillApp(String handle);
	
	/**
	 * 
	 * @return the full command to executes to list running window apps 
	 */
	public abstract String getCommandListApps();	
	
	/**
	 * @param brInput
	 * @param brOutput
	 * @return list of running apps
	 * @throws IOException 
	 */
	public abstract List<ActiveApp> getRunningApps(BufferedReader brInput, BufferedReader brError) throws IOException;
	
	/**
	 * Mute the pc volume
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public abstract boolean mute() throws Exception;
	
	/**
	 * Set the pc volume
	 * @param vol between 0 and 100 inclusive
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public abstract boolean setVolume(int vol) throws Exception;
	
	/**
	 * Poweroff the pc
	 * @return
	 * @throws Exception 
	 */
	public abstract boolean poweroff() throws Exception;
	
	/**
	 * Suspend the pc
	 * @return
	 * @throws Exception 
	 */
	public abstract boolean suspend() throws Exception;
	
	//*****************************************************************************************
	
	public SettingsBusiness getSettingsBusiness() {
		return settingsBusiness;
	}

	public MediaBusiness getMediaBusiness() {
		return mediaBusiness;
	}

	public MediaCategoryBusiness getMediaCategoryBusiness() {
		return mediaCategoryBusiness;
	}

	public ActiveAppBusiness getActiveAppBusiness() {
		return activeAppBusiness;
	}

	public AppBusiness getAppBusiness() {
		return appBusiness;
	}

	public RemoteControlBusiness getRemoteControlBusiness() {
		return remoteControlBusiness;
	}
	
	/**
	 * 
	 * @return
	 * @throws AWTException
	 */
	public static Robot getMyRobot() throws AWTException {
		if (myRobot == null){
			myRobot = new Robot();
		}
		return myRobot;
	}
	
	/**
	 * 
	 * @return
	 * @throws AWTException
	 */
	public Keyboard getKeyboard() throws AWTException {
		if (keyboard == null){
			keyboard = new KeyboardClipboard();
		}
		return keyboard;
	}
	
	/**
	 * 
	 * @return
	 * @throws AWTException
	 */
	public Mouse getMouse() throws AWTException {
		if (mouse == null){
			mouse = new MouseRobot();
		}
		return mouse;
	}
	
	
	/**
	 * @return paths where executable application are usually installed
	 */
	public List<String> getBinDefaultPaths(){
		return binDefaultPaths;
	}

	/**
	 * Return the dimensions (WxH) of all pc' screens
	 * TODO cache these values
	 * @return
	 */
	public int[][] getScreenSizes(){
//		default version to obtain screen size
//		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
//		Dimension dim = toolkit.getScreenSize();
		long now = System.currentTimeMillis();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		int[][] results = new int[gs.length][2];
		for (int i=0; i<gs.length; i++) {
		    DisplayMode dm = gs[i].getDisplayMode();
		    int screenWidth = dm.getWidth();
		    int screenHeight = dm.getHeight();
		    results[i][0] = screenWidth;
		    results[i][1] = screenHeight;
		    log.debug("Screen " + i + ": " + screenWidth + "x" + screenHeight);
		}
		log.debug("time to obtan screen size: " + (System.currentTimeMillis() - now));
		return results;
	}
	
	public ControlsManager getKeyboardControlsManager() {
		if (keyboardControlsManager == null){
			keyboardControlsManager = new ControlsManager();
			//first line
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_UP, KeyEvent.VK_UP, 1, 0));
			
			//second line
			Control cntrlTab = Control.getControl("Tab <", 1, KeyEvent.VK_TAB, 2, -2);
			cntrlTab.setHideImg(true);
			keyboardControlsManager.addControl(cntrlTab);
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_LEFT, KeyEvent.VK_LEFT, 2, -1));
			Control cntrlEnter = Control.getControl(ControlDefaultTypeKeyboard.KBD_ENTER, KeyEvent.VK_ENTER, 2, 0);
			keyboardControlsManager.addControl(cntrlEnter);
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_RIGHT, KeyEvent.VK_RIGHT, 2, 1));
			Control cntrlITab = Control.getControl("Tab >", 1, 
					new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_TAB})), 2, 2);
			cntrlITab.setHideImg(true);
			keyboardControlsManager.addControl(cntrlITab);
			
			//third line		
			Control cntrlAlt = Control.getControl("Alt", 1, KeyEvent.VK_ALT, 3, -2);
			cntrlAlt.setHideImg(true);
			keyboardControlsManager.addControl(cntrlAlt);
			
			Control cntrl = Control.getControl("Ctrl", 1, KeyEvent.VK_CONTROL, 3, -1);
			cntrl.setHideImg(true);
			keyboardControlsManager.addControl(cntrl);
			
			Control cntrlMeta = Control.getControl("Meta", 1, KeyEvent.VK_META, 3, 0);
			cntrlMeta.setHideImg(true);
			keyboardControlsManager.addControl(cntrlMeta);
			
			Control cntrlEsc = Control.getControl("Esc", 1, KeyEvent.VK_ESCAPE, 3, 1);
			cntrlEsc.setHideImg(true);
			keyboardControlsManager.addControl(cntrlEsc);
			
			//fourth line
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_DOWN, KeyEvent.VK_DOWN, 4, 0));			
			
			
		}
		return keyboardControlsManager;
	}
	
	public ControlsManager getMouseControlsManager() {
		if (mouseControlsManager == null){
			mouseControlsManager = new ControlsManager();
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CLICK1, 1, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_UP, 1, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CLICK3, 1, 1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_LEFT, 2, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CENTER, 2, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_RIGHT, 2, 1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_WHEELUP, 3, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_DOWN, 3, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_WHEELDOWN, 3, 1));
		}
		return mouseControlsManager;
	}
	
	//*****************************************************************************************
	
	/**
	 * Open the {@link filePath} through the {@link App}, if not null, with the default system application otherwise
	 * @param app
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public boolean openFile(String filePath, App app) throws Exception {
		File f = new File(filePath);
		if (!f.exists()){
			throw new IllegalArgumentException("File " + filePath + " doesn't exist.");
		}
		Process process;
		if (app != null){
			String argumentMedia = 
					f.isDirectory() ? app.getArgumentsDir().replace(Constants.APP_ARGUMENT_DIR, filePath) 
							: app.getArgumentsFile().replace(Constants.APP_ARGUMENT_FILE, filePath); 
		    String[] commands = new String[]{app.getPath(), argumentMedia};
			process = execute(commands);
			appProcesses.put(app, process);
		} else {
			String openFileCommand = getCommandOpenFile();
			if (Utils.isEmpty(openFileCommand)){
				throw new Exception("In this system is impossible to open files with their default application");
			}
			process = execute(openFileCommand, filePath);
		}
		return process != null;
	}
	
	/**
	 * Closes the {@link App}
	 * @param app
	 * @return
	 * @throws Exception 
	 */
	public boolean closeApp(App app) throws Exception {
		killApps(activeAppBusiness.getActiveHandlesOfApp(app, true));
		Process process = appProcesses.get(app);
		if (process != null){
			process.destroy();
			appProcesses.remove(app);
		}
		rebuildActiveApps();
		return process != null;
	}
	
	/**
	 * 
	 * @param handles
	 * @throws Exception
	 */
	public void killActiveApps(List<String> handles) throws Exception{
		killApps(handles);
		for(String handle: handles){
			ActiveApp activeApp = getActiveAppBusiness().getActiveAppByHandle(handle, false);
			Iterator<Entry<App, Process>> it = appProcesses.entrySet().iterator();
			while (it.hasNext()) {
				Entry<App, Process> entry = it.next();
				if (activeApp.isInstanceOf(entry.getKey())){
					entry.getValue().destroy();
					it.remove();
				}
			}
		}
	}
	
	
	/**
	 * rebuilds {@link #activeApps}
	 */
	public void rebuildActiveApps(){
		log.info("rebuildActiveApps");
		getActiveAppBusiness().clearActiveApps();
		BufferedReader brInput = null;
		BufferedReader brError = null;
		try {
			Process p = execute(getCommandListApps().split("\\s+"));
			brInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			getActiveAppBusiness().addActiveApps(getRunningApps(brInput, brError));
			//p.waitFor();
			p.destroy();
		} catch (Exception e) {
			log.error(e.getMessage());
	    } finally {
	    	try {
	    		if (brInput != null){
	    			brInput.close();
	    		}
	    		if (brError != null){
	    			brError.close();
	    		}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
	    }
	}
	
	/**
	 * Put the user input focus on the {@link ActiveApp} with the given handle
	 * @param handle
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public ActiveApp focusApp(String handle) throws Exception {
		if (!Utils.isEmpty(handle)){
			if (execute(true, getCommandFocusApp(handle).split("\\s+")) != null){ //TODO waitFor?
				return activeAppBusiness.getActiveAppByHandle(handle, false);
			}
		}
		return null;
	}
	
	/**
	 * Kill the running applications on the pc with the given handles
	 * @param handles
	 * @throws Exception 
	 */
	public void killApps(List<String> handles) throws Exception { //TODO argument boolean gracefully?
		if (handles != null){
			for(String handle: handles){
				execute(getCommandKillApp(handle).split("\\s+"));
			}
		}
	}
	
	/**
	 * It destroys all created {@link Process}es. 
	 * @return
	 */
	public boolean cleanProcesses(){
		for(Process process: appProcesses.values()){
			process.destroy();
		}
		appProcesses.clear();
		return true;
	}
	
	/**
	 * Execute an operating system process without waiting it
	 * @param commands
	 * @return
	 * @throws Exception
	 */
	protected Process execute(String... commands) throws Exception{
		return execute(false, commands);
	}
	
	/**
	 * Execute an operating system process
	 * @param waitFor
	 * @param commands
	 * @return
	 * @throws Exception
	 */
	protected Process execute(boolean waitFor, String... commands) throws Exception{
		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		Process process = processBuilder.start();
//		BufferedReader bri = new BufferedReader(new InputStreamReader(process.getInputStream()));
//		BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//		String line = bri.readLine();
//		while ((line = bri.readLine()) != null) {
//			System.out.println(line);
//	      }
//		while ((line = bre.readLine()) != null) {	//errors
//			System.err.println(line);
//		}
	    if (waitFor){
	    	process.waitFor();
	    }
	    return process;
	}
	
	/**
	 * Put the user input focus on the argument App 
	 * @param app
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public ActiveApp focusApp(App app) throws Exception{
		List<String> handles = activeAppBusiness.getActiveHandlesOfApp(app, true);
		if (handles != null && handles.size() > 0){
			return focusApp(handles.get(0));
		}
		return null;
	}

}
