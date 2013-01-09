package org.zooper.becuz.restmote.conf;

import org.zooper.becuz.restmote.exceptions.NotYetImplementedException;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;

public class ModelFactoryMac extends ModelFactoryAbstract{

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

	/**
	 * @see ModelFactoryWindows#getAShellCommand()
	 */
	@Override
	public Command getACommand() {
		throw new NotYetImplementedException();
	}
}
