package org.zooper.becuz.restmote.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.zooper.becuz.restmote.model.transport.ActiveApp;

/**
 * TODO implement a method verify() that check the presence , and notify the user in case of absence, of the tools needed as wmctrl
 * @author bebo
 */
public class PcControllerLinux extends PcControllerAbstract{
	
	@Override
	public String getCommandOpenFile() {
		return "gnome-open";	//TODO switch between windows managers. available: gnome-open, kde-open, exo-open. Generic: xdg-utils
	}
	
	@Override
	public String getCommandFocusApp(String handle) {
		return "wmctrl -i -a " + handle;
	}
	
	@Override
	public String getCommandKillApp(String handle) {
		return "wmctrl -i -c " + handle;
	}
	
	@Override
	public String getCommandListApps() {
		return "wmctrl -lpx";
	}
	
	/**
	 * 0x05000004  0 smplayer.Smplayer     kancha SMPlayer
	 */
	@Override
	public List<ActiveApp> getRunningApps(BufferedReader brInput, BufferedReader brError) throws IOException {
		List<ActiveApp> activeApps = new ArrayList<ActiveApp>();
		String activeHandle = getActiveHandle();
		String line = null;
		while ((line = brInput.readLine()) != null) {
			String[] infos = line.split("\\s+");
			String handle = infos[0];
			String[] windowClassSplitted = infos[3].split("\\.");
			String name = windowClassSplitted[windowClassSplitted.length-1];
			String windowLbl = infos[5];
			ActiveApp activeApp = new ActiveApp(handle, name, windowLbl, handle.equals(activeHandle));
			log.debug(activeApp.toString());
			activeApps.add(activeApp);
	      }
		while ((line = brError.readLine()) != null) {	//errors
			log.error("Error: " + line);
		}
		return activeApps;
	}

	/**
	 * TODO better way 
	 * @return the active window
	 * @throws IOException 
	 */
	private String getActiveHandle() throws IOException{
		Process process = null;
		BufferedReader brInput = null;
		try {
			process = execute("wmctrl -v -r :ACTIVE: -e dummynone".split(" "));
			brInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			log.debug("getActiveHandle line 1 : " + brInput.readLine());
			String line = brInput.readLine();	//second line is "Using window: 0x04000005"
			log.debug("getActiveHandle line 2 : " + line);
			final String pattern = "Using window: "; 
			if (line != null && line.startsWith(pattern)){
				return line.substring(pattern.length()).trim();
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if (brInput != null){
				brInput.close();
			}
			if (process != null){
				process.destroy();
			}
		}
		return null;
		
	}
	
	@Override
	public boolean mute() throws Exception {
		return execute("amixer --quiet set Master toggle".split(" ")) != null; 			//TODO bug in amixer (toggle works just the first time)
	}

	@Override
	public boolean setVolume(int vol) throws Exception {
		return execute(("amixer --quiet set Master " + vol + "%").split(" ")) != null; //TODO why Linux sound supports value than 100% ? 
	}

	@Override
	public boolean poweroff() throws Exception {
		return execute(new String[]{"shutdown"}) != null;
	}

	@Override
	public boolean suspend() throws Exception {
		return execute(new String[]{"pm-suspend"}) != null; 							//TODO only root?
	}


}
