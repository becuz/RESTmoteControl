package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.ControlCategory;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils.OS;

public class ModelFactoryWindows extends ModelFactoryAbstract{

	@Override
	public Command getACommand() {
		return new Command("google", "explorer http://www.google.com", "open google.com with the default browser");
	}
	
	@Override
	public App getAppMovies(){
		if (appMovies == null){
			appMovies = new App("Smplayer", "C:\\Program Files (x86)\\SMPlayer\\smplayer.exe");
			appMovies.setWindowName("smplayer");
			appMovies.addExtension("avi");
			appMovies.setForceOneInstance(true);
			appMovies.setOs(OS.WINDOWS.toString());
			getControl(appMovies, ControlDefaultTypeApp.VOLUP, 3, KeyEvent.VK_0, 1, 0);
			getControl(appMovies, ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_F, 2, -2);
			getControl(appMovies, ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 2, -1);
			getControl(appMovies, ControlDefaultTypeApp.PLAY, KeyEvent.VK_SPACE, 2, 0);
			getControl(appMovies, ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 2, 1);
			ControlInterface[] cs = getControl(appMovies, "menu", 1, 
					new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_S})), 2, 2);
			((VisualControl)cs[1]).setHideImg(true);
			getControl(appMovies, ControlDefaultTypeApp.VOLMUTE, 1, KeyEvent.VK_M, 3, -1);
			getControl(appMovies, ControlDefaultTypeApp.VOLDOWN, 3, KeyEvent.VK_9, 3, 0);
		}
		return appMovies;
	}
	
	@Override
	public App getAppMusic(){
		if (appMusic == null){
			appMusic = new App("Winamp", "C:\\Program Files (x86)\\Winamp\\winamp.exe");
			appMusic.setWindowName("winamp");
			appMusic.setArgumentsDir(Constants.APP_ARGUMENT_DIR);
			appMusic.addExtension("mp3");
			appMusic.addExtension("ogg");
			appMusic.setForceOneInstance(false);
			appMusic.setOs(OS.WINDOWS.toString());
			ControlCategory controlCategory = new ControlCategory("Play Music in Winamp");
			ControlCategory controlCategory2 = new ControlCategory("Manage Winamp Window(s)");
			appMusic.addControlCategory(controlCategory);
			appMusic.addControlCategory(controlCategory2);
			ControlInterface[] cs = getControl(appMusic, ControlDefaultTypeApp.VOLUP, 5, KeyEvent.VK_UP, 1, 0);
			((Control)cs[0]).setDescription("pump up the volume");
			getControl(appMusic, ControlDefaultTypeApp.PREV, KeyEvent.VK_Z, 2, -2);
			getControl(appMusic, ControlDefaultTypeApp.PLAY, KeyEvent.VK_X, 2, -1);
			getControl(appMusic, ControlDefaultTypeApp.PAUSE, KeyEvent.VK_C, 2, 0);
			getControl(appMusic, ControlDefaultTypeApp.STOP, KeyEvent.VK_V, 2, 1);
			getControl(appMusic, ControlDefaultTypeApp.NEXT, KeyEvent.VK_B, 2, 2);
			getControl(appMusic, ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 3, -1);
			getControl(appMusic, ControlDefaultTypeApp.VOLDOWN, 5, KeyEvent.VK_DOWN, 3, 0);
			getControl(appMusic, ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 3, 1);
			for(Control control: appMusic.getControlsManager().getControls()){
				control.setControlCategory(controlCategory);
			}
		}
		return appMusic;
	}
	
	@Override
	public App getAppPics(){
		if (appPics == null){
			appPics = new App("Irfan View", "C:\\Program Files (x86)\\IrfanView\\i_view32.exe");
			appPics.setWindowName("i_view32");
			appPics.addExtension("jpg");
			appPics.addExtension("gif");
			appPics.addExtension("png");
			appPics.setForceOneInstance(true);
			appPics.setOs(OS.WINDOWS.toString());
			getControl(appPics, ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_ENTER, 1, 0);
			getControl(appPics, ControlDefaultTypeApp.BACKWARD, KeyEvent.VK_LEFT, 2, -1);
			Set<Integer> list = new HashSet<Integer>();
			list.addAll(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A}));
			getControl(appPics, ControlDefaultTypeApp.PLAY, 1, list, 2, 0);
			getControl(appPics, ControlDefaultTypeApp.FORWARD, KeyEvent.VK_RIGHT, 2, 1);
		}
		return appPics;
	}
}
