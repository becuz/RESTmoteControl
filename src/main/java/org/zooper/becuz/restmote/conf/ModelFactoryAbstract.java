package org.zooper.becuz.restmote.conf;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeKeyboard;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.PopulateDb;

/**
 * Return instances of model objects used in tests and by {@link PopulateDb} 
 * @author bebo
 *
 */
public abstract class ModelFactoryAbstract {

	protected MediaCategory mediaCategoryMovies;
	protected MediaCategory mediaCategoryMusic;
	protected MediaCategory mediaCategoryPics;
	protected MediaCategory mediaCategoryRoot;
	protected App appMovies;
	protected App appMusic;
	protected App appPics;
	
	public abstract App getAppMovies();
	public abstract App getAppMusic();
	public abstract App getAppPics();
	
	public abstract Command getACommand();
	
	public MediaCategory getMediaCategoryMovies(){
		if (mediaCategoryMovies == null){
			mediaCategoryMovies = new MediaCategory("Movies");
			mediaCategoryMovies.setApp(getAppMovies());
			mediaCategoryMovies.addExtension("avi");
			mediaCategoryMovies.addExtension("mkv");
			mediaCategoryMovies.addExtension("mpg");
			mediaCategoryMovies.addExtension("mp4");
		}
		return mediaCategoryMovies;
	}

	public MediaCategory getMediaCategoryMusic(){
		if (mediaCategoryMusic == null){
			mediaCategoryMusic = new MediaCategory("Music");
			mediaCategoryMusic.addExtension("mp3");
			mediaCategoryMusic.addExtension("flac");
			mediaCategoryMusic.addExtension("mpc");
			mediaCategoryMusic.addExtension("ogg");
			mediaCategoryMusic.setApp(getAppMusic());
		}
		return mediaCategoryMusic;
	}

	public MediaCategory getMediaCategoryPics(){
		if (mediaCategoryPics == null){
			mediaCategoryPics = new MediaCategory("Pics");
			mediaCategoryPics.addExtension("jpeg");
			mediaCategoryPics.addExtension("jpg");
			mediaCategoryPics.addExtension("gif");
			mediaCategoryPics.addExtension("png");
			mediaCategoryPics.setApp(getAppPics());
		}
		return mediaCategoryPics;
	}
	
	public MediaCategory getMediaCategoryRoot(){
		if (mediaCategoryRoot == null){
			mediaCategoryRoot = new MediaCategory(Constants.MEDIA_ROOT);
			mediaCategoryRoot.setDescription("Allows to browse the entire filesystem.");
		}
		return mediaCategoryRoot;
	}
	
	public static ControlInterface[] getControlKeyboard(ControlDefaultTypeKeyboard c, Integer key, Integer row, Integer position){
		return getControlKeyboard(c.toString().toLowerCase(), key, row, position);
	}
	
	public static ControlInterface[] getControlKeyboard(String name, Integer key, Integer row, Integer position){
		return getControl(null, name, 1, key, row, position);
	}
	
	public static ControlInterface[] getControlKeyboard(String name, HashSet<Integer> keys, Integer row, Integer position){
		return getControl(null, name, 1, keys, row, position);
	}
	
	public static ControlInterface[] getControl(App app, ControlDefaultTypeApp c, Integer key, Integer row, Integer position){
		return getControl(app, c, 1, key, row, position);
	}
	
	public static ControlInterface[] getControl(App app, ControlDefaultTypeApp c, Integer repeat, Integer key, Integer row, Integer position){
		return getControl(app, c.toString().toLowerCase(), repeat, key, row, position);
	}
	
	public static ControlInterface[] getControl(App app, String name, Integer repeat, Integer key, Integer row, Integer position){
		Set<Integer> keysInner = new HashSet<Integer>();
		if (key != null){
			keysInner.add(key);
		}
		return getControl(app, name, repeat, keysInner, row, position);
	}
	
	public static ControlInterface[] getControl(App app, ControlDefaultTypeApp c, Integer repeat, Set<Integer> keys, Integer row, Integer position){
		return getControl(app, c.toString().toLowerCase(), repeat, keys, row, position);
	}
	
	public static ControlInterface[] getControl(App app, String name, Integer repeat, Set<Integer> keys, Integer row, Integer position){
		List<Set<Integer>> sequenceKeys = new ArrayList<Set<Integer>>();
		sequenceKeys.add(keys);
		return getControlMultiCommand(app, name, Collections.singletonList(repeat), sequenceKeys, row, position);
	}
	
	public static ControlInterface[] getControlMultiCommand(App app, String name, List<Integer> repeat, List<Set<Integer>> keys, Integer row, Integer position){
		Control c = new Control(name.toLowerCase());
		int i = 0;
		for(Set<Integer> k: keys){
			KeysEvent keysEvent = new KeysEvent(k, repeat.get(i++));
			c.addKeysEvent(keysEvent);
		}
		c.validate();
		VisualControl vc = new VisualControl(c, row, position);
		vc.validate();
		if (app != null){
			app.getControlsManager().addControl(c);
			app.getVisualControlsManager().addControl(vc);
		}
		return new ControlInterface[]{c, vc};
	}
	
}
