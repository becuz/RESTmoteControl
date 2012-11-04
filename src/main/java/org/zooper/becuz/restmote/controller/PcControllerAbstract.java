package org.zooper.becuz.restmote.controller;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.becuz.restmote.utils.Utils;

public abstract class PcControllerAbstract {

	protected static final Logger log = Logger.getLogger(PcControllerAbstract.class.getName());
	
	/**
	 * TODO
	 */
	protected List<String> binDefaultPaths;
	
	protected SettingsBusiness settingsBusiness = new SettingsBusiness();
	protected MediaBusiness mediaBusiness = new MediaBusiness();
	protected MediaCategoryBusiness mediaCategoryBusiness = new MediaCategoryBusiness();
	protected ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	protected AppBusiness appBusiness = new AppBusiness();
	protected RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	
	/**
	 * 
	 */
	protected List<ActiveApp> activeApps = new ArrayList<ActiveApp>();
	
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
	 * Open the {@link filePath} through the {@link App}, if not null, with the default system application otherwise
	 * @param app
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public abstract boolean open(String filePath, App app) throws Exception;
	
	/**
	 * Closes the {@link App}
	 * @param app
	 * @return
	 * @throws Exception 
	 */
	public abstract boolean close(App app) throws Exception;
	
	/**
	 * Should release all the resources, ex destroy all created {@link Process}es. 
	 * @return
	 */
	public abstract boolean clean();
	
	/**
	 * Put the user input focus on the {@link ActiveApp} with the given pid
	 * @param pid
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public abstract ActiveApp focusApp(String pid) throws Exception;
	
	/**
	 * rebuilds {@link #activeApps}
	 */
	public abstract void rebuildActiveApps();
	
