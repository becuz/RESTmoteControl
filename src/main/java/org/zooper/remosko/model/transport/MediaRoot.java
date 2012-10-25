package org.zooper.remosko.model.transport;

import java.util.List;

import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;

/**
 * A browsing entry point for {@link Media}s. 
 * It has an optional {@link App} associated to it, meaning the {@link #getMediaChildren()} should be opened with that app.
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
		setMediaChildren(PcControllerFactory.getPcController().getMedia(this));
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
