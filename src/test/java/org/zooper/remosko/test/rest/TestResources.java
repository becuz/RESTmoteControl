/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Logger;

import org.junit.Test;
import org.zooper.remosko.utils.Utils;

/**
 * @author bebo
 */
public class TestResources extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestResources.class.getName());
	
	@Test
	public void testMisc() throws Exception {
		File f = new File(Utils.getRootDir() + "/db");
		assertTrue(f.exists());
	}
	
}
