/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.util.List;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.remosko.conf.RestFactory;

/**
 * @author bebo
 */
public class TestMediaRootResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestMediaRootResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		//GET /mediaroots
		result = invokeUrl(
				server.getApiUrl() + "mediaroots/", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.info("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<MediaRoot> medias = objectMapper.readValue(myString, new TypeReference<List<MediaRoot>>() {});
		assertTrue(medias.size() >= 4); 
	}
	
	
	
	
}
