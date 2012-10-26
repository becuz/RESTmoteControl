package org.zooper.remosko.business;

import org.zooper.remosko.business.interfaces.BusinessModelAbstract;
import org.zooper.remosko.model.MediaCategory;

public class MediaCategoryBusiness extends BusinessModelAbstract<MediaCategory>{

	public MediaCategoryBusiness() {
		super(MediaCategory.class);
	}

	/**
	 * @param extension
	 * @return
	 */
	public MediaCategory getByExtension(String extension) {
		for(MediaCategory mediaCategory: getAll()){
			if (mediaCategory.getExtensions().contains(extension)){
				return mediaCategory;
			}
		}
		return null;
	}
	
}
