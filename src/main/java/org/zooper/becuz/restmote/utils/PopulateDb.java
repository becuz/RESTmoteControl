package org.zooper.becuz.restmote.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Environment;
import org.zooper.becuz.restmote.RestmoteControl;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.conf.ModelFactoryAbstract;
import org.zooper.becuz.restmote.conf.ModelFactoryFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;
import org.zooper.becuz.restmote.persistence.PersistenceHibernate;
import org.zooper.becuz.restmote.persistence.hibernate.HibernateUtil;

/**
 * Creates (or updates) the database and populate it with some data.
 * It creates the db if not exists the file (HSQL on db/) or updates it if !RestmoteControl.getVersion().equals(RestmoteControl.getDbVersion())
 * @author bebo
 *
 */
public class PopulateDb {
	
	private static final Logger log = Logger.getLogger(PopulateDb.class.getName());

	public void createAndPopulate(/*boolean develop*/) {
		boolean populate = false;
		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		if (persistenceAbstract instanceof PersistenceHibernate){
			Properties p = new Properties();
			String hbm2dll = null;
			String dbFile = HibernateUtil.getDbFile(false);
			if (!new File(dbFile+".properties").exists()){
				hbm2dll = "create";
				populate = true;
			} else if (/*develop ||*/ !RestmoteControl.getVersion().equals(RestmoteControl.getInstalledVersion())){
				hbm2dll = "update";
			}
			if (hbm2dll != null){
				log.info("A mainteneance operation is goin to perform on the database: " + hbm2dll);
				p.put(Environment.HBM2DDL_AUTO, hbm2dll); 	
				HibernateUtil.setProperties(p);
			}
		} else {
			populate = true;
		}
		
		Settings settings = new SettingsBusiness().get();
		persistenceAbstract.beginTransaction();
		if (settings == null){
			log.info("Settings instance not found, creating.. ");
			settings = new Settings();
			persistenceAbstract.store(settings);
		}
		
		if (populate){
			
			ModelFactoryAbstract modelFactoryAbstract = ModelFactoryFactory.getModelFactoryAbstract();
			
			App appMovies = modelFactoryAbstract.getAppMovies();
			App appMusic = modelFactoryAbstract.getAppMusic();
			App appPics = modelFactoryAbstract.getAppPics();
			
			MediaCategory mediaCategoryMovies = modelFactoryAbstract.getMediaCategoryMovies();
			MediaCategory mediaCategoryMusic = modelFactoryAbstract.getMediaCategoryMusic();
			MediaCategory mediaCategoryPics = modelFactoryAbstract.getMediaCategoryPics();
			MediaCategory mediaCategoryRoot = modelFactoryAbstract.getMediaCategoryRoot();
			
			Command command = modelFactoryAbstract.getACommand();
			
			//Flexible way to specify paths to scan for media. developers can list their own paths
			try {
				BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource("/paths").getPath()));
		        String line;
		        while((line = br.readLine()) != null) {
		        	if (!line.startsWith("#")){
			        	if (new File(line).exists()){
			        		settings.addPath(line);
			        	} else {
			        		log.warn("In file path, specified a non existing path: " + line);
			        	}
		        	}
		        }
		        br.close();
			} catch (Exception e){
				log.error(e.toString());
			}
			
			String userHome = Utils.getUserHome();
			if (new File(userHome).exists()){
				settings.addPath(userHome);
			}
			
			persistenceAbstract.store(appMovies);
			persistenceAbstract.store(appMusic);
			persistenceAbstract.store(appPics);
			persistenceAbstract.store(mediaCategoryMovies);
			persistenceAbstract.store(mediaCategoryMusic);
			persistenceAbstract.store(mediaCategoryPics);
			persistenceAbstract.store(mediaCategoryRoot);
			persistenceAbstract.store(command);
		}
		
		persistenceAbstract.commit();
	}
	
}
