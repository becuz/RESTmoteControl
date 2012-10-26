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
import org.zooper.remosko.business.ActiveAppBusiness;
import org.zooper.remosko.business.MediaBusiness;
import org.zooper.remosko.business.RemoteControlBusiness;
import org.zooper.remosko.conf.modelfactory.ModelFactoryFactory;
import org.zooper.remosko.model.Control;
import org.zooper.remosko.model.transport.ActiveApp;
import org.zooper.remosko.model.transport.Media;
import org.zooper.remosko.model.transport.MediaRoot;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestRemoteControlBusiness extends TestAbstract{

	private MediaBusiness mediaBusiness = new MediaBusiness();
	private RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	private ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	private String pid;
	
	private Media findMediaByExtensionInMediaRoots(List<MediaRoot> medias, String extension){
		for(MediaRoot mediaRoot: medias){
			Media m2 = findMediaByExtension(mediaRoot.getMediaChildren(), extension);
			if (m2 != null){
				return m2;
			}
		}
		return null;
	}
	
	private Media findMediaByExtension(List<Media> medias, String extension){
		for(Media m: medias){
			if (m.isFile() && m.getPath().endsWith(extension)){
				return m;
			} else {
				Media m2 = findMediaByExtension(m.getMediaChildren(), extension);
				if (m2 != null){
					return m2;
				}
			}
		}
		return null;
	}
	
	private String getActivePid(String appName, boolean wait){
		int attempts = 15;
		while (attempts > 0){
			log.severe("Looking for app " + appName + ". Waiting: " + wait + "");
			for(ActiveApp activeApp: activeAppBusiness.getActiveApps(true)){
				if(activeApp.getName().equals(appName)){
					log.severe("found..");
					return activeApp.getPid();
				}
			}
			if (!wait){
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.severe(e.getMessage() + " " + e.getCause());
			}
			attempts--;
		}
		log.severe("not found..");
		return null;
	}
	
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
			
			//wait the app is open, ang get the pid
			pid = getActivePid(appMusic.getName(), true);
			if (pid == null){
				remoteControlBusiness.closeMedia(appMusic);
				fail();
			}
			
			//ControlMedia
			
			//by app name, control name
			remoteControlBusiness.control(appMusic.getName(), Control.ControlDefaultTypeApp.FORWARD.toString(), null);
			Thread.sleep(50);
			//by app name, char
			remoteControlBusiness.control(appMusic.getName(), null, ModelFactoryFactory.getModelFactoryAbstract().getAppMusicPauseChar());
			Thread.sleep(50);
			remoteControlBusiness.control(appMusic.getName(), null, ModelFactoryFactory.getModelFactoryAbstract().getAppMusicPauseChar());
			//by app pid, control name
			remoteControlBusiness.controlByPid(pid, Control.ControlDefaultTypeApp.BACKWARD.toString(), null);
			
			//CloseMedia
			
			pid = getActivePid(appMusic.getName(), false);
			assertNotNull(pid);
			
			remoteControlBusiness.closeMedia(appMusic);
			
			Thread.sleep(250);
			pid = getActivePid(appMusic.getName(), false);
			assertNull(pid);
			
			//OpenFile
			
			m = findMediaByExtensionInMediaRoots(mediaRoots, "mp3");
			remoteControlBusiness.openFile(m.getPath(), appMusic);
			pid = getActivePid(appMusic.getName(), true);
			assertNotNull(pid);
			
			//KillActiveApps
			activeAppBusiness.killActiveApps(Collections.singletonList(pid));
			Thread.sleep(2000);
			pid = getActivePid(appMusic.getName(), false);
			assertNull(pid);
			
			remoteControlBusiness.openFile(m.getPath(), null);
			pid = getActivePid(appMusic.getName(), true);
			assertNotNull(pid);
			
			//KillActiveApps
			activeAppBusiness.killActiveApps(Collections.singletonList(pid));
			Thread.sleep(2000);
			pid = getActivePid(appMusic.getName(), false);
			assertNull(pid);
		} catch (Exception e){
			e.printStackTrace();
			log.severe(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	@Test
	public void testGetActiveApps(){
		List<ActiveApp> listApps = activeAppBusiness.getActiveApps(true);
		assertTrue(listApps.size()>0);
		for(ActiveApp activeApp: listApps){
			assertNotNull(activeApp.getPid());
			assertNotNull(activeApp.getName());
			assertNotNull(activeApp.getWindowLbl());
		}
		pid = listApps.iterator().next().getPid();
	}
	
	@Test
	public void testNextActiveApp(){
		try {
			ActiveApp activeApp = activeAppBusiness.next();
			ActiveApp activeApp2 = activeAppBusiness.next();
			assertTrue(!activeApp.getPid().equals(activeApp2.getPid()));
		} catch (Exception e){
			e.printStackTrace();
			log.severe(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	@Test
	public void testFocusActiveApp(){
		try {
			ActiveApp activeApp = activeAppBusiness.next();
//			ActiveApp activeApp2 = 
					activeAppBusiness.next();
			activeAppBusiness.focusActiveApp(activeApp.getPid());
			String pidFocus = null;
			for(ActiveApp a: activeAppBusiness.getActiveApps(true)){
				if (a.isFocus()){
					pidFocus = a.getPid();
					break;
				}
			}
			assertEquals(pidFocus, activeApp.getPid());
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.getMessage() + " " + e.getCause());
			fail();
		}
	}
	
	@Test
	public void mute() throws InterruptedException{
		try {
			for (int i = 0; i < 2; i++) {
				remoteControlBusiness.mute();
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.getMessage() + " " + e.getCause());
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
				if (activeApp.getName().equals(appMovies.getName())){
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
			log.severe(e.getMessage() + " " + e.getCause());
			fail();
		}
	}

}
