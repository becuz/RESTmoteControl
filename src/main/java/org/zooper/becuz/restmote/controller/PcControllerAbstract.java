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
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.conf.ModelFactoryAbstract;
import org.zooper.becuz.restmote.controller.keyboards.Keyboard;
import org.zooper.becuz.restmote.controller.keyboards.KeyboardRobot;
import org.zooper.becuz.restmote.controller.mouses.Mouse;
import org.zooper.becuz.restmote.controller.mouses.MouseRobot;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeKeyboard;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeMouse;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.model.VisualControlsManager;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils;

public abstract class PcControllerAbstract {

	protected static final Logger log = Logger.getLogger(PcControllerAbstract.class.getName());
	
	/**
	 * List of common paths containing bins and programs
	 */
	protected List<String> binDefaultPaths;
	
	/**
	 * Active {@link Process}es
	 */
	protected Map<App, Process> appProcesses = new HashMap<App, Process>();
	
	/**
	 * Used to send keyStrokes and mouse movements
	 */
	protected static Robot myRobot;
	
	/**
	 * keyboard control
	 */
	protected Keyboard keyboard;

	/**
	 * mouse control
	 */
	protected Mouse mouse;
	
	/**
	 * 
	 */
	private ControlsManager kbdControlsManager;
	
	/**
	 * 
	 */
	private VisualControlsManager kbdVisualControlsManager;
	
	/**
	 * 
	 */
	private VisualControlsManager mouseVisualControlsManager;
	
	//*****************************************************************************************
	
	protected PcControllerAbstract() {
		buildMouseControls();
		buildKeyboardsControls();
	}
	
	//*****************************************************************************************
	
