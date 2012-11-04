package org.zooper.becuz.restmote.rest.resources;

import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.business.RemoteControlBusiness;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.controller.PcControllerFactory;

public class AbstractResource {
	
	protected RemoteControlBusiness getRemoteControlBusiness() {
		return PcControllerFactory.getPcController().getRemoteControlBusiness();
	}
	
	protected MediaCategoryBusiness getMediaCategoryBusiness() {
		return PcControllerFactory.getPcController().getMediaCategoryBusiness();
	}
	
	protected AppBusiness getAppBusiness() {
		return PcControllerFactory.getPcController().getAppBusiness();
	}

	protected SettingsBusiness getSettingsBusiness() {
		return PcControllerFactory.getPcController().getSettingsBusiness();
	}

	protected MediaBusiness getMediaBusiness() {
		return PcControllerFactory.getPcController().getMediaBusiness();
	}
	
	protected ActiveAppBusiness getActiveAppBusiness() {
		return PcControllerFactory.getPcController().getActiveAppBusiness();
	}
	
}
