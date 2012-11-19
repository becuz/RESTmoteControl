package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.ControlsManager;

public class ModelFactoryWindows extends ModelFactoryAbstract{

	@Override
	public Character getAppMusicPauseChar(){
		return 'c';
	}
	
	@Override
	public App getAppMovies(){
		if (appMovies == null){
			appMovies = new App("smplayer", "C:\\Program Files (x86)\\SMPlayer\\smplayer.exe", "%f");
			appMovies.addExtension("avi");
			appMovies.setForceOneInstance(true);
			ControlsManager controlAppMovies = new ControlsManager();
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLUP.toString(), 3, KeyEvent.VK_0, 1, 0));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN.toString(), KeyEvent.VK_F, 2, -2));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD.toString(), 3, KeyEvent.VK_LEFT, 2, -1));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.PLAY.toString(), KeyEvent.VK_SPACE, 2, 0));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD.toString(), 3, KeyEvent.VK_RIGHT, 2, 1));
			Set<Integer> list = new HashSet<Integer>();
			list.addAll(Arrays.asList(new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_S}));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.MENU.toString(), 1, list, 2, 2));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLMUTE.toString(), 1, KeyEvent.VK_M, 3, -1));
			controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLDOWN.toString(), 3, KeyEvent.VK_9, 3, 0));
			appMovies.setControlsManager(controlAppMovies);
		}
		return appMovies;
	}
	
	@Override
	public App getAppMusic(){
		if (appMusic == null){
			appMusic = new App("winamp", "C:\\Program Files (x86)\\Winamp\\winamp.exe", "%f", "%f");
			appMusic.addExtension("mp3");
			appMusic.addExtension("ogg");
			appMusic.setForceOneInstance(false);
			ControlsManager controlAppMusic = new ControlsManager();
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.VOLUP.toString(), 5, KeyEvent.VK_UP, 1, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PREV.toString(), KeyEvent.VK_Z, 2, -2));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PLAY.toString(), KeyEvent.VK_X, 2, -1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.PAUSE.toString(), KeyEvent.VK_C, 2, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.STOP.toString(), KeyEvent.VK_V, 2, 1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.NEXT.toString(), KeyEvent.VK_B, 2, 2));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD.toString(), 3, KeyEvent.VK_LEFT, 3, -1));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.VOLDOWN.toString(), 5, KeyEvent.VK_DOWN, 3, 0));
			controlAppMusic.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD.toString(), 3, KeyEvent.VK_RIGHT, 3, 1));
			appMusic.setControlsManager(controlAppMusic);
		}
		return appMusic;
	}
	
	@Override
	public App getAppPics(){
		if (appPics == null){
			appPics = new App("i_view32", "C:\\Program Files (x86)\\IrfanView\\i_view32.exe", "%f");
			appPics.addExtension("jpg");
			appPics.setForceOneInstance(true);
			ControlsManager controlAppPics = new ControlsManager();
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN.toString(), 1, KeyEvent.VK_ENTER, 1, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.BACKWARD.toString(), KeyEvent.VK_LEFT, 2, -1));
			Set<Integer> list = new HashSet<Integer>();
			list.addAll(Arrays.asList(new Integer[]{KeyEvent.VK_SHIFT, KeyEvent.VK_A}));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.PLAY.toString(), 1, list, 2, 0));
			controlAppPics.addControl(Control.getControl(ControlDefaultTypeApp.FORWARD.toString(), KeyEvent.VK_RIGHT, 2, 1));
			appPics.setControlsManager(controlAppPics);
		}
		return appPics;
	}
}
