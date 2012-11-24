/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.remosko.test.TestAbstract;


/**
 * @author bebo
 *
 */
public class TestMediaCategoryBusiness extends TestAbstract{

	private MediaCategoryBusiness mediaCategoryBusiness = new MediaCategoryBusiness();
	private AppBusiness appBusiness = new AppBusiness();
	
	@Test
	public void testBasicOps() {
		int sizeApps = mediaCategoryBusiness.getAll().size();
		assertTrue(sizeApps > 0);
		MediaCategory mediaCategory = new MediaCategory("name");
		mediaCategoryBusiness.store(mediaCategory);
		assertTrue(mediaCategoryBusiness.getAll().size() == sizeApps+1);
		
		MediaCategory mediaCategory2 = mediaCategoryBusiness.getByName(".");
		assertNull(mediaCategory2);
		mediaCategory2 = mediaCategoryBusiness.getByName("name");
		assertNotNull(mediaCategory2);
		assertTrue(mediaCategory2.getName().equals(mediaCategory.getName()));
		
		mediaCategoryBusiness.delete(mediaCategory);
		assertTrue(mediaCategoryBusiness.getAll().size() == sizeApps);
		
		MediaCategory appM = mediaCategoryBusiness.getByName(mediaCategoryMovies.getName());
		assertTrue(appM.getName().equals(mediaCategoryMovies.getName()));
		assertTrue(appM.getExtensions().equals(mediaCategoryMovies.getExtensions()));
	}
	
	
	@Test
	public void testUpdate() {
		List<App> apps = appBusiness.getAll();
		
		MediaCategory mediaCategory = new MediaCategory("testUpdate");
		mediaCategory.setApp(apps.get(0));
		mediaCategory = mediaCategoryBusiness.store(mediaCategory);
		mediaCategory = mediaCategoryBusiness.get(mediaCategory.getId());
		mediaCategory.setApp(apps.get(1));
		mediaCategory = mediaCategoryBusiness.store(mediaCategory);
		assertEquals(mediaCategory.getApp(), apps.get(1));
		
	}
	

}
