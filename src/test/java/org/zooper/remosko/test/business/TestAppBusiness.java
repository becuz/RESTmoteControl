/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;

import org.junit.Test;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.conf.ModelFactoryAbstract;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.utils.Utils;
import org.zooper.becuz.restmote.utils.Utils.OS;
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
		App app = new App("name", "p");
		appBusiness.store(app);
		assertTrue(appBusiness.getAll().size() == sizeApps+1);
		appBusiness.delete(app);
		assertTrue(appBusiness.getAll().size() == sizeApps);
		app = new App("name", "p");
		appBusiness.save(app);
		assertTrue(appBusiness.getAll().size() == sizeApps+1);
		appBusiness.delete(app);
		assertTrue(appBusiness.getAll().size() == sizeApps);
		App appM = appBusiness.getByName(appMovies.getName());
		assertTrue(appM.getName().equals(appMovies.getName()));
		assertTrue(appM.getArgumentsFile().equals(appMovies.getArgumentsFile()));
		assertTrue(appM.getExtensions().equals(appMovies.getExtensions()));
	}
	
	@Test
	public void testGetAppByFilter() {
		assertTrue(
				appBusiness.getByFilters(appMusic.getName(), null, null, null, null).get(0)
				.equals(appMusic));
		assertTrue(
				appBusiness.getByFilters(null, appMusic.getWindowName(), null, null, null).get(0)
				.equals(appMusic));
		assertTrue(
				appBusiness.getByFilters(null, null, "mp3", null, null).get(0)
				.equals(appMusic));
		assertTrue(
				appBusiness.getByFilters(null, appMusic.getWindowName(), null, Utils.getOs(), null).get(0)
				.equals(appMusic));
		assertTrue(
				appBusiness.getByFilters(null, null, "mp3", null, null).get(0)
				.equals(appMusic));
		assertTrue(
				appBusiness.getByFilters(appMusic.getName(), appMusic.getWindowName(), appMusic.getExtensions().iterator().next(), null, null).get(0)
				.equals(appMusic));
		assertTrue(appBusiness.getByFilters("not"+appMusic.getName(), null, null, null, null).isEmpty());
		assertTrue(appBusiness.getByFilters(null, "not"+appMusic.getWindowName(), null, null, null).isEmpty());
		
	}
	
	@Test
	public void testCascade() {
		App app = new App("smplayer", "path");
		app.addExtension("avi");
		app.setForceOneInstance(true);
		ControlsManager controlApp = app.getControlsManager();
		Control control1 = 
				(Control) ModelFactoryAbstract.getControl(app, ControlDefaultTypeApp.VOLUP, KeyEvent.VK_0, 1, 0)[0];
		Control control2 = 
				(Control) ModelFactoryAbstract.getControl(app, ControlDefaultTypeApp.FULLSCREEN, KeyEvent.VK_F, 1, -2)[0];
		controlApp.addControl(control1);
		controlApp.addControl(control2);
		appBusiness.store(app);
		control1.setName("name");
		appBusiness.store(app);
		app = appBusiness.get(app.getId());
		controlApp = app.getControlsManager();
		assertNotNull(controlApp.getControl("name"));
		assertNull(controlApp.getControl(ControlDefaultTypeApp.VOLUP.toString()));
		assertEquals(controlApp.getControls().size(), 2);
		controlApp.removeControl(control2);
		appBusiness.store(app);
		assertEquals(controlApp.getControls().size(), 1);
		appBusiness.delete(app);
	}

}
