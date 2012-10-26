package org.zooper.remosko.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.cfg.Environment;
import org.zooper.remosko.conf.modelfactory.ModelFactoryFactory;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.persistence.PersistenceAbstract;
import org.zooper.remosko.persistence.PersistenceFactory;
import org.zooper.remosko.persistence.hibernate.HibernateUtil;

/**
 * Convenience class to recreate the db and populate with some data.
 * 
 * @author bebo
 *
 */
public class PopulateDb {
	
	private static final Logger log = Logger.getLogger(PopulateDb.class.getName());

	public void createAndPopulate() {
		Properties p = new Properties();
		p.put(Environment.HBM2DDL_AUTO, "create"); 	
		HibernateUtil.setProperties(p);
		
		App appMovies = ModelFactoryFactory.getModelFactoryAbstract().getAppMovies();
		App appMusic = ModelFactoryFactory.getModelFactoryAbstract().getAppMusic();
		App appPics = ModelFactoryFactory.getModelFactoryAbstract().getAppPics();
		
		MediaCategory mediaCategoryMovies = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryMovies();
		MediaCategory mediaCategoryMusic = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryMusic();
		MediaCategory mediaCategoryPics = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryPics();
		MediaCategory mediaCategoryRoot = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryRoot();
		
		Settings settings = new Settings();
		settings.setName("Home");
		settings.setNameRoot("My medias");
		settings.setScanDepth(1);
		
		//Temporary way to specify paths, waiting a proper admin user interface
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource("/paths").getPath()));
	        String line;
	        while((line = br.readLine()) != null) {
	        	if (!line.startsWith("#")){
		        	if (new File(line).exists()){
		        		settings.addPath(line);
		        	} else {
		        		log.warning("In file path, specified a non existing path: " + line);
		        	}
	        	}
	        }
	        br.close();
		} catch (Exception e){
//			e.printStackTrace();
			log.severe(e.toString());
		}
		String userHome = Utils.getUserHome();
		if (new File(userHome).exists()){
			settings.addPath(userHome);
		}
		
		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		persistenceAbstract.beginTransaction();
		persistenceAbstract.store(appMovies);
		persistenceAbstract.store(appMusic);
		persistenceAbstract.store(appPics);
		persistenceAbstract.store(mediaCategoryMovies);
		persistenceAbstract.store(mediaCategoryMusic);
		persistenceAbstract.store(mediaCategoryPics);
		persistenceAbstract.store(mediaCategoryRoot);
		persistenceAbstract.store(settings);
		persistenceAbstract.commit();
	}
	
}
