/**
 * 
 */
package org.zooper.remosko.test;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.cfg.Environment;
import org.junit.AfterClass;
import org.zooper.remosko.conf.modelfactory.ModelFactoryFactory;
import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.persistence.PersistenceAbstract;
import org.zooper.remosko.persistence.PersistenceFactory;
import org.zooper.remosko.persistence.PersistenceHibernate;
import org.zooper.remosko.persistence.hibernate.HibernateUtil;
import org.zooper.remosko.utils.Utils;

/**
 * @author bebo
 *
 */
public abstract class TestAbstract {

	protected static final Logger log = Logger.getLogger(TestAbstract.class.getName());
	
	protected static PersistenceAbstract persistenceAbstract;
	
	/**
	 * Contains avi, mp3, jpg files
	 */
	protected static final String PATH_RESOURCES = Utils.getRootDir() + "src/test/resources";
	
	protected static MediaCategory mediaCategoryMovies = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryMovies();
	protected static MediaCategory mediaCategoryMusic = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryMusic();
	protected static MediaCategory mediaCategoryPics = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryPics();
	protected static MediaCategory mediaCategoryRoot = ModelFactoryFactory.getModelFactoryAbstract().getMediaCategoryRoot();
	protected static App appMovies = mediaCategoryMovies.getApp();
	protected static App appMusic = mediaCategoryMusic.getApp();
	protected static App appPics = mediaCategoryPics.getApp();
	
	static {
		persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		
		if (persistenceAbstract instanceof PersistenceHibernate){
			//Ensures to recreate the database at each session of test running
			Properties p = new Properties();
			p.put(Environment.HBM2DDL_AUTO, "create"); 	
			HibernateUtil.setProperties(p);
			HibernateUtil.setTest(true);
		}
		
		assertTrue(persistenceAbstract.getAll(Settings.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(App.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(MediaCategory.class).isEmpty());
		
		Settings settings = new Settings();
		settings.setName("settingsName");
		settings.setNameRoot("settingsNameRoot");
		Set<String> paths = new HashSet<String>();
		
		String fileSeparator = System.getProperty("file.separator");
		paths.add(PATH_RESOURCES);
//		paths.add(System.getProperties().getProperty("user.dir") +  fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "resources");
		paths.add("path2");
		settings.setPaths(paths);
		
//			settings.setKeyboardControls(keyboardControls)
//			settings.setMouseControls(mouseControls);
		
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
		
		PcControllerFactory.getPcController().rootScan();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void clean() throws Exception {
		PcControllerFactory.getPcController().clean();
	}

}
