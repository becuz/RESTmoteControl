package org.zooper.becuz.restmote.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.zooper.becuz.restmote.exceptions.NotYetImplementedException;
import org.zooper.becuz.restmote.model.transport.ActiveApp;

public class PcControllerMac extends PcControllerAbstract{

	@Override
	public String getCommandOpenFile() {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean toggleMute() throws Exception {
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

	@Override
	public String getCommandFocusApp(String handle) {
		throw new NotYetImplementedException();
	}

	@Override
	public String getCommandKillApp(String handle) {
		throw new NotYetImplementedException();
	}

	@Override
	public String getCommandListApps() {
		throw new NotYetImplementedException();
	}

	@Override
	public List<ActiveApp> getRunningApps(BufferedReader brInput,
			BufferedReader brError) throws IOException {
		throw new NotYetImplementedException();
	}

}
