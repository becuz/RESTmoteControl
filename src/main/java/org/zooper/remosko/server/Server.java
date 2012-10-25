package org.zooper.remosko.server;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.zooper.remosko.utils.Utils;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * HttpServer
 * 
 * @author bebo
 *
 */
public class Server implements Runnable {

	private static final Logger log = Logger.getLogger(Server.class.getName());
	
	private static Map<String, String> contentTypes = new HashMap<String, String>();
	
	private int port = 9898;
	private HttpServer httpServer;
	private String serverUrl = null;
	
	static {
//		try {
//			File f = new File("logging.properties");
//			LogManager.getLogManager().readConfiguration(new FileInputStream(f));
//		} catch (Exception e) {
//			log.severe(e.getMessage());
//		}
		contentTypes.put("png", "image/png");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("jpg", "image/jpg");
		contentTypes.put("css", "text/css");
		contentTypes.put("js", "text/javascript");
		contentTypes.put("html", "text/html");
	}
	
	private String getServerUrl() {
		if (serverUrl == null){
			String url = null;
			Enumeration<NetworkInterface> nets;
			try {
				nets = NetworkInterface.getNetworkInterfaces();
				while (url == null && nets.hasMoreElements()) {
					NetworkInterface netint = (NetworkInterface) nets.nextElement();
					List<InetAddress> inetAddresses = Collections.list(netint.getInetAddresses());
					if (!inetAddresses.isEmpty()) {
						for (InetAddress inetAddress : inetAddresses) {
							if (inetAddress.isSiteLocalAddress()) {
								url = getIpAsString(inetAddress);
								break;
							}
						}
					}
				}
			} catch (SocketException e) {
				log.severe(e.getMessage() + e.getCause());
			}
			if (url == null){
				url = "http://192.168.1.11/";
			}
			serverUrl = UriBuilder.fromUri("http://" + url + "/").port(port).build().toString();
		}
		return serverUrl;
	}

	private static String getIpAsString(InetAddress address) {
		byte[] ipAddress = address.getAddress();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < ipAddress.length; i++) {
			if (i > 0)
				str.append('.');
			str.append(ipAddress[i] & 0xFF);
		}
		return str.toString();
	}
	
	public String getApiUrl(){
		return getServerUrl() + "api/";
	}
	
	private String getClientUrl(){
		return getServerUrl() + "client/";
	}

	public Server(int port) {
		this();
		this.port = port;
	}
	
	public Server() {}

	public void start() throws IOException {
		System.out.println("Starting grizzly...");
		
		ResourceConfig rc = new PackagesResourceConfig("org.zooper.remosko.rest.resources");
		rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		//rc.getFeatures().put(ResourceConfig.FEATURE_TRACE, true);
		httpServer = GrizzlyServerFactory.createHttpServer(getApiUrl(), rc);
		
//		Map<String, String> initParams = new HashMap<String, String>();
//		initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
//        initParams.put("com.sun.jersey.config.property.packages", "org.zooper.remosko.test.rest.resources");  
//        System.out.println("Starting grizzly...");          
//        httpServer = GrizzlyWebContainerFactory.create(getBaseURI(), initParams);  
		
		StaticHttpHandler staticHttpHandler = new StaticHttpHandler(Utils.getRootDir() + "client/"){
			@Override
			public void service(
					org.glassfish.grizzly.http.server.Request request,
					Response response) throws Exception {
				String extension = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('.')+1);
				String contentType = contentTypes.get(extension);
				if (contentType != null){
					response.setContentType(contentType);
				}
				super.service(request, response);
			}
		};
		httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/client");
		
		System.out.println("Jersey app started with WADL available at " + getServerUrl() + "application.wadl\n" +
				"Try out " + getClientUrl() + "index.html or " + getApiUrl()  + "data\n");
		
//		no thread way
//		callGetSettings(getBaseURI()+"settings");
//		System.in.read();
//		httpServer.stop();
		
//		thread way
		new Thread(this).start();
		callGetSettings();
	}	
	
	public void stop() throws IOException {
		httpServer.stop();
		httpServer = null;
	}

	@Override
	public void run() {
		while(httpServer != null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("Exiting thread");
	}
	
	/**
	 * Call by http the url /settings
	 * TODO BUG, this method is called internally after server startup because the first invocation, despite the fact that it's executed correctly, returns a 404!
	 * @param completeUrl
	 */
	private void callGetSettings(){
		for (int i = 0; i < 1; i++) {
			try {
				URL url = new URL(getApiUrl()+"data");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setUseCaches(false);
				connection.setRequestMethod("GET");
				connection.setInstanceFollowRedirects(false);
				connection.setConnectTimeout(60000);
				connection.setDoOutput(false);
				connection.connect();
				log.severe("response code for /data is " + connection.getResponseCode());
			} catch (Exception e){
				log.severe(e.getMessage() + " " + e.getCause());
			}
		}
	}
	
    
}
