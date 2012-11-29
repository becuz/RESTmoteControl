package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.ControlsManager;

public class ModelFactoryLinux extends ModelFactoryAbstract{

	
	/**
	 * @see ModelFactoryWindows#getAppMovies()
	 */
	@Override
	public App getAppMovies(){
		if (appMovies == null){
			appMovies = (new ModelFactoryWindows()).getAppMovies();
			appMovies.setPath("smplayer");
		}
		return appMovies;
	}
	
	/**
	 * @see ModelFactoryWindows#getAppMusic()
	 */
	@Override
	public App getAppMusic(){
		if (appMusic == null){
			appMusic = new App("Rhythmbox", "rhythmbox");
			appMusic.setWindowName("rhythmbox");
			appMusic.addExtension("mp3");
			//appMusic.addExtension("ogg");
			appMusic.setForceOneInstance(false);
			ControlsManager controlAppMusic = new ControlsManager();
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.VOLUP, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_UP})), 1, 0));
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.PREV, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_LEFT})), 2, -2));
			Set<Integer> l = new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE}));
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.PLAY, 1, l, 2, -1));
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.PAUSE, 1, l, 2, -1));
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.NEXT, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_RIGHT})), 2, 2));
			//controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD, 3, KeyEvent.VK_LEFT, 3, -1));
			controlAppMusic.addControl(Control.getControl(
					ControlDefaultTypeApp.VOLDOWN, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_DOWN})), 3, 0));
			//controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, 3, KeyEvent.VK_RIGHT, 3, 1));
			appMusic.setControlsManager(controlAppMusic);
		}
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
			ControlsManager controlAppPics = new ControlsManager();
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_F11, 1, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD, KeyEvent.VK_LEFT, 2, -1));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.PLAY, KeyEvent.VK_F5, 2, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD, KeyEvent.VK_RIGHT, 2, 1));
			appPics.setControlsManager(controlAppPics);
		}
		return appPics;
	}
}
