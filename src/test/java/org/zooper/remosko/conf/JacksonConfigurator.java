/**
 * 
 */
package org.zooper.remosko.conf;


import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@Provider
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {
	
	private static final Logger log = Logger.getLogger(JacksonConfigurator.class.getName());
    
	private static ObjectMapper mapper = null;

    /**
     * <p>Constructor for JacksonConfigurator.</p>
     */
    public JacksonConfigurator() {
    	log.info("starting jackson context ..");
    	log.debug("initialize mapper");
    	mapper = new ObjectMapper();
    	SerializationConfig serConfig = mapper.getSerializationConfig();
//        serConfig.setDateFormat(new SimpleDateFormat(Constants.dateFormat));
        serConfig.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
//        deserializationConfig.setDateFormat(new SimpleDateFormat(Constants.dateFormat));
//        mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,false);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, false);
        mapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
//        SimpleModule testModule = new SimpleModule("MyModule", new Version(1, 0, 0, null)).addDeserializer(Text.class, new TextDeserializer());
//        mapper.registerModule(testModule);
    }

    public ObjectMapper getContext(Class<?> arg0) {
    	log.debug("class requested: "+arg0.getSimpleName());
        return mapper;
    }

}
