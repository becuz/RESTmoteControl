package org.zooper.remosko.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
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
	
	/**
	 * 
	 */
	@Test
	public void testImportExportAll() {
		log.info("Starting test");
		File f = null;
		try {
			String pathFile = importExport.exportJson(false);
			assertTrue(pathFile.contains("dump"));
			assertNotNull(pathFile);
			f = new File(pathFile);
			assertTrue(f.exists());
			
			importExport.importJsonFile(pathFile, false);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			f.delete();
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
