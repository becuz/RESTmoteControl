package org.zooper.remosko;

import java.io.IOException;

import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.persistence.PersistenceAbstract;
import org.zooper.remosko.persistence.PersistenceFactory;
import org.zooper.remosko.server.Server;
import org.zooper.remosko.utils.PopulateDb;

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