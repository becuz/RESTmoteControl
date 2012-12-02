/**
 * 
 */
package org.zooper.remosko.test.persistence;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
		log.info("Starting TestPersistence test");
	}

}
