/**
 * 
 */
package org.zooper.remosko.conf;

import java.util.logging.Logger;

/**
 * <p>RestFactory class.</p>
 */
public final class RestFactory {
	
	private static final Logger log = Logger.getLogger(RestFactory.class.getName());
	
	private static JacksonConfigurator confJsonInstance = null;
	
    public static JacksonConfigurator getJson() {
    	if(confJsonInstance==null) {
    		log.fine("JacksonConfigurator start");
    		confJsonInstance = new JacksonConfigurator();
    		log.fine("JacksonConfigurator end");
    	}
        return confJsonInstance;
    }
    
    private RestFactory() {}
}
