/**
 * 
 */
package org.zooper.remosko.test.persistence;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.Control;
import org.zooper.remosko.model.ControlsManager;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.persistence.hibernate.HibernateUtil;
import org.zooper.remosko.test.TestAbstract;
/**
 * @author bebo
 *
 */
public class TestPersistence extends TestAbstract {

	protected static final Logger log = Logger.getLogger(TestPersistence.class.getName());
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		log.severe("Starting TestPersistence test");
	}

}
