package org.zooper.becuz.restmote.controller;

import java.util.List;

import org.zooper.becuz.restmote.exceptions.NotYetImplementedException;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.transport.ActiveApp;

public class PcControllerMac extends PcControllerAbstract{

	@Override
	public boolean open(String filePath, App app) throws Exception {
		throw new NotYetImplementedException();
		
	}

	@Override
	public boolean close(App app) throws Exception {
		throw new NotYetImplementedException();
		
	}

	@Override
	public boolean clean() {
		throw new NotYetImplementedException();
		
	}

	@Override
	public ActiveApp focusApp(String pid) throws Exception {
		throw new NotYetImplementedException();
	}

	@Override
	public void rebuildActiveApps() {
		throw new NotYetImplementedException();
	}

	@Override
	public void killApps(List<String> pids) throws Exception {
		throw new NotYetImplementedException();
		
	}

	@Override
	public boolean mute() throws Exception {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean setVolume(int vol) throws Exception {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean poweroff() throws Exception {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean suspend() throws Exception {
		throw new NotYetImplementedException();
	}

}
