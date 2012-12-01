package org.zooper.becuz.restmote.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.interfaces.BusinessAbstract;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.transport.ActiveApp;

public class ActiveAppBusiness extends BusinessAbstract{
	
	protected static final Logger log = Logger.getLogger(ActiveAppBusiness.class.getName());

	/**
	 * 
	 */
	protected static List<ActiveApp> activeApps = new ArrayList<ActiveApp>();
	
	public ActiveAppBusiness() {}
	
	public void clearActiveApps() {
		activeApps.clear();
	}

	public void addActiveApps(Collection<ActiveApp> c) {
		activeApps.addAll(c);
		Collections.sort(activeApps);
	}
	
	public ActiveApp focusActiveApp(String hande) throws Exception{
		return PcControllerFactory.getPcController().focusApp(hande);
	}
	
	public void killActiveApps(List<String> handles) throws Exception{
		PcControllerFactory.getPcController().killApps(handles);
	}
	
	/**
	 * @param refresh
	 * @return running applications on the pc.
	 */
	public List<ActiveApp> getActiveApps(boolean refresh) {
		if (refresh || activeApps.isEmpty()){
			PcControllerFactory.getPcController().rebuildActiveApps();
		}
		return activeApps;
	}
	
	/**
	 * @param appName
	 * @param refresh
	 * @return running applications on the pc.
	 */
	public List<ActiveApp> getActiveAppsByAppName(String appName, boolean refresh) {
		List<ActiveApp> result = new ArrayList<>();
		getActiveApps(refresh);
		App app = getAppBusiness().getByName(appName);
		for(ActiveApp activeApp: activeApps){
			if (activeApp.isInstanceOf(app)){
				result.add(activeApp);
			}
		}
		return result;
	}
	
	
	/**
	 * Put the user input focus on the next running application
	 * @return the ActiveApp that has focus
	 * @throws Exception 
	 */
	public ActiveApp next() throws Exception{
		ActiveApp previousFocusedActiveApp = getActiveAppFocused(true);
		ActiveApp nextActiveApp = activeApps.get((activeApps.indexOf(previousFocusedActiveApp)+1) % activeApps.size()); 
		PcControllerFactory.getPcController().focusApp(nextActiveApp.getHandle());
		if (previousFocusedActiveApp != null){
			previousFocusedActiveApp.setFocus(false);
		}
		nextActiveApp.setFocus(true);
		return nextActiveApp;
	}
	
	/**
	 * Return the list of handes of running processes of the argument app (name match)
	 * @param app
	 * @param refresh re-retrieve running apps
	 * @return
	 */
	public List<String> getActiveAppHandlesOfApp(App app, boolean refresh){
		List<String> result = new ArrayList<String>();
		for(ActiveApp activeApp: getActiveApps(refresh)){
			if (activeApp.isInstanceOf(app)){
				result.add(activeApp.getHandle());
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param hande
	 * @param refresh
	 * @return
	 */
	public ActiveApp getActiveAppByHandle(String hande, boolean refresh){
		for(ActiveApp activeApp: getActiveApps(refresh)){
			if (activeApp.getHandle().equals(hande)){
				return activeApp;
			}
		}
		return null;
	}
	
	
	/**
	 * @return the app that has focus
	 */
	public ActiveApp getActiveAppFocused(boolean refresh){
		for(ActiveApp activeApp: getActiveApps(refresh)){
			if (activeApp.isFocus()){
				return activeApp;
			}
		}
		log.warn("No active activeApp found");
		return null;
	}
	
	
	
}
