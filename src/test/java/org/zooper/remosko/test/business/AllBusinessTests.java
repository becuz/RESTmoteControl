package org.zooper.remosko.test.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAppBusiness.class, TestActiveAppBusiness.class, TestMediaCategoryBusiness.class, TestRemoteControlBusiness.class, TestMediaBusiness.class, TestSettingsBusiness.class})
public class AllBusinessTests {

}
