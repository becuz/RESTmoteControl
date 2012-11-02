package org.zooper.becuz.restmote.model.transport;

import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;

/**
 * A browsing entry point for {@link Media}s. 
 * This class is never stored in db, it's used in memory and for transport (it's serialized to the client(s))
 * Specialized subclass of {@link Media}, holding a {@link MediaCategory}
 * @author bebo
 *
 */
public class MediaRoot extends Media {

	/**
	 * Just the mediaRoots have a valued mediaCategory, all others have it null 
	 */
	private MediaCategory mediaCategory;
	
	public MediaRoot() {}
	
	public MediaRoot(MediaCategory mediaCategory) {
		setMediaCategory(mediaCategory);
	}
	
	public void refresh(){
		setMediaChildren(PcControllerFactory.getPcController().getMedias(this));
	}
	
	public MediaCategory getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(MediaCategory mediaCategory) {
		this.mediaCategory = mediaCategory;
		if (mediaCategory != null){
			setName(mediaCategory.getName());
		}
	}
}
