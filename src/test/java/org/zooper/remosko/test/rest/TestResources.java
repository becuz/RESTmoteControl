/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.remosko.conf.RestFactory;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.transport.ActiveApp;
import org.zooper.remosko.model.transport.Data;
import org.zooper.remosko.model.transport.Media;
import org.zooper.remosko.model.transport.MediaRoot;
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
