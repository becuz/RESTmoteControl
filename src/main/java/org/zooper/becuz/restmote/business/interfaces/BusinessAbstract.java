package org.zooper.becuz.restmote.business.interfaces;

import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;

public class BusinessAbstract {
	
	private AppBusiness appBusiness;
//	private MediaBusiness mediaBusiness;
//	private MediaCategoryBusiness mediaCategoryBusiness;
	private ActiveAppBusiness activeAppBusiness;
	
	protected PersistenceAbstract persistenceAbstract;
	
	protected BusinessAbstract() { 
		persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
	}
	
//	protected MediaCategoryBusiness getMediaCategoryBusiness() {
//		if (mediaCategoryBusiness == null){
//			mediaCategoryBusiness = new MediaCategoryBusiness();
//		}
//		return mediaCategoryBusiness;
//	}
	
	protected AppBusiness getAppBusiness() {
		if (appBusiness == null){
			appBusiness = new AppBusiness();
		}
		return appBusiness;
	}

//	protected MediaBusiness getMediaBusiness() {
//		if (mediaBusiness == null){
//			mediaBusiness = new MediaBusiness();
//		}
//		return mediaBusiness;
//	}

	protected ActiveAppBusiness getActiveAppBusiness() {
		if (activeAppBusiness == null){
			activeAppBusiness = new ActiveAppBusiness();
		}
		return activeAppBusiness;
	}
	
}
