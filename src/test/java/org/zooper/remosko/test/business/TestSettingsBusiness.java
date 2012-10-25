/**
 * 
 */
package org.zooper.remosko.test.business;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.zooper.remosko.business.SettingsBusiness;
import org.zooper.remosko.model.Control;
import org.zooper.remosko.model.ControlsManager;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.test.TestAbstract;
/**
 * @author bebo
 *
 */
public class TestSettingsBusiness extends TestAbstract{

	private SettingsBusiness settingsBusiness = new SettingsBusiness();
	
	@Test
	public void testGet() {
		Settings settings = settingsBusiness.get();
		assertNotNull(settings.getName());
		assertNotNull(settings.getNameRoot());
		assertNotNull(settings.getPaths());
		assertTrue(settings.getPaths().contains(PATH_RESOURCES));
	}

}