	/**
	 * Kill the running applications on the pc with the given pids
	 * @param pids
	 * @throws Exception 
	 */
	public abstract void killApps(List<String> pids) throws Exception;
	
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
	 * 
	 * @return all currently {@link ActiveApp}s
	 */
	public List<ActiveApp> getActiveApps() {
		return activeApps;
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
		    log.severe("Screen " + i + ": " + screenWidth + "x" + screenHeight);
		}
		log.severe("time to obtan screen size: " + (System.currentTimeMillis() - now));
		return results;
	}
	
	public ControlsManager getKeyboardControlsManager() {
		if (keyboardControlsManager == null){
			keyboardControlsManager = new ControlsManager();
			//first line
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_UP.toString(), 1, KeyEvent.VK_UP, 1, 0));
			//second line center
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_LEFT.toString(), 1, KeyEvent.VK_LEFT, 2, -1));
			Control cntrlEnter = Control.getControl(ControlDefaultTypeKeyboard.KBD_ENTER.toString(), 1, KeyEvent.VK_ENTER, 2, 0);
			cntrlEnter.setHideImg(true);
			cntrlEnter.setText("Enter");
			keyboardControlsManager.addControl(cntrlEnter);
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_RIGHT.toString(), 1, KeyEvent.VK_RIGHT, 2, 1));
			//third line		
			keyboardControlsManager.addControl(Control.getControl(ControlDefaultTypeKeyboard.KBD_DOWN.toString(), 1, KeyEvent.VK_DOWN, 3, 0));
			//fourth line
			Control cntrlAlt = Control.getControl(ControlDefaultTypeKeyboard.KBD_ALT.toString(), 1, KeyEvent.VK_ALT, 4, -2);
			cntrlAlt.setHideImg(true);
			cntrlAlt.setText("Alt");
			keyboardControlsManager.addControl(cntrlAlt);
			Control cntrlEsc = Control.getControl(ControlDefaultTypeKeyboard.KBD_ESC.toString(), 1, KeyEvent.VK_ESCAPE, 4, -1);
			cntrlEsc.setHideImg(true);
			cntrlEsc.setText("Esc");
			keyboardControlsManager.addControl(cntrlEsc);
			Control cntrlTab = Control.getControl(ControlDefaultTypeKeyboard.KBD_TAB.toString(), 1, KeyEvent.VK_TAB, 4, 1);
			cntrlTab.setHideImg(true);
			cntrlTab.setText("Tab<");
			keyboardControlsManager.addControl(cntrlTab);
			Set<Integer> list = new HashSet<Integer>();
			list.addAll(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_TAB}));
			Control cntrlITab = Control.getControl(ControlDefaultTypeKeyboard.KBD_ANTITAB.toString(), 1, list, 4, 2);
			cntrlITab.setHideImg(true);
			cntrlITab.setText("Tab>");
			keyboardControlsManager.addControl(cntrlITab);
		}
		return keyboardControlsManager;
	}
	
	public ControlsManager getMouseControlsManager() {
		if (mouseControlsManager == null){
			mouseControlsManager = new ControlsManager();
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CLICK1.toString(), null, 1, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_UP.toString(), null, 1, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CLICK3.toString(), null, 1, 1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_LEFT.toString(), null, 2, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_CENTER.toString(), null, 2, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_RIGHT.toString(), null, 2, 1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_WHEELUP.toString(), null, 3, -1));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_DOWN.toString(), null, 3, 0));
			mouseControlsManager.addControl(Control.getControl(ControlDefaultTypeMouse.MOUSE_WHEELDOWN.toString(), null, 3, 1));
		}
		return mouseControlsManager;
	}
	
	//*****************************************************************************************
	
	/**
	 * Scan the filesystem and call the persistence layer to store the {@link Media}s
	 * @return
	 */
	public void rootScan() {
		mediaBusiness.clearMediaRoots();
		long startTime = System.currentTimeMillis();
		Collection<MediaCategory> mediaCategories = mediaCategoryBusiness.getAll();
		if (mediaCategories != null){
			for(MediaCategory mediaCategory: mediaCategories){
				if (!Boolean.FALSE.equals(mediaCategory.getActive())){
					MediaRoot mediaRoot = new MediaRoot(mediaCategory);
					getMedias(mediaRoot);
					mediaBusiness.addMediaRoot(mediaRoot);
				}
			}
		}
		log.severe("Time to scan " + ((System.currentTimeMillis() - startTime)/1000));
	}
	
	//TODO the staff to manage the medias has to be in the proper business class
	public List<Media> getMedias(MediaRoot mediaRoot){
		mediaRoot.clearChildren();
		Settings settings = settingsBusiness.get();
		Set<String> paths = mediaRoot.getMediaCategory().getPaths().isEmpty() ? settings.getPaths() : mediaRoot.getMediaCategory().getPaths();
		for(String path: paths){
			File file = new File(path);
			if (file.exists()){
				MediaCategory mediaCategory = mediaRoot.getMediaCategory();
				Media mediaPath = new Media(path);
				mediaPath.setMediaChildren(
						getMedias(
								mediaPath.getPath(), 
								mediaCategory.isRoot() ? 1 : settings.getScanDepth(),
								new ArrayList<String>(mediaCategory.getExtensions()), 
								null));
				mediaRoot.addMediaChild(mediaPath);
			}
		}
		return mediaRoot.getMediaChildren();
	}
	
	/**
	 * 
	 * @param path root path for the scan
	 * @param depth negative to scan everything
	 * @param extensions extensions allowed (null or empty to scan all files)
	 * @param pattern filter by this regex 
	 * @return
	 */
	public List<Media> getMedias(String path, Integer depth, List<String> extensions, String pattern){
		log.severe("getMedia() path: " + path + ", depth: " + depth + ", extensions: " + extensions + ", pattern: " + pattern);
		Pattern p = null;
		if (!Utils.isEmpty(pattern)){
			p = Pattern.compile(pattern);
		}
		List<Media> results = new ArrayList<Media>();
		depth = depth == null ? settingsBusiness.get().getScanDepth() : depth;
		if (depth != 0) { //-1 is a valid value, means go deep indefinitely, as there are files.
			File f = new File(path);
			if (f.exists() && f.isDirectory()){
				File[] children = f.listFiles();
				if (children != null){
					for(File child: children){
						String childPath = child.getAbsolutePath();
						boolean fileAllowed = false;
						if (child.isDirectory()){
							fileAllowed = true;
						} else {
							if ((p == null || p.matcher(childPath).matches()) 
									&& (extensions == null || extensions.isEmpty() || extensions.contains(Utils.getFileExtension(childPath).toLowerCase()))){
								fileAllowed = true;
							}
						}
						if (fileAllowed){
							Media mediaChild = new Media(childPath);
							if (!child.isDirectory()){
								mediaChild.setFile(true);	//let's leave it at null in case it's a directory: less noise as we avoid serialize nulls
							}
							results.add(mediaChild);							
						}
					}
					if (depth != 1){ 
						for (Media mediaChild: results){
							mediaChild.setMediaChildren(getMedias(mediaChild.getPath(), depth-1, extensions, pattern));
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Put the user input focus on the argument App 
	 * @param app
	 * @return true if the operation had success
	 * @throws Exception 
	 */
	public ActiveApp focusApp(App app) throws Exception{
		List<String> pids = activeAppBusiness.getActivePidsOfApp(app, true);
		if (pids != null && pids.size() > 0){
			return focusApp(pids.get(0));
		}
		return null;
	}

}