	private void buildMouseControls() {
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_CLICK1.toString().toLowerCase(), 1, -1));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_UP.toString().toLowerCase(), 1, 0));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_CLICK3.toString().toLowerCase(), 1, 1));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_LEFT.toString().toLowerCase(), 2, -1));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_CENTER.toString().toLowerCase(), 2, 0));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_RIGHT.toString().toLowerCase(), 2, 1));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_WHEELUP.toString().toLowerCase(), 3, -1));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_DOWN.toString().toLowerCase(), 3, 0));
		getMouseVisualControlsManager().addControl(new VisualControl(ControlDefaultTypeMouse.MOUSE_WHEELDOWN.toString().toLowerCase(), 3, 1));
	}
	
	protected void buildKeyboardsControls() {
		//first line
		addKbdControls(ModelFactoryAbstract.getControlKeyboard(ControlDefaultTypeKeyboard.KBD_UP, KeyEvent.VK_UP, 1, 0));
		
		ControlInterface[] cs = ModelFactoryAbstract.getControlKeyboard("Tab <", KeyEvent.VK_TAB, 2, -2);
		((VisualControl)cs[1]).setHideImg(true);
		addKbdControls(cs);
		
		//second line
		addKbdControls(ModelFactoryAbstract.getControlKeyboard(ControlDefaultTypeKeyboard.KBD_LEFT, KeyEvent.VK_LEFT, 2, -1));
		addKbdControls(ModelFactoryAbstract.getControlKeyboard(ControlDefaultTypeKeyboard.KBD_ENTER, KeyEvent.VK_ENTER, 2, 0));
		addKbdControls(ModelFactoryAbstract.getControlKeyboard(ControlDefaultTypeKeyboard.KBD_RIGHT, KeyEvent.VK_RIGHT, 2, 1));
		
		cs = ModelFactoryAbstract.getControlKeyboard(
				"Tab >",  
				new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_TAB})), 2, 2);
		((VisualControl)cs[1]).setHideImg(true);
		addKbdControls(cs);
		
		//third line		
		cs = ModelFactoryAbstract.getControlKeyboard("Alt", KeyEvent.VK_ALT, 3, -2);
		((VisualControl)cs[1]).setHideImg(true);
		addKbdControls(cs);
		
		cs = ModelFactoryAbstract.getControlKeyboard("Ctrl", KeyEvent.VK_CONTROL, 3, -1);
		((VisualControl)cs[1]).setHideImg(true);
		addKbdControls(cs);
		
		cs = ModelFactoryAbstract.getControlKeyboard("Esc", KeyEvent.VK_ESCAPE, 3, 1);
		((VisualControl)cs[1]).setHideImg(true);
		addKbdControls(cs);
		
		//fourth line
		addKbdControls(ModelFactoryAbstract.getControlKeyboard(ControlDefaultTypeKeyboard.KBD_DOWN, KeyEvent.VK_DOWN, 4, 0));
	}
	
	protected void addKbdControls(ControlInterface[] control){
		getKbdControlsManager().addControl((Control)control[0]);
		getKbdVisualControlsManager().addControl((VisualControl)control[1]);
	}

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
	public abstract boolean toggleMute() throws Exception;
	
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
	
	public ControlsManager getKbdControlsManager() {
		if (kbdControlsManager == null){
			kbdControlsManager = new ControlsManager();
		}
		return kbdControlsManager;
	}

	public VisualControlsManager getKbdVisualControlsManager() {
		if (kbdVisualControlsManager == null){
			kbdVisualControlsManager = new VisualControlsManager();
		}
		return kbdVisualControlsManager;
	}

	public VisualControlsManager getMouseVisualControlsManager() {
		if (mouseVisualControlsManager == null){
			mouseVisualControlsManager = new VisualControlsManager();
		}
		return mouseVisualControlsManager;
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
			keyboard = new KeyboardRobot();
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
		killApps(BusinessFactory.getActiveAppBusiness().getActiveAppHandlesOfApp(app, true));
		Process process = appProcesses.get(app);
		if (process != null){
			process.destroy();
			appProcesses.remove(app);
		}
		rebuildActiveApps(false);
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
			ActiveApp activeApp = BusinessFactory.getActiveAppBusiness().getActiveAppByHandle(handle, false);
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
	 * 
	 * @param detailed 
	 */
	public void rebuildActiveApps(boolean detailed){
		log.info("rebuildActiveApps");
		BusinessFactory.getActiveAppBusiness().clearActiveApps();
		BufferedReader brInput = null;
		BufferedReader brError = null;
		try {
			Process p = execute(getCommandListApps().split("\\s+"));
			brInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			List<ActiveApp> activeApps = getRunningApps(brInput, brError);
			if (detailed){
				for(ActiveApp activeApp: activeApps){
					List<App> apps = BusinessFactory.getAppBusiness().getByFilters(null, activeApp.getName(), null, Utils.getOs(), true);
					activeApp.setHasApp(!apps.isEmpty());
				}
			}
			BusinessFactory.getActiveAppBusiness().addActiveApps(activeApps);
			//p.waitFor();
			p.destroy();
		} catch (Exception e) {
			log.error("", e);
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
				return BusinessFactory.getActiveAppBusiness().getActiveAppByHandle(handle, false);
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
	 * @param command es: "explorer http://www.google.com"
	 * @return
	 * @throws Exception
	 */
	public Process executeStringCommand(String command) throws IOException, InterruptedException{
		return execute(false, command.trim().split("\\s+"));
	}
	
	/**
	 * Execute an operating system process without waiting it
	 * @param commands es: {"explorer", "http://www.google.com"}
	 * @return
	 * @throws Exception
	 */
	public Process execute(String... commands) throws IOException, InterruptedException{
		return execute(false, commands);
	}
	
	/**
	 * Execute an operating system process
	 * @param waitFor
	 * @param commands
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	protected Process execute(boolean waitFor, String... commands) throws IOException, InterruptedException {
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
	public ActiveApp focusApp(App app, boolean refresh) throws Exception{
		List<String> handles = BusinessFactory.getActiveAppBusiness().getActiveAppHandlesOfApp(app, refresh);
		if (handles != null && handles.size() > 0){
			return focusApp(handles.get(0));
		}
		return null;
	}

}
