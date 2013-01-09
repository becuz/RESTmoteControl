package org.zooper.becuz.restmote.http;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.zooper.becuz.restmote.utils.Utils;

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
	
	/**
	 * Singleton instance
	 */
	private static volatile Server instance = null;
	
	/**
	 * Content types
	 */
	private static Map<String, String> contentTypes = new HashMap<String, String>();
	
	static {
		contentTypes.put("png", "image/png");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("jpg", "image/jpg");
		contentTypes.put("css", "text/css");
		contentTypes.put("js", "text/javascript");
		contentTypes.put("html", "text/html");
	}
	
	//***************************************************************************

	private HttpServer httpServer;
	
	private String serverUrl = null;
	
	private List<ServerStatusListener> serverStatusListeners;
	
	//***************************************************************************
	
	/**
	 * 
	 */
	private Server() {}
	
	/**
	 * Get the instance. Lazy initialization Singleton.
	 * @return
	 */
	public static Server getInstance() {
		if (instance == null) {
			synchronized (Server.class) {
				if (instance == null) {
					instance = new Server();
				}
			}
		}
		return instance;
	}
	
	//***************************************************************************
	
	public void addServerStatusListeners(ServerStatusListener serverStatusListener) {
		if (serverStatusListeners == null){
			serverStatusListeners = new ArrayList<ServerStatusListener>();
		}
		serverStatusListeners.add(serverStatusListener);
	}
	
	public void toggle(String inetName, int port) throws Exception{
        if (isRunning()){
            stop();
        } else {
			Server.getInstance().start(inetName, port);
        }
    }
	
	/**
	 * Start the {@link HttpServer}
	 * @throws IOException
	 */
	public InetAddr start(String inetName, int port) throws Exception {
		log.info("Starting grizzly...");
		InetAddr inetAddr = getInetAddr(inetName);
		if (inetAddr == null){
			//serverUrl = UriBuilder.fromUri("http://128.131.193.124/").port(port).build().toString();
			throw new Exception("Impossible to find a local net");
		} 
		serverUrl = UriBuilder.fromUri("http://" + inetAddr.getIp() + "/").port(port).build().toString();
		
		ResourceConfig rc = new PackagesResourceConfig("org.zooper.becuz.restmote.rest.resources");
		rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		httpServer = GrizzlyServerFactory.createHttpServer(getApiUrl(), rc);
		
		StaticHttpHandler staticHttpHandler = new StaticHttpHandler(Utils.getRootDir() + "client");
		staticHttpHandler.setFileCacheEnabled(false);	//Allow editing of files when server is running. TODO Should be true in production!
		httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/client");
		
		log.info("Server started with WADL available at " + serverUrl + "application.wadl\n" +
				"Try out " + getClientUrl() + " or " + getApiUrl()  + "data\n");
		new Thread(this).start();
		return inetAddr;
	}	
	
	/**
	 * Stop the {@link HttpServer}
	 * @throws IOException
	 */
	public void stop() throws IOException {
		httpServer.stop();
		httpServer = null;
	}
	
	/**
	 * @return true if the {@link HttpServer} is on and running
	 */
	public boolean isRunning(){
		return httpServer != null && httpServer.isStarted();
	}
	
	/**
	 * @return
	 */
	public List<InetAddr> getLocalInetAddresses(){
		List<InetAddr> inetAddres = new ArrayList<InetAddr>();
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface netint = (NetworkInterface) nets.nextElement();
				//if (netint.isLoopback()) continue;
				List<InetAddress> inetAddresses = Collections.list(netint.getInetAddresses());
				if (!inetAddresses.isEmpty()) {
					for (InetAddress inetAddress : inetAddresses) {
						if (!(inetAddress instanceof Inet4Address)) continue;
						//if (inetAddress.isSiteLocalAddress()) {
							String netName = netint.getName();
							String ip = getIpAsString(inetAddress);
							inetAddres.add(new InetAddr(netName, ip));
						//}
					}
				}
			}
		} catch (SocketException e){
			log.error(e.toString());
		}
		return inetAddres;
	}
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	private String getIpAsString(InetAddress address) {
		byte[] ipAddress = address.getAddress();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < ipAddress.length; i++) {
			if (i > 0)
				str.append('.');
			str.append(ipAddress[i] & 0xFF);
		}
		return str.toString();
	}
	
	/**
	 * 
	 * @return the best {@link InetAddr} candidate where restmote should listen to
	 * @throws Exception 
	 */
	private InetAddr getInetAddr(String serverInetName) {
		List<InetAddr> localInetAddresses = getLocalInetAddresses();
		if (!Utils.isEmpty(serverInetName)){
			for(InetAddr inetAddr: localInetAddresses){
				if (serverInetName.equals(inetAddr.getInetName())){
					return inetAddr;
				}
			}
		}
		if (!localInetAddresses.isEmpty()){ //returns the first interface if none is specified of if specified one isn't available
			return localInetAddresses.get(0);
		}
		return null;
	}

	public String getApiUrl(){
		return serverUrl + "api/";
	}
	
	public String getClientUrl(){
		return serverUrl + "client/index.html";
	}


	/**
	 * 
	 */
	@Override
	public void run() {
		if (serverStatusListeners != null){
			for(ServerStatusListener serverStatusListener: serverStatusListeners){
				serverStatusListener.serverStatusChanged(true);
			}
		}
		while(httpServer != null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e.toString());
			}
		}
		if (serverStatusListeners != null){
			for(ServerStatusListener serverStatusListener: serverStatusListeners){
				serverStatusListener.serverStatusChanged(false);
			}
		}
		log.info("Exiting thread");
	}
    
}
