/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Logger;

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
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<ActiveApp> activeApps = objectMapper.readValue(myString, new TypeReference<List<ActiveApp>>() {});
		assertTrue(!activeApps.isEmpty());
		String pidEclipse = null;
		String pidOther = null;
		for(ActiveApp activeApp: activeApps){
			assertNotNull(activeApp.getPid());
			assertNotNull(activeApp.getName());
			if (activeApp.getName().equalsIgnoreCase("eclipse")){
				pidEclipse = activeApp.getPid();
			} else if (!activeApp.isFocus()){
				pidOther = activeApp.getPid();
			}
		}
		
		//POST /activeapps/pid/focus
		invokeUrl(
				server.getApiUrl() + "activeapps/" + pidOther + "/focus", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "POST", "", HttpURLConnection.HTTP_NO_CONTENT);
		//GET /activeapps/pidOther
		result = invokeUrl(
				server.getApiUrl() + "activeapps/" + pidOther, "?refresh=true", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(ActiveApp.class);
		ActiveApp activeApp = objectMapper.readValue(myString, new TypeReference<ActiveApp>() {});
		assertTrue(activeApp.isFocus());
		
		if (pidEclipse != null){
			invokeUrl(
					server.getApiUrl() + "activeapps/" + pidEclipse + "/focus", "", 
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "POST", "", HttpURLConnection.HTTP_NO_CONTENT);
			//GET /activeapps/pidOther
			result = invokeUrl(
					server.getApiUrl() + "activeapps/" + pidOther, "?refresh=true", 
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
			myString = IOUtils.toString(result, "UTF-8");
			log.severe("/get returned:" + myString);
			objectMapper = RestFactory.getJson().getContext(ActiveApp.class);
			activeApp = objectMapper.readValue(myString, new TypeReference<ActiveApp>() {});
			assertTrue(!activeApp.isFocus());
		}
	}
}
