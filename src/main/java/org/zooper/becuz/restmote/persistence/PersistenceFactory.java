package org.zooper.becuz.restmote.persistence;

import java.util.logging.Logger;

public class PersistenceFactory {

	private static PersistenceAbstract persistenceAbstract;
	
	protected static final Logger log = Logger.getLogger(PersistenceAbstract.class.getName());

	public static PersistenceAbstract getPersistenceAbstract(){
		if (persistenceAbstract == null){
//			persistenceAbstract = new PersistenceMemory();       //use memory
			persistenceAbstract = new PersistenceHibernate();	 //use a db through hibernate
		}
		return persistenceAbstract;
	}
	
}
