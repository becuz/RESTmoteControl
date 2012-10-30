/**
 * 
 */
package org.zooper.remosko.test;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.cfg.Environment;
import org.junit.AfterClass;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;
import org.zooper.becuz.restmote.persistence.PersistenceHibernate;
import org.zooper.becuz.restmote.persistence.hibernate.HibernateUtil;
import org.zooper.becuz.restmote.utils.Utils;
import org.zooper.remosko.conf.modelfactory.ModelFactoryFactory;

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
		
		paths.add(PATH_RESOURCES);
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
	
	protected Media findMediaByExtensionInMediaRoots(List<MediaRoot> medias, String extension){
		for(MediaRoot mediaRoot: medias){
			Media m2 = findMediaByExtension(mediaRoot.getMediaChildren(), extension);
			if (m2 != null){
				return m2;
			}
		}
		return null;
	}
	
	protected Media findMediaByExtension(List<Media> medias, String extension){
		for(Media m: medias){
			if (m.isFile() && (Utils.isEmpty(extension) || m.getPath().endsWith("."+extension))){
				return m;
			} else {
				Media m2 = findMediaByExtension(m.getMediaChildren(), extension);
				if (m2 != null){
					return m2;
				}
			}
		}
		return null;
	}
	
	protected Media findMediaFile(List<Media> medias){
		return findMediaByExtension(medias, null);
	}

}
