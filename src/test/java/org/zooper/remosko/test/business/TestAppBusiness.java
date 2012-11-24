/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Test;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeApp;
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
	
	@Test
	public void testCascade() {
		App app = new App("smplayer", "path");
		app.addExtension("avi");
		app.setForceOneInstance(true);
		ControlsManager controlAppMovies = new ControlsManager();
		Control control1 = controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.VOLUP.toString(), KeyEvent.VK_0, 1, 0));
		Control control2 = controlAppMovies.addControl(Control.getControl(ControlDefaultTypeApp.FULLSCREEN.toString(), KeyEvent.VK_F, 2, -2));
		app.setControlsManager(controlAppMovies);
		appBusiness.store(app);
		control1.setName("name");
		appBusiness.store(app);
		app = appBusiness.get(app.getId());
		assertNotNull(app.getControlsManager().getControl("name"));
		assertNull(app.getControlsManager().getControl(ControlDefaultTypeApp.VOLUP.toString()));
		assertEquals(app.getControlsManager().getControls().size(), 2);
		app.getControlsManager().removeControl(control2);
		appBusiness.store(app);
		assertEquals(app.getControlsManager().getControls().size(), 1);
	}

}
