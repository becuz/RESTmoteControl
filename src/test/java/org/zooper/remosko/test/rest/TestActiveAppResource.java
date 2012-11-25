/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.util.List;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.remosko.conf.RestFactory;

/**
 * TODO
 * @author bebo
 */
public class TestActiveAppResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestActiveAppResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		//GET /activeapps
		result = invokeUrl(
				server.getApiUrl() + "activeapps/", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.info("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<ActiveApp> activeApps = objectMapper.readValue(myString, new TypeReference<List<ActiveApp>>() {});
		assertTrue(!activeApps.isEmpty());
		String handleEclipse = null;
		String handleOther = null;
		for(ActiveApp activeApp: activeApps){
			assertNotNull(activeApp.getHandle());
			assertNotNull(activeApp.getName());
			if (activeApp.getName().equalsIgnoreCase("eclipse")){
				handleEclipse = activeApp.getHandle();
			} else if (!activeApp.isFocus()){
				handleOther = activeApp.getHandle();
			}
		}
		
		//POST /activeapps/handleOther/focus
		invokeUrl(
				server.getApiUrl() + "activeapps/" + handleOther + "/focus", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "POST", "", HttpURLConnection.HTTP_NO_CONTENT);
		//GET /activeapps/handleOther
		result = invokeUrl(
				server.getApiUrl() + "activeapps/" + handleOther, "?refresh=true", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.info("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(ActiveApp.class);
		ActiveApp activeApp = objectMapper.readValue(myString, new TypeReference<ActiveApp>() {});
		assertTrue(activeApp.isFocus());
		
		if (handleEclipse != null){
			invokeUrl(
					server.getApiUrl() + "activeapps/" + handleEclipse + "/focus", "", 
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "POST", "", HttpURLConnection.HTTP_NO_CONTENT);
			//GET /activeapps/handleOther
			result = invokeUrl(
					server.getApiUrl() + "activeapps/" + handleOther, "?refresh=true", 
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
			myString = IOUtils.toString(result, "UTF-8");
			log.info("/get returned:" + myString);
			objectMapper = RestFactory.getJson().getContext(ActiveApp.class);
			activeApp = objectMapper.readValue(myString, new TypeReference<ActiveApp>() {});
			assertTrue(!activeApp.isFocus());
		}
	}
}
