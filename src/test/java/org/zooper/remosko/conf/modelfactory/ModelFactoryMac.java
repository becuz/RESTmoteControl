package org.zooper.remosko.conf.modelfactory;

import org.zooper.remosko.exceptions.NotYetImplementedException;
import org.zooper.remosko.model.App;

public class ModelFactoryMac extends ModelFactoryAbstract{

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
		throw new NotYetImplementedException();
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
