/**
 * 
 */
package org.zooper.remosko.test.persistence;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlCategory;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.model.VisualControlsManager;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
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
	public void testDeleteAll() {
		
	}
	
	@Test
	public void testSave() {
		persistenceAbstract.beginTransaction();
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(MediaCategory.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(App.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(ControlCategory.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(ControlsManager.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(VisualControlsManager.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(Control.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(VisualControl.class));
		persistenceAbstract.deleteAll(persistenceAbstract.getAll(KeysEvent.class));
		persistenceAbstract.commit();
		
		persistenceAbstract.beginTransaction();
		assertTrue(persistenceAbstract.getAll(MediaCategory.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(App.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(ControlCategory.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(ControlsManager.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(VisualControlsManager.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(Control.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(VisualControl.class).isEmpty());
		assertTrue(persistenceAbstract.getAll(KeysEvent.class).isEmpty());
		persistenceAbstract.commit();
		
		persistenceAbstract.beginTransaction();
		
		App newApp = new App("name2");
		newApp.setId(43543543l);
		
		ControlCategory controlCategory = new ControlCategory("ControlCategory");
		controlCategory.setId(99l);
		newApp.addControlCategory(controlCategory);
		
		Control c = new Control();
		c.setId(999l);
		c.setName("name");
		c.setControlCategory(controlCategory);
		newApp.getControlsManager().addControl(c);
		
		SortedSet<KeysEvent> keysEvents = new TreeSet<KeysEvent>();
		KeysEvent k = new KeysEvent(1);
		k.setLogicOrder(0);
		keysEvents.add(k);
		c.setKeysEvents(keysEvents);
		
		try {
			persistenceAbstract.save(controlCategory);
			persistenceAbstract.save(c);
			persistenceAbstract.saveAll(Collections.singletonList(newApp));
		} catch (Exception e){
			log.fatal("", e);
			fail();
		}
		
		persistenceAbstract.commit();
		
		persistenceAbstract.beginTransaction();
		List<Persistable> apps = persistenceAbstract.getAll(App.class);
		assertEquals(apps.size(), 1);
		assertEquals(persistenceAbstract.getAll(ControlCategory.class).size(), 1);
		assertEquals(persistenceAbstract.getAll(Control.class).size(), 1);
		assertEquals(persistenceAbstract.getAll(KeysEvent.class).size(), 1);
		boolean found = true;
		for (Persistable p: apps){
			if (((App)p).getName().equals("name2")){
				found = true;
				assertTrue(controlCategory.getName().equals(((App)p).getControlCategories().iterator().next().getName()));
				assertTrue(c.getName().equals(((App)p).getControlsManager().getControl(c.getName()).getName()));
				assertTrue(newApp.getControlsManager().getId().equals(((App)p).getControlsManager().getId()));
				break;
			}
		}
		assertTrue(found);
		persistenceAbstract.commit();
	}
	
//	@Test
//	public void testStore() {
//		log.info("Starting TestPersistence testStore");
//		App app = new App("name3");
//		app = appBusiness.store(app);
//		Long id = app.getId();
//		assertNotNull(id);
//		assertTrue(id > 0);
//		
//		appBusiness.delete(app);
//		
//		ControlCategory controlCategory = new ControlCategory("name2");
//		controlCategory.setId(99l);
//		controlCategoryBusiness.store(controlCategory); //this fails, cause store do a saveOrUpdate, and cause it has an id, it will try to make an update
//		
//		App newApp = new App("name2");
//		newApp.setId(id);
//		newApp.addControlCategory(controlCategory);
//		try {
//			appBusiness.save(newApp); //this fails cause cascade save doesn't work on controCategory
//			fail();	
//		} catch (Exception e){}
//	}

}
