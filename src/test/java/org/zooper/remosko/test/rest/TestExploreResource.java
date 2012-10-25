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
 * TODO
 * @author bebo
 */
public class TestExploreResource extends TestResourceAbstract {

	protected static final Logger log = Logger.getLogger(TestExploreResource.class.getName());
	
	@Test
	public void testGet() throws Exception {
		String rootPath = Utils.getRootDir();
		
		//GET /medias/ 
		invokeUrl(
				server.getApiUrl() + "medias", "", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_NOT_ACCEPTABLE);
		
		//GET /medias/ 
		result = invokeUrl(
				server.getApiUrl() + "medias", "?path=" + URLEncoder.encode(rootPath, "UTF-8"), 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		List<Media> medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		int sizeMedias = medias.size();
		assertTrue(!medias.isEmpty());
		
		//GET /medias/ 
		result = invokeUrl(
				server.getApiUrl() + "medias", "?path=" + URLEncoder.encode(rootPath, "UTF-8") + "&extensions=mp3", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		assertTrue(!medias.isEmpty());
		assertTrue(sizeMedias > medias.size());
		sizeMedias = medias.size();
		
		//GET /medias/Music
		result = invokeUrl(
				server.getApiUrl() + "medias/" + mediaCategoryMusic.getName() + "", "?path=" + URLEncoder.encode(rootPath, "UTF-8"), 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		assertTrue(!medias.isEmpty());
		assertEquals(sizeMedias, medias.size());
		
		//GET /medias/Music
		result = invokeUrl(
				server.getApiUrl() + "medias/" + mediaCategoryMusic.getName() + "", "?path=foo", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		assertTrue(medias.isEmpty());
		
		
	}
}
