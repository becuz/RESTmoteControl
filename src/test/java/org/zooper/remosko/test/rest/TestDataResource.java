/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.becuz.restmote.conf.rest.RestFactory;
import org.zooper.becuz.restmote.model.transport.Data;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;

/**
 * @author bebo
 */
public class TestDataResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestDataResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		//get data
		result = invokeUrl(
				server.getApiUrl() + "data", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.info("/data returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(Data.class);
		Data data = objectMapper.readValue(myString, new TypeReference<Data>() {});
		assertNotNull(data.getSettings());
		assertNotNull(data.getMediaRoots());
		for(MediaRoot mediaRoot: data.getMediaRoots()){
			assertNotNull(mediaRoot.getMediaCategory());
//			assertNotNull(mediaRoot.getId()); //JsonIgnore
			assertNotNull(mediaRoot.getName());
			assertNull(mediaRoot.getPath());
			assertFalse(mediaRoot.isFile());
			System.out.println("MEDIA ROOT: " + mediaRoot.getName() + ": " + mediaRoot);
			if (mediaRoot.getMediaChildren() != null){
				for(Media mediaChild: mediaRoot.getMediaChildren()){
					System.out.println(mediaChild.getName() + ": " + mediaChild);
//					assertNotNull(mediaChild.getId()); //JsonIgnore
					assertNotNull(mediaChild.getName());
				}
			}
		}
		result.close();
	
	}
}
