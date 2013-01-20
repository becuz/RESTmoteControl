/**
 * 
 */
package org.zooper.remosko.test.persistence;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.business.ControlCategoryBusiness;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.ControlCategory;
import org.zooper.remosko.test.TestAbstract;
/**
 * @author bebo
 *
 */
public class TestPersistence extends TestAbstract {

	protected static final Logger log = Logger.getLogger(TestPersistence.class.getName());
	
	private AppBusiness appBusiness;
	private ControlCategoryBusiness controlCategoryBusiness;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		appBusiness = BusinessFactory.getAppBusiness();
		controlCategoryBusiness = BusinessFactory.getControlCategoryBusiness();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		appBusiness = null;
	}
	
	@Test
	public void testSave() {
		log.info("Starting TestPersistence testSave");
		App app = new App("name");
		app = appBusiness.store(app);
		Long id = app.getId();
		assertNotNull(id);
		assertTrue(id > 0);
		
		appBusiness.delete(app);
		
		ControlCategory controlCategory = new ControlCategory("name2");
		controlCategory.setId(99l);
		controlCategoryBusiness.save(controlCategory);
		
		App newApp = new App("name2");
		newApp.setId(id);
		newApp.addControlCategory(controlCategory);
		try {
			appBusiness.save(newApp);
		} catch (Exception e){
			log.fatal("", e);
			fail();
		}
	}
	
	@Test
	public void testStore() {
		log.info("Starting TestPersistence testSave");
		App app = new App("name");
		app = appBusiness.store(app);
		Long id = app.getId();
		assertNotNull(id);
		assertTrue(id > 0);
		
		appBusiness.delete(app);
		
		ControlCategory controlCategory = new ControlCategory("name2");
		controlCategory.setId(99l);
		controlCategoryBusiness.store(controlCategory); //this fails, cause store do a saveOrUpdate, and cause it has an id, it will try to make an update
		
		App newApp = new App("name2");
		newApp.setId(id);
		newApp.addControlCategory(controlCategory);
		try {
			appBusiness.save(newApp); //this fails cause cascade save doesn't work on controCategory
			fail();	
		} catch (Exception e){}
	}

}
