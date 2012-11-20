package org.zooper.becuz.restmote.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.interfaces.BusinessAbstract;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.transport.ActiveApp;

public class ActiveAppBusiness extends BusinessAbstract{
	
	protected static final Logger log = Logger.getLogger(ActiveAppBusiness.class.getName());

	public ActiveAppBusiness() {}
	
	public ActiveApp focusActiveApp(String pid) throws Exception{
		return PcControllerFactory.getPcController().focusApp(pid);
	}
	
	public void killActiveApps(List<String> handles) throws Exception{
		PcControllerFactory.getPcController().killApps(handles);
	}
	
	/**
	 * @param refresh
	 * @return running applications on the pc.
	 */
	public List<ActiveApp> getActiveApps(boolean refresh) {
		if (refresh || PcControllerFactory.getPcController().getActiveApps().isEmpty()){
			PcControllerFactory.getPcController().rebuildActiveApps();
		}
		return PcControllerFactory.getPcController().getActiveApps();
	}
	
	/**
	 * Put the user input focus on the next running application
	 * @return the ActiveApp that has focus
	 * @throws Exception 
	 */
	public ActiveApp next() throws Exception{
		ActiveApp previousFocusedActiveApp = getActiveAppFocused(true);
		List<ActiveApp> activeApps = PcControllerFactory.getPcController().getActiveApps();
		ActiveApp nextActiveApp = activeApps.get((activeApps.indexOf(previousFocusedActiveApp)+1) % activeApps.size()); 
		PcControllerFactory.getPcController().focusApp(nextActiveApp.getPid());
		if (previousFocusedActiveApp != null){
			previousFocusedActiveApp.setFocus(false);
		}
		nextActiveApp.setFocus(true);
		return nextActiveApp;
	}
	
	/**
	 * Return the list of pids of running processes of the argument app (name match)
	 * @param app
	 * @param refresh re-retrieve running apps
	 * @return
	 */
	public List<String> getActivePidsOfApp(App app, boolean refresh){
		List<String> result = new ArrayList<String>();
		for(ActiveApp activeApp: getActiveApps(refresh)){
			if (activeApp.getName().equals(app.getName())){
				result.add(activeApp.getPid());
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param pid
	 * @param refresh
	 * @return
	 */
	public ActiveApp getActiveAppByPid(String pid, boolean refresh){
		for(ActiveApp activeApp: getActiveApps(refresh)){
			if (activeApp.getPid().equals(pid)){
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
		log.warn("No activeApp found with focus");
		return null;
	}
	
	
	
}
