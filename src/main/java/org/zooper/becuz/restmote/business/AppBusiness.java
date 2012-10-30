package org.zooper.becuz.restmote.business;

import org.zooper.becuz.restmote.business.interfaces.BusinessModelAbstract;
import org.zooper.becuz.restmote.model.App;

public class AppBusiness extends BusinessModelAbstract<App>{

	public AppBusiness() {
		super(App.class);
	}
	
	/**
	 * @param extension
	 * @return
	 */
	public App getByExtension(String extension) {
		for (App app: getAll()){
			if (app.getExtensions().contains(extension)){
				return app;
			}
		}
		return null;
	}
	
}
