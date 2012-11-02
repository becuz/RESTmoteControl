package org.zooper.becuz.restmote.persistence.hibernate;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.zooper.becuz.restmote.utils.Utils;

public class HibernateUtil {

	protected static final Logger log = Logger.getLogger(HibernateUtil.class.getName());
	
    private static SessionFactory sessionFactory;

    /**
     * Tests set this to true, to allow to use two different physical databases and two sessionFactories
     */
    private static boolean test = false;
    
	private static Properties properties;
    
    private static SessionFactory buildSessionFactory() {
    	Logger.getLogger("org.hibernate").setLevel(Level.INFO);
        try {
            Configuration configuration = new Configuration().configure();
            configuration.setProperty(
                    Environment.URL, 
                    "jdbc:hsqldb:file:" + getDbFile(test));
	        if (properties != null){
	        	for(Object k: properties.keySet()){
	        		configuration.setProperty(k.toString(), properties.getProperty(k.toString()));	
	        	}
	        }
            return configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            log.severe("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static String getDbFile(boolean test){
    	return Utils.getRestmoteRootDirAbsolutePath() + "db/remoskodb" + (test ? "_test" : "");
    }
    
    public static void setTest(boolean test) {
		HibernateUtil.test = test;
	}
    
    public static void setProperties(Properties properties) {
		HibernateUtil.properties = properties;
	}
    
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null){
			sessionFactory = buildSessionFactory();
		}
        return sessionFactory;
    }
    
}