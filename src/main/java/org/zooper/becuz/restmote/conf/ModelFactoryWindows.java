package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.utils.Constants;

public class ModelFactoryWindows extends ModelFactoryAbstract{

	@Override
	public App getAppMovies(){
		if (appMovies == null){
			appMovies = new App("smplayer", "C:\\Program Files (x86)\\SMPlayer\\smplayer.exe");
			appMovies.addExtension("avi");
			appMovies.setForceOneInstance(true);
			ControlsManager controlAppMovies = new ControlsManager();
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLUP, 3, KeyEvent.VK_0, 1, 0));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_F, 2, -2));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 2, -1));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.PLAY, KeyEvent.VK_SPACE, 2, 0));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 2, 1));
			Control controlMenu = Control.getControl("menu", 1, 
					new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_S})), 2, 2);
			controlMenu.setHideImg(true);
			controlAppMovies.addControl(controlMenu);
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLMUTE, 1, KeyEvent.VK_M, 3, -1));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLDOWN, 3, KeyEvent.VK_9, 3, 0));
			appMovies.setControlsManager(controlAppMovies);
		}
		return appMovies;
	}
	
	@Override
	public App getAppMusic(){
		if (appMusic == null){
			appMusic = new App("winamp", "C:\\Program Files (x86)\\Winamp\\winamp.exe");
			appMusic.setArgumentsDir(Constants.APP_ARGUMENT_DIR);
			appMusic.addExtension("mp3");
			appMusic.addExtension("ogg");
			appMusic.setForceOneInstance(false);
			ControlsManager controlAppMusic = new ControlsManager();
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.VOLUP, 5, KeyEvent.VK_UP, 1, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PREV, KeyEvent.VK_Z, 2, -2));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PLAY, KeyEvent.VK_X, 2, -1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PAUSE, KeyEvent.VK_C, 2, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.STOP, KeyEvent.VK_V, 2, 1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.NEXT, KeyEvent.VK_B, 2, 2));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 3, -1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.VOLDOWN, 5, KeyEvent.VK_DOWN, 3, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 3, 1));
			appMusic.setControlsManager(controlAppMusic);
		}
		return appMusic;
	}
	
	@Override
	public App getAppPics(){
		if (appPics == null){
			appPics = new App("i_view32", "C:\\Program Files (x86)\\IrfanView\\i_view32.exe");
			appPics.addExtension("jpg");
			appPics.addExtension("gif");
			appPics.addExtension("png");
			appPics.setForceOneInstance(true);
			ControlsManager controlAppPics = new ControlsManager();
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_ENTER, 1, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD, KeyEvent.VK_LEFT, 2, -1));
			Set<Integer> list = new HashSet<Integer>();
			list.addAll(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A}));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.PLAY, 1, list, 2, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, KeyEvent.VK_RIGHT, 2, 1));
			appPics.setControlsManager(controlAppPics);
		}
		return appPics;
	}
}
