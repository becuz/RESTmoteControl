package org.zooper.becuz.restmote.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.utils.Utils;

public class PcControllerWindows extends PcControllerAbstract{

	protected PcControllerWindows() {
		super();
		binDefaultPaths = Arrays.asList(new String[]{"%ProgramFiles%", "%CommonProgramFiles%"});
	}
	
	@Override
	public String getCommandOpenFile() {
		return "explorer";
	}
	
	@Override
	public String getCommandFocusApp(String handle) {
		return ".\\lib\\win\\cmdow.exe " + handle + " /ACT";
	}
	
	@Override
	public String getCommandKillApp(String handle) {
		return ".\\lib\\win\\cmdow.exe " + handle + " /CLS";
	}
	
	@Override
	public String getCommandListApps() {
		return ".\\lib\\win\\cmdow.exe /T";
	}
	
	/**
	 * example of cmdow output:
	 * Handle  Lev Pid -Window status- Image    Caption
	 * 0x090E36 1 8448 Res Act Ena Vis cmd      Amministratore: C:\Windows\system32\cm 
	 */
	@Override
	public List<ActiveApp> getRunningApps(BufferedReader brInput, BufferedReader brError) throws IOException{
		List<ActiveApp> activeApps = new ArrayList<ActiveApp>();
		String line = brInput.readLine();
		while ((line = brInput.readLine()) != null) {
			String[] infos = line.split("\\s+");
			String handle = infos[0];
			boolean hasFocus = "Act".equals(infos[4]); 
			String name = infos[7];
			String windowLbl = infos[8];
			for (int i = 9; i < infos.length; i++) {
				windowLbl += (" " + infos[i]);
			}
			ActiveApp activeApp = new ActiveApp(handle, name, windowLbl, hasFocus);
			log.debug(activeApp.toString());
			activeApps.add(activeApp);
	      }
		while ((line = brError.readLine()) != null) {	//errors
			log.error("Error: " + line);
		}
		return activeApps;
	}
	
	@Override
	public boolean poweroff() throws Exception {
		//execute(new String[]{"Shutdown.exe", "-s", "-t", "00"});
		return execute(new String[]{".\\lib\\win\\nircmd.exe", "exitwin", "poweroff"}) != null;
	}

	@Override
	public boolean suspend() throws Exception {
		//execute(new String[]{".\\lib\\win\\nircmd.exe", "standby"
		return execute(new String[]{"Rundll32", "powrprof.dll,SetSuspendState" ,"Sleep"}) !=null; //powercfg -hibernate off
	}

	@Override
	public boolean mute() throws Exception {
		return execute(new String[]{".\\lib\\win\\nircmd.exe", "mutesysvolume", "2"}) != null; 
	}
	
	@Override
	public boolean setVolume(int vol) throws Exception {
		return execute(new String[]{".\\lib\\win\\nircmd.exe", "setsysvolume", "" + Utils.map(vol, 0, 100, 0, 65535)}) != null; 
	}
	
}
