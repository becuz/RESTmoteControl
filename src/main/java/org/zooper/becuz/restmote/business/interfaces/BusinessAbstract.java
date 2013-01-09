package org.zooper.becuz.restmote.business.interfaces;

import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;

public class BusinessAbstract {
	
	protected PersistenceAbstract persistenceAbstract;
	
	protected BusinessAbstract() { 
		persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
	}
	
}
