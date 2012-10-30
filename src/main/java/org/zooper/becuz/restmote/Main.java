package org.zooper.becuz.restmote;

import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;
import org.zooper.becuz.restmote.server.Server;
import org.zooper.becuz.restmote.utils.PopulateDb;

public class Main {

//	static {
//	    TODO using persistenceHibernate breaks the log!
//		System.setProperty("org.jboss.logging", "jdk");
//	}
	
	public static void main(String[] args) throws Exception {
		//recreates the db through ModelFactory, comment it out to persist db
		new PopulateDb().createAndPopulate();
		
		//to cache everything, let's do several getAll
		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		persistenceAbstract.getAll(Settings.class);
		persistenceAbstract.getAll(MediaCategory.class);
		persistenceAbstract.getAll(App.class);
		
		//scan for medias
		PcControllerFactory.getPcController().rootScan();
		
		//start http listener
		Server server = new Server();
		server.start();
	}
}