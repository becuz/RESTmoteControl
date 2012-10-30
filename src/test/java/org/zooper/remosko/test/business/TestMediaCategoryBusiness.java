/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestMediaCategoryBusiness extends TestAbstract{

	private MediaCategoryBusiness mediaCategoryBusiness = new MediaCategoryBusiness();
	
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

}
