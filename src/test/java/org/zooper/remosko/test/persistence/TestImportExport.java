package org.zooper.remosko.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
	public void test() {
		log.info("Starting test");
		try {
			String pathFile = importExport.exportJson(false);
			assertNotNull(pathFile);
			File f = new File(pathFile);
			assertTrue(f.exists());
			
			importExport.importJsonFile(pathFile, false);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
