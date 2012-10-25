package org.zooper.remosko.business.interfaces;

import org.zooper.remosko.business.ActiveAppBusiness;
import org.zooper.remosko.business.AppBusiness;
import org.zooper.remosko.business.MediaCategoryBusiness;
import org.zooper.remosko.business.MediaBusiness;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.transport.Media;
import org.zooper.remosko.persistence.PersistenceAbstract;
import org.zooper.remosko.persistence.PersistenceFactory;

public class BusinessAbstract {
	
	private AppBusiness appBusiness;
	private MediaBusiness mediaBusiness;
	private MediaCategoryBusiness mediaCategoryBusiness;
	private ActiveAppBusiness activeAppBusiness;
	protected PersistenceAbstract persistenceAbstract;
	
	protected BusinessAbstract() { 
		persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
	}
	
	protected MediaCategoryBusiness getMediaCategoryBusiness() {
		if (mediaCategoryBusiness == null){
			mediaCategoryBusiness = new MediaCategoryBusiness();
		}
		return mediaCategoryBusiness;
	}
	
	protected AppBusiness getAppBusiness() {
		if (appBusiness == null){
			appBusiness = new AppBusiness();
		}
		return appBusiness;
	}

	protected MediaBusiness getMediaBusiness() {
		if (mediaBusiness == null){
			mediaBusiness = new MediaBusiness();
		}
		return mediaBusiness;
	}

	protected ActiveAppBusiness getActiveAppBusiness() {
		if (activeAppBusiness == null){
			activeAppBusiness = new ActiveAppBusiness();
		}
		return activeAppBusiness;
	}
	
}
