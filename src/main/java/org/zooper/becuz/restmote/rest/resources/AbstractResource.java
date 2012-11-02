package org.zooper.becuz.restmote.rest.resources;

import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.business.RemoteControlBusiness;
import org.zooper.becuz.restmote.business.SettingsBusiness;

public class AbstractResource {

	private RemoteControlBusiness remoteControlBusiness;
	private MediaCategoryBusiness mediaCategoryBusiness;
	private AppBusiness appBusiness;
	private SettingsBusiness settingsBusiness;
	private MediaBusiness mediaBusiness;
	private ActiveAppBusiness activeAppBusiness;
	
	public AbstractResource() {}
	
	protected RemoteControlBusiness getRemoteControlBusiness() {
		if (remoteControlBusiness == null){
			remoteControlBusiness = new RemoteControlBusiness();
		}
		return remoteControlBusiness;
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

	protected SettingsBusiness getSettingsBusiness() {
		if (settingsBusiness == null){
			settingsBusiness = new SettingsBusiness();
		}
		return settingsBusiness;
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