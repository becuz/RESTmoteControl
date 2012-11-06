package org.zooper.becuz.restmote.persistence;

import org.apache.log4j.Logger;

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
