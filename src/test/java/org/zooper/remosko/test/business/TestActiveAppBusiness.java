/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.RemoteControlBusiness;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestActiveAppBusiness extends TestAbstract{

	private AppBusiness appBusiness = new AppBusiness();
	private MediaBusiness mediaBusiness = new MediaBusiness();
	private RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	private static ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	
	public static ActiveApp getActiveApp(App app, boolean wait){
		int attempts = 15;
		while (attempts > 0){
			log.info("Looking for app " + app.getName() + ". Waiting: " + wait + "");
			for(ActiveApp activeApp: activeAppBusiness.getActiveApps(true)){
				if(activeApp.isInstanceOf(app)){
					log.info("found..");
					return activeApp;
				}
			}
			if (!wait){
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.info(e.getMessage() + " " + e.getCause());
			}
			attempts--;
		}
		log.info("not found..");
		return null;
	}
	
	@Test
	public void testBasicOps() {
		try {
			List<MediaRoot> mediaRoots = mediaBusiness.getMediaRoots();
			assertNotNull(mediaRoots);
			assertEquals(mediaRoots.size(), 4);
			Media m = findMediaByExtensionInMediaRoots(mediaRoots, "mp3");
			assertNotNull(m);
			
			//OpenFile
			
			remoteControlBusiness.openFile(m.getPath(), appMusic);
			
			//wait the app is open, ang get the handle
			ActiveApp activeApp = getActiveApp(appMusic, true);
			if (activeApp == null){
				remoteControlBusiness.closeMedia(appMusic);
				fail();
			}
			
			List<ActiveApp> activeApps = activeAppBusiness.getActiveApps(true, true);
			boolean found = false;
			for(ActiveApp acApp: activeApps){
				if (acApp.getName().equals(appMusic.getWindowName())){
					found = true;
					assertTrue(acApp.isHasApp());
					break;
				}
			}
			assertTrue(found);
			
			App app = appBusiness.getRunningByExtension("mp3");
			assertEquals(app, appMusic);
			
			//KillActiveApps
			activeAppBusiness.killActiveApps(Collections.singletonList(activeApp.getHandle()));
			Thread.sleep(2000);
			assertNull(TestActiveAppBusiness.getActiveApp(appMusic, false));
			
		} catch (Exception e){
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	@Test
	public void testGetActiveApps(){
		List<ActiveApp> listApps = activeAppBusiness.getActiveApps(true);
		assertTrue(listApps.size()>0);
		for(ActiveApp activeApp: listApps){
			assertNotNull(activeApp.getHandle());
			assertNotNull(activeApp.getName());
			assertNotNull(activeApp.getWindowLbl());
		}
		String handle = listApps.iterator().next().getHandle();
		assertNotNull(handle);
	}
	
	
	@Test
	public void testFocusActiveApp(){
		try {
			String prevHandle = activeAppBusiness.getActiveAppFocused(true).getHandle();
					
			ActiveApp activeApp = activeAppBusiness.next();
			Thread.sleep(1000);
			assertTrue(!activeApp.getHandle().equals(prevHandle));
			
			ActiveApp activeApp2 = activeAppBusiness.next();
			Thread.sleep(1000);
			assertTrue(!activeApp2.getHandle().equals(prevHandle));
			assertTrue(!activeApp2.getHandle().equals(activeApp.getHandle()));
			assertEquals(activeAppBusiness.getActiveAppFocused(false).getHandle(), activeApp2.getHandle());
			assertEquals(activeAppBusiness.getActiveAppFocused(true).getHandle(), activeApp2.getHandle());
			
			activeAppBusiness.focusActiveApp(prevHandle);
			String nextHandle = null;
			for(ActiveApp a: activeAppBusiness.getActiveApps(true)){
				if (a.isFocus()){
					nextHandle = a.getHandle();
					break;
				}
			}
			assertEquals(prevHandle, nextHandle);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}

}
