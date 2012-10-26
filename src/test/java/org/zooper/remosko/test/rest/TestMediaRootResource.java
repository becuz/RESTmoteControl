/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.remosko.conf.RestFactory;
import org.zooper.remosko.model.transport.MediaRoot;

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
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<MediaRoot> medias = objectMapper.readValue(myString, new TypeReference<List<MediaRoot>>() {});
		assertEquals(4, medias.size());
	}
	
	
	
	
}
