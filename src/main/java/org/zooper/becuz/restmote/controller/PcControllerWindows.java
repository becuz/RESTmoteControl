package org.zooper.becuz.restmote.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.utils.Utils;

public class PcControllerWindows extends PcControllerAbstract{

	/**
	 * Active {@link Process}es linked to {@link MediaCategory}s
	 */
	protected Map<App, Process> appProcesses = new HashMap<App, Process>();
	
	protected PcControllerWindows() {
		super();
		binDefaultPaths = Arrays.asList(new String[]{"%ProgramFiles%", "%CommonProgramFiles%"});
	}
	
	@Override
	public boolean open(String filePath, App app) throws Exception {
		File f = new File(filePath);
		if (!f.exists()){
			throw new IllegalArgumentException("File " + filePath + " doesn't exist.");
		}
		Process process;
		if (app != null){
			String argumentMedia = f.isDirectory() ? app.getArgumentsDir() : app.getArgumentsFile(); 
		    String[] commands = new String[]{
		    		app.getPath(),
		    		argumentMedia.replace("%f", filePath)};
			process = execute(commands);
			appProcesses.put(app, process);
		} else {
			process = execute("explorer", filePath);
		}
		return process != null;
	}
	
	/**
	 * @throws Exception 
	 *  
	 */
	@Override
	public boolean close(App app) throws Exception {
		killApps(activeAppBusiness.getActivePidsOfApp(app, true));
		Process process = appProcesses.get(app); //Alternative way to close the app: return execute(new String[]{".\\lib\\win\\cmdow.exe", getHandleApp(mediaCategory.getApp(), "/CLS"}) != null;
		if (process != null){
			process.destroy();
		}
		rebuildActiveApps();
		return process != null;
	}
	
	@Override
	public boolean clean(){
		for(Process process: appProcesses.values()){
			process.destroy();
		}
		appProcesses.clear();
		return true;
	}
	
	@Override
	public ActiveApp focusApp(String pid) throws Exception {
		if (!Utils.isEmpty(pid)){
			if (execute(true, ".\\lib\\win\\cmdow.exe", pid, "/ACT") != null){ //TODO waitFor?
				return activeAppBusiness.getActiveAppByPid(pid, false);
			}
		}
		return null;
	}

	@Override
	public void killApps(List<String> handles) throws Exception {
		if (handles != null){
			for(String handle: handles){
				execute(new String[]{".\\lib\\win\\cmdow.exe", handle, "/CLS"});
				//this.handles.remove(handle);
			}
		}
	}
	
	@Override
	public boolean poweroff() throws Exception {
		//execute(new String[]{"Shutdown.exe", "-s", "-t", "00"});
		execute(new String[]{".\\lib\\win\\nircmd.exe", "exitwin", "poweroff"});
		return true;
	}

	@Override
	public boolean suspend() throws Exception {
		execute(new String[]{"Rundll32", "powrprof.dll,SetSuspendState" ,"Sleep"}); //powercfg -hibernate off
		//execute(new String[]{".\\lib\\win\\nircmd.exe", "standby"
		return true;
	}

	@Override
	public boolean mute() throws Exception {
		execute(new String[]{".\\lib\\win\\nircmd.exe", "mutesysvolume", "2"}); 
		return false;
	}
	
	@Override
	public boolean setVolume(int vol) throws Exception {
		int mappedVol = Utils.map(vol, 0, 100, 0, 65535);
		execute(new String[]{".\\lib\\win\\nircmd.exe", "setsysvolume", ""+mappedVol}); 
		return false;
	}
	
	/**
	 * example of cmdow output:
	 * Handle  Lev Pid -Window status- Image    Caption
	 * 0x090E36 1 8448 Res Act Ena Vis cmd      Amministratore: C:\Windows\system32\cm
	 */
	@Override
	public void rebuildActiveApps(){
		log.info("rebuildActiveApps");
		activeApps.clear();
		BufferedReader bri = null;
		BufferedReader bre;
		try {
			Process p = Runtime.getRuntime().exec(".\\lib\\win\\cmdow.exe /T");
			bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = bri.readLine();
			while ((line = bri.readLine()) != null) {
				String[] infos = line.split("\\s+");
				String handle = infos[0];
				boolean hasFocus = "Act".equals(infos[4]); 
				String image = infos[7];
				String windowName = infos[8];
				for (int i = 9; i < infos.length; i++) {
					windowName += (" " + infos[i]);
				}
				ActiveApp activeApp = new ActiveApp(handle, image, windowName, hasFocus);
				log.debug(activeApp.toString());
				activeApps.add(activeApp);
		      }
			while ((line = bre.readLine()) != null) {	//errors
				log.error("Error: " + line);
			}
			//p.waitFor();
			p.destroy();
			java.util.Collections.sort(activeApps);
		} catch (Exception e) {
			log.error(e.getMessage());
	    } finally {
	    	try {
	    		if (bri != null){
	    			bri.close();
	    		}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
//		 bre.close();
	    }
	}
	
	//*****************************************************************************************
	
	private Process execute(String... commands) throws Exception{
		return execute(false, commands);
	}
		
	private Process execute(boolean waitFor, String... commands) throws Exception{
		Process child = Runtime.getRuntime().exec(commands);
	    if (waitFor){
	    	child.waitFor();
	    }
	    return child;
	}

		
	
}
