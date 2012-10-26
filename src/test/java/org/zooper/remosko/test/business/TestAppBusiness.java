/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zooper.remosko.business.AppBusiness;
import org.zooper.remosko.model.App;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestAppBusiness extends TestAbstract{

	private AppBusiness appBusiness = new AppBusiness();
	
	@Test
	public void testBasicOps() {
		int sizeApps = appBusiness.getAll().size();
		assertTrue(sizeApps > 0);
		App app = new App("name", "p", "f");
		appBusiness.store(app);
		assertTrue(appBusiness.getAll().size() == sizeApps+1);
		appBusiness.delete(app);
		assertTrue(appBusiness.getAll().size() == sizeApps);
		App appM = appBusiness.getByName(appMovies.getName());
		assertTrue(appM.getName().equals(appMovies.getName()));
		assertTrue(appM.getArgumentsFile().equals(appMovies.getArgumentsFile()));
		assertTrue(appM.getExtensions().equals(appMovies.getExtensions()));
	}
	
	@Test
	public void testGetAppByExtension() {
		String ext = appMusic.getExtensions().iterator().next();
		assertNotNull(ext);
		assertTrue(appBusiness.getByExtension(ext).getName().equals(appMusic.getName()));
	}

}
