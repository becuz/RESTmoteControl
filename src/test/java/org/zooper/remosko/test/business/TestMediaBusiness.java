/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestMediaBusiness extends TestAbstract{

	private MediaBusiness mediaBusiness = new MediaBusiness();
	
	private void print(Media mediaParent, int level){
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
		for(Media media: mediaParent.getMediaChildren()){
			print(media, level+1);
		}
	}
	
	@Test
	public void testGetMediaRoots() {
		List<MediaRoot> mediaRoots = mediaBusiness.getMediaRoots();
		assertNotNull(mediaRoots);
		assertEquals(mediaRoots.size(), 4);
		for(MediaRoot m: mediaRoots){
			assertNull(m.getPath());
			MediaCategory mediaCategory = m.getMediaCategory();
			assertNotNull(mediaCategory);
		}
	}
	
}
