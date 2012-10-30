/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.utils.Utils;
import org.zooper.remosko.conf.RestFactory;

/**
 * TODO all tests
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
				server.getApiUrl() + "medias", "?path=" + URLEncoder.encode(rootPath, "UTF-8") + "&extensions=mp3&extensions=mp2", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		assertTrue(!medias.isEmpty());
		assertTrue(sizeMedias > medias.size());
		sizeMedias = medias.size();
		
		result = invokeUrl(
				server.getApiUrl() + "medias", "?path=" + URLEncoder.encode(rootPath, "UTF-8") + "&extensions=uuuuu", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		Media m = findMediaFile(medias);
		assertNull(m); //no files should be found
		
		result = invokeUrl(
				server.getApiUrl() + "medias", "?path=" + URLEncoder.encode(rootPath, "UTF-8") + "&filter=uuuuuuuuuuuuuu", 
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, "GET", "", HttpURLConnection.HTTP_OK);
		myString = IOUtils.toString(result, "UTF-8");
		log.severe("/get returned:" + myString);
		objectMapper = RestFactory.getJson().getContext(List.class);
		medias = objectMapper.readValue(myString, new TypeReference<List<Media>>() {});
		m = findMediaFile(medias);
		assertNull(m); //no files should be found
		
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
