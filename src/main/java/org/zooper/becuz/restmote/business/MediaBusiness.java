package org.zooper.becuz.restmote.business;

import java.util.List;

import org.zooper.becuz.restmote.business.interfaces.BusinessAbstract;
import org.zooper.becuz.restmote.model.transport.MediaRoot;

public class MediaBusiness extends BusinessAbstract{

	
	/**
	 * @return
	 */
	public List<MediaRoot> getMediaRoots(){
		return persistenceAbstract.getMediaRoots();
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public MediaRoot getMediaRootByName(String name){
		for (MediaRoot mediaRoot: getMediaRoots()){
			if (name.equals(mediaRoot.getName())){
				return mediaRoot;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param m
	 */
	public void addMediaRoot(MediaRoot m){
		persistenceAbstract.addMediaRoot(m);
	}
	
//	public void removeMediaRoot(MediaRoot m){
//		persistenceAbstract.removeMediaRoot(m);
//	}
	
	/**
	 * 
	 */
	public void clearMediaRoots(){
		persistenceAbstract.clearMediaRoots();
	}

}