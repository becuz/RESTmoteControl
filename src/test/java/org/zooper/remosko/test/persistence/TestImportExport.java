package org.zooper.remosko.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.persistence.export.ImportExport;
import org.zooper.remosko.test.TestAbstract;

public class TestImportExport extends TestAbstract {

	protected static final Logger log = Logger.getLogger(TestImportExport.class.getName());

	private ImportExport importExport;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		importExport = new ImportExport();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		importExport = null;
	}
	
	@Test
	public void testConversionKeys() {
		String[] strings = {"a", "d+a"};
		for(String s: strings){
			List<KeysEvent> keysEvents = importExport.getKeysEventsFromString(s);
			assertEquals(s.toUpperCase(), ImportExport.getStringFromControlKeys(keysEvents));
		}
		
		strings = new String[] {"0+!"};
		for(String s: strings){
			try {
				List<KeysEvent> keysEvents = importExport.getKeysEventsFromString(s);
				log.info(s + "\t" + ImportExport.getStringFromControlKeys(keysEvents));
			} catch (IllegalArgumentException e){
				log.error("", e);
			}
		}
	}
	
	/**
	 * 
	 */
//	@Test
	public void testImportExportAll() {
		log.info("Starting test");
		File f = null;
		File f2 = null;
		try {
			String pathFile = importExport.exportJson(false);
			assertTrue(pathFile.contains("dump"));
			assertNotNull(pathFile);
			f = new File(pathFile);
			assertTrue(f.exists());
			
			importExport.importJsonFile(pathFile, false);
			
			String pathFile2 = importExport.exportJson(false);
			f2 = new File(pathFile2);
			//assertEquals(FileUtils.readFileToString(f), FileUtils.readFileToString(new File(pathFile2)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			if (f != null){
				f.delete();
			}
			if (f2 != null){
				f2.delete();
			}
		}
	}
	
//	@Test
//	public void testImportExportApps() {
//		log.info("Starting test");
//		try {
//			String pathFile = importExport.exportJson(true);
//			assertTrue(pathFile.contains("apps"));
//			File f = new File(pathFile);
//			assertTrue(f.exists());
//			
//			importExport.importJsonFile(pathFile, true);
//			
//			MediaCategoryBusiness mediaCategoryBusiness = BusinessFactory.getMediaCategoryBusiness();
//			for(MediaCategory mc: mediaCategoryBusiness.getAll()){
//				mediaCategoryBusiness.delete(mc);
//			}
//			AppBusiness appBusiness = BusinessFactory.getAppBusiness();
//			for(App app: appBusiness.getAll()){
//				appBusiness.delete(app);
//			}
//			
//			importExport.importJsonFile(pathFile, true);
//			assertTrue(appBusiness.getAll().size() > 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
}
