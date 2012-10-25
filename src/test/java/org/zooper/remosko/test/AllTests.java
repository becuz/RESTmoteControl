package org.zooper.remosko.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.zooper.remosko.test.business.AllBusinessTests;
import org.zooper.remosko.test.rest.AllRestTests;

@RunWith(Suite.class)
@SuiteClasses({ AllBusinessTests.class, AllRestTests.class})
public class AllTests {

}
