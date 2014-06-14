package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlCategory;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils.OS;

public class ModelFactoryLinux extends ModelFactoryAbstract{

	@Override
	public Command getACommand() {
		return new Command("ls", "ls", "show files of current dir");
	}
	
	/**
	 * @see ModelFactoryWindows#getAppMovies()
	 */
	@Override
	public App getAppMovies(){
		if (appMovies == null){
			appMovies = (new ModelFactoryWindows()).getAppMovies();
			appMovies.setPath("smplayer");
			appMovies.setOs(OS.LINUX.toString());
		}
		return appMovies;
	}
	
	/**
	 * @see ModelFactoryWindows#getAppMusic()
	 */
	@Override
	public App getAppMusic(){
		if (appMusic == null){
			appMusic = new App("Audacious", "audacious");
			appMusic.setWindowName("Audacious");
			appMusic.addExtension("mp3");
			appMusic.addExtension("ogg");
			appMusic.addExtension("mpc");
			appMusic.addExtension("flac");
			appMusic.setArgumentsDir(Constants.APP_ARGUMENT_DIR);
			appMusic.setForceOneInstance(false);
			appMusic.setOs(OS.LINUX.toString());
			getControl(appMusic, 		
					ControlDefaultTypeApp.VOLUP, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_PLUS})), 1, 0);
			getControl(appMusic, 
					ControlDefaultTypeApp.PREV, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_UP})), 2, -2);
//			getControl(appMusic, 
//					ControlDefaultTypeApp.PAUSE, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_COMMA})), 2, -1);
			getControl(appMusic, 
					ControlDefaultTypeApp.PLAY, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_COMMA})), 2, 0);
			getControl(appMusic, 
					ControlDefaultTypeApp.NEXT, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_DOWN})), 2, 2);
			getControl(appMusic, 
					ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 3, -1);
			getControl(appMusic, 
					ControlDefaultTypeApp.VOLDOWN, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_MINUS})), 3, 0);
			getControl(appMusic, 
					ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 3, 1);
			
			ControlCategory controlCategory = new ControlCategory("Play Music in Winamp");
			ControlCategory controlCategory2 = new ControlCategory("Manage Winamp Window(s)");
			appMusic.addControlCategory(controlCategory);
			appMusic.addControlCategory(controlCategory2);
			for(Control control: appMusic.getControlsManager().getControls()){
				control.setControlCategory(controlCategory);
			}
			
		}
		
//		if (appMusic == null){
//			appMusic = new App("Rhythmbox", "rhythmbox");
//			appMusic.setWindowName("rhythmbox");
//			appMusic.addExtension("mp3");
//			appMusic.addExtension("ogg");
//			appMusic.addExtension("mpc");
//			appMusic.addExtension("flac");
//			appMusic.setForceOneInstance(false);
//			appMusic.setOs(OS.LINUX.toString());
//			getControl(appMusic, 		
//					ControlDefaultTypeApp.VOLUP, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_UP})), 1, 0);
//			getControl(appMusic, 
//					ControlDefaultTypeApp.PREV, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_LEFT})), 2, -2);
//			Set<Integer> l = new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE}));
//			getControl(appMusic, ControlDefaultTypeApp.PLAY, 1, l, 2, -1);
//			getControl(appMusic, ControlDefaultTypeApp.PAUSE, 1, l, 2, -1);
//			getControl(appMusic, 
//					ControlDefaultTypeApp.NEXT, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_RIGHT})), 2, 2);
//			//getControl(appMusic, ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 3, -1));
//			getControl(appMusic, 
//					ControlDefaultTypeApp.VOLDOWN, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_DOWN})), 3, 0);
//			//controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 3, 1));
//		}
		return appMusic;
	}
	
	/**
	 * @see ModelFactoryWindows#getAppPics()
	 */
	@Override
	public App getAppPics(){
		if (appPics == null){
			appPics = new App("Gnome Image Viewer", "eog");
			appPics.setWindowName("eog");
			appPics.addExtension("jpg");
			appPics.addExtension("gif");
			appPics.addExtension("png");
			appPics.setForceOneInstance(true);
			appPics.setOs(OS.LINUX.toString());
			getControl(appPics, ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_F11, 1, 0);
			getControl(appPics, ControlDefaultTypeApp.BACKWARD, KeyEvent.VK_LEFT, 2, -1);
			getControl(appPics, ControlDefaultTypeApp.PLAY, KeyEvent.VK_F5, 2, 0);
			getControl(appPics, ControlDefaultTypeApp.FORWARD, KeyEvent.VK_RIGHT, 2, 1);
		}
		return appPics;
	}
}
