package org.zooper.remosko.business;

import java.util.Collection;

import org.zooper.remosko.business.interfaces.BusinessModelAbstract;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;

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
