package org.zooper.becuz.restmote.business;

import java.util.ArrayList;
import java.util.List;

import org.zooper.becuz.restmote.business.interfaces.BusinessModelAbstract;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.utils.Utils;
import org.zooper.becuz.restmote.utils.Utils.OS;

public class AppBusiness extends BusinessModelAbstract<App>{

	public AppBusiness() {
		super(App.class);
	}
	
	/**
	 * TODO test
	 * Return the app, if any, that is best candidate to receive the control by etension
	 * @param extension
	 * @return
	 */
	public App getRunningByExtension(String extension){
		MediaCategory mediaCategory = getMediaCategoryBusiness().getByExtension(extension);
		App app = null;
		if (mediaCategory != null){
			app = mediaCategory.getApp();
		}
		if (app == null){
			for (ActiveApp activeApp: getActiveAppBusiness().getActiveApps(false)){
				List<App> apps = getByFilters(null, activeApp.getName(), extension, Utils.getOs(), true);
				if (!apps.isEmpty()){
					app = apps.get(0);
					break;
				}
			}
		}
		return app;
	}
	
	/**
	 * @param nameFilter
	 * @param extensionFilter
	 * @param osFilter
	 * @param chosen
	 * @return
	 */
	public List<App> getByFilters(
			String nameFilter,
			String windowNameFilter,
			String extensionFilter,
			OS osFilter,
			Boolean chosen
			){
		List<App> results = new ArrayList<App>();
		List<App> apps = getAll();
		for (App app: apps){
			if (!Utils.isEmpty(nameFilter) && !app.getName().toLowerCase().contains(nameFilter.toLowerCase())){
				continue;
			}
			if (!Utils.isEmpty(windowNameFilter) && !app.getWindowName().toLowerCase().contains(windowNameFilter.toLowerCase())){
				continue;
			}
			if (!Utils.isEmpty(extensionFilter) && !app.getExtensions().contains(extensionFilter)){
				continue;
			}
			if (osFilter != null && app.getOs() != null && !app.getOs().equals(osFilter.toString())){
				continue;
			}
			if (Boolean.TRUE.equals(chosen) && Boolean.TRUE.equals(app.isChosen())){
				continue;
			}
			results.add(app);
		}
		return results;
	}
	
}
