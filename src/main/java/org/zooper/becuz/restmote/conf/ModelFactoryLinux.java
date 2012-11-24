package org.zooper.becuz.restmote.conf;

import org.zooper.becuz.restmote.exceptions.NotYetImplementedException;
import org.zooper.becuz.restmote.model.App;

public class ModelFactoryLinux extends ModelFactoryAbstract{

	/**
	 * @see ModelFactoryWindows#getAppMusicPauseChar()
	 */
	@Override
	public Character getAppMusicPauseChar(){
		throw new NotYetImplementedException();
	}
	
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
		throw new NotYetImplementedException();
	}
	
	/**
	 * @see ModelFactoryWindows#getAppPics()
	 */
	@Override
	public App getAppPics(){
		throw new NotYetImplementedException();
	}
}
