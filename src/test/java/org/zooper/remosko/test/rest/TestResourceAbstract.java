/**
 * 
 */
package org.zooper.remosko.test.rest;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.utils.Utils;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public abstract class TestResourceAbstract extends TestAbstract{

	protected static Server server;
	protected InputStream result; 
	protected String myString;
	protected ObjectMapper objectMapper;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void start() throws Exception {
		if (server == null){
			server = Server.getInstance();
			SettingsBusiness settingsBusiness = new SettingsBusiness(); 
			Settings settings = settingsBusiness.get();
			server.startAll(settings.getServerPort());//(settings.getPreferredServerInetName(), settings.getServerPort());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void stop() throws Exception {
		server.stop();
		server = null;
		PcControllerFactory.getPcController().cleanProcesses();
	}

	protected InputStream invokeUrl(String completeUrl, String queryParam, String accept, String contentType, String method, String body, int returnCode) throws Exception{
		URL url = new URL(completeUrl + queryParam);
		log.info(completeUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setUseCaches(false);
//		connection.setDoInput(true);
		connection.setRequestMethod(method);
		if (!Utils.isEmpty(accept)){
			connection.setRequestProperty("accept", accept);
		}
		if (!Utils.isEmpty(contentType)){
			connection.setRequestProperty("content-type", contentType);
		}
		connection.setInstanceFollowRedirects(false);
//		connection.setRequestProperty("User-Agent", "gui-integration-test");
		connection.setConnectTimeout(60000);
		if (!Utils.isEmpty(body)) {
			log.info("write the body " + body);
			connection.setDoOutput(true);
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			out.write(body);
			out.flush();
			out.close();
		} else {
			log.info("body empty");
		}
		connection.connect();
		if (connection.getResponseCode() != returnCode) {
			log.info("=====================");
			log.info("BEGIN: url requested: "+method+" "+ completeUrl+queryParam);
			log.info("return code " + connection.getResponseCode());
			log.info("method " + method);
			log.info("message " + connection.getResponseMessage());
			log.info("END content ");
			log.info("=====================");
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String line = null;
//			while((line = in.readLine()) != null) {
//				log.info(line);
//			}
			fail();
		}
		try {
			return connection.getInputStream();
		} catch (IOException io){};
		return null;
	}
	
	
}
