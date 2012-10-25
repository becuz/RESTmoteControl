package org.zooper.remosko.test.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestActiveAppResource.class, TestAppResource.class,
		TestDataResource.class, TestExploreResource.class,
		TestMediaRootResource.class, TestPcResource.class, TestResources.class })
public class AllRestTests {

}
