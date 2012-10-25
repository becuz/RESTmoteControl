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
public class TestDataResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestDataResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		//get data
		result = invokeUrl(
				server.getApiUrl() + "data", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/data returned:" + myString);
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
			if (mediaRoot.getMediaChildren() != null){
				for(Media mediaChild: mediaRoot.getMediaChildren()){
//					assertNotNull(mediaChild.getId()); //JsonIgnore
					assertNotNull(mediaChild.getName());
				}
			}
		}
		result.close();
	
	}
}
