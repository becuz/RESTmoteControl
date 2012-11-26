package org.zooper.becuz.restmote.http;

/**
 * Listener of {@link Server} status  
 * 
 * @author bebo
 */
public interface ServerStatusListener {

	/**
	 * @param running server running
	 */
	public void serverStatusChanged(boolean running);
	
}
