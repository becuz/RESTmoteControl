package org.zooper.becuz.restmote.business.interfaces;

import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;

public class BusinessAbstract {
	
	protected PersistenceAbstract persistenceAbstract;
	
	protected BusinessAbstract() { 
		persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
	}
	
	protected AppBusiness getAppBusiness() {
		return PcControllerFactory.getPcController().getAppBusiness();
	}

	protected ActiveAppBusiness getActiveAppBusiness() {
		return PcControllerFactory.getPcController().getActiveAppBusiness();
	}
	
}
