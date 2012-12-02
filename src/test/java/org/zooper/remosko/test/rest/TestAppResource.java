/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.becuz.restmote.conf.rest.RestFactory;
import org.zooper.becuz.restmote.model.App;

/**
 * TODO
 * @author bebo
 */
public class TestAppResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestAppResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		//GET /apps
		result = invokeUrl(
				server.getApiUrl() + "apps/", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.info("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<App> apps = objectMapper.readValue(myString, new TypeReference<List<App>>() {});
		assertTrue(!apps.isEmpty());
		for(App app: apps){
			assertNotNull(app.getName());
		}
	}
}
