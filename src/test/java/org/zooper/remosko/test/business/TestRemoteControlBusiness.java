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
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.RemoteControlBusiness;
import org.zooper.becuz.restmote.conf.ModelFactoryFactory;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestRemoteControlBusiness extends TestAbstract{

	private MediaBusiness mediaBusiness = new MediaBusiness();
	private RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	private ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	private String handle;
	
	/**
	 * Tests openMedia, control, closeMedia, openFile, testKillActiveApps
	 * @throws InterruptedException 
	 */
	@Test
	public void testOpenMedia() throws InterruptedException{
		try {
			List<MediaRoot> mediaRoots = mediaBusiness.getMediaRoots();
			assertNotNull(mediaRoots);
			assertEquals(mediaRoots.size(), 4);
			Media m = findMediaByExtensionInMediaRoots(mediaRoots, "mp3");
			assertNotNull(m);
			
			//OpenFile
			
			remoteControlBusiness.openFile(m.getPath(), appMusic);
			
			//wait the app is open, ang get the handle
			ActiveApp activeApp = TestActiveAppBusiness.getActiveApp(appMusic, true);
			if (activeApp == null){
				remoteControlBusiness.closeMedia(appMusic);
				fail();
			}
			handle = activeApp.getHandle();
			
			//ControlMedia
			
			//by app name, control name
			remoteControlBusiness.control(appMusic.getName(), Control.ControlDefaultTypeApp.NEXT.toString(), null);
			Thread.sleep(50);
			//by app name, char
			remoteControlBusiness.control(appMusic.getName(), null, ModelFactoryFactory.getModelFactoryAbstract().getAppMusicPauseChar());
			Thread.sleep(50);
			remoteControlBusiness.control(appMusic.getName(), null, ModelFactoryFactory.getModelFactoryAbstract().getAppMusicPauseChar());
			//by app handle, control name
			remoteControlBusiness.controlByHandle(handle, Control.ControlDefaultTypeApp.PREV.toString(), null);
			
			//CloseMedia
			
			handle = TestActiveAppBusiness.getActiveApp(appMusic, false).getHandle();
			assertNotNull(handle);
			
			remoteControlBusiness.closeMedia(appMusic);
			
			Thread.sleep(1250);
			assertNull(TestActiveAppBusiness.getActiveApp(appMusic, false));
			
			//OpenFile
			
			m = findMediaByExtensionInMediaRoots(mediaRoots, "mp3");
			remoteControlBusiness.openFile(m.getPath(), appMusic);
			handle = TestActiveAppBusiness.getActiveApp(appMusic, true).getHandle();
			assertNotNull(handle);
			
			//KillActiveApps
			activeAppBusiness.killActiveApps(Collections.singletonList(handle));
			int retry = 5;
			activeApp = TestActiveAppBusiness.getActiveApp(appMusic, false);
			for (int i = 0; i < retry && activeApp != null; i++) {
				Thread.sleep(2000);
				activeApp = TestActiveAppBusiness.getActiveApp(appMusic, false);	
			}
			assertNull(activeApp);
			
			
		} catch (Exception e){
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	
	@Test
	public void testOpenFile(){
		try {
			List<MediaRoot> mediaRoots = mediaBusiness.getMediaRoots();
			assertNotNull(mediaRoots);
			assertEquals(mediaRoots.size(), 4);
			Media m = findMediaByExtensionInMediaRoots(mediaRoots, "mp3");
			assertNotNull(m);
			remoteControlBusiness.openFile(m.getPath(), null);
			handle = TestActiveAppBusiness.getActiveApp(appMusic, true).getHandle();
			assertNotNull(handle);
			
			//KillActiveApps
			activeAppBusiness.killActiveApps(Collections.singletonList(handle));
			Thread.sleep(2000);
			assertNull(TestActiveAppBusiness.getActiveApp(appMusic, false));
		} catch (Exception e){
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	
	@Test
	public void mute() throws InterruptedException{
		try {
			for (int i = 0; i < 2; i++) {
				remoteControlBusiness.toggleMute();
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
//	@Test
//	public void testTypeString(){
//		remoteControlBusiness.typeString(s);
//	}
	
//	@Test
//	public void poweroff(){
//		remoteControlBusiness.poweroff();
//	}
	
//	@Test
//	public void suspend(){
//		remoteControlBusiness.suspend();
//	}
	
//	@Test
//	public void setVolume(){
//		remoteControlBusiness.setVolume(volume);
//	}
	
	/**
	 * Additional test
	 * @throws InterruptedException
	 */
	@Test
	public void testVideoControl() throws InterruptedException {
		try {
			List<MediaRoot> mediaRoots = mediaBusiness.getMediaRoots();
			Media aviMedia = findMediaByExtensionInMediaRoots(mediaRoots, "mp4");
			assertNotNull(aviMedia);
			remoteControlBusiness.openFile(aviMedia.getPath(), appMovies);
			Thread.sleep(3000);
			
			remoteControlBusiness.control(appMovies.getName(), Control.ControlDefaultTypeApp.FORWARD.toString(), null);
			
			Thread.sleep(3000);
			boolean found = false;
			for(ActiveApp activeApp: activeAppBusiness.getActiveApps(true)){
				if (activeApp.isInstanceOf(appMovies)){
					found = true;
				}
			}
			assertTrue(found);
			remoteControlBusiness.closeMedia(appMovies);
			for(ActiveApp activeApp: activeAppBusiness.getActiveApps(true)){
				if (activeApp.getName().equals(appMovies.getName())){
					fail();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage() + " " + e.getCause());
			fail();
		}
	}

}
