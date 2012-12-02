/**
 * 
 */
package org.zooper.becuz.restmote.conf.rest;

import org.apache.log4j.Logger;

/**
 * <p>RestFactory class.</p>
 */
public final class RestFactory {
	
	private static final Logger log = Logger.getLogger(RestFactory.class.getName());
	
	private static JacksonConfigurator confJsonInstance = null;
	
    public static JacksonConfigurator getJson() {
    	if(confJsonInstance==null) {
    		confJsonInstance = new JacksonConfigurator();
    	}
        return confJsonInstance;
    }
    
    private RestFactory() {}
}
