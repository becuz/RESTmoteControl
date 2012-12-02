package org.zooper.becuz.restmote.business;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.interfaces.BusinessAbstract;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.becuz.restmote.utils.Utils;

public class MediaBusiness extends BusinessAbstract{
	
	protected static final Logger log = Logger.getLogger(PcControllerAbstract.class.getName());
	
	/**
	 * Medias are currently not persisted..
	 */
	private static List<MediaRoot> mediaRoots = new ArrayList<MediaRoot>(); 
	
	public List<MediaRoot> getMediaRoots() {
		return mediaRoots;
	}

	public void addMediaRoot(MediaRoot m) {
		mediaRoots.add(m);
	}

//	public void removeMediaRoot(MediaRoot m) {
//		mediaRoots.remove(m);
//	}
	
	public void clearMediaRoots() {
		mediaRoots.clear();
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
	 * Scan the filesystem and call the persistence layer to store the {@link Media}s
	 * @return
	 */
	public void rootScan() {
		clearMediaRoots();
		long startTime = System.currentTimeMillis();
		Collection<MediaCategory> mediaCategories = getMediaCategoryBusiness().getAll();
		if (mediaCategories != null){
			for(MediaCategory mediaCategory: mediaCategories){
				if (!Boolean.FALSE.equals(mediaCategory.getActive())){
					MediaRoot mediaRoot = new MediaRoot(mediaCategory);
					retrieveMedias(mediaRoot);
					addMediaRoot(mediaRoot);
				}
			}
		}
		log.debug("Time to scan " + ((System.currentTimeMillis() - startTime)/1000));
	}
	
	/**
	 * 
	 * @param mediaRootName
	 * @return
	 */
	public List<Media> retrieveMedias(String mediaRootName){
		MediaRoot mediaRoot = getMediaRootByName(mediaRootName);
		if (mediaRoot == null){
			throw new IllegalArgumentException("MediaRoot " + mediaRootName + " not found");
		}
		return retrieveMedias(mediaRoot);
	}
	
	/**
	 * 
	 * @param mediaRoot
	 * @return
	 */
	public List<Media> retrieveMedias(MediaRoot mediaRoot){
		mediaRoot.clearChildren();
		Settings settings = getSettingsBusiness().get();
		Set<String> paths = mediaRoot.getMediaCategory().getPaths().isEmpty() ? settings.getPaths() : mediaRoot.getMediaCategory().getPaths();
		for(String path: paths){
			File file = new File(path);
			if (file.exists()){
				MediaCategory mediaCategory = mediaRoot.getMediaCategory();
				Media mediaPath = new Media(path);
				mediaPath.setMediaChildren(
						retrieveMedias(
								mediaPath.getPath(), 
								mediaCategory.isRoot() ? 1 : settings.getScanDepth(),
								new ArrayList<String>(mediaCategory.getExtensions()), 
								null));
				mediaRoot.addMediaChild(mediaPath);
			}
		}
		return mediaRoot.getMediaChildren();
	}
	
	/**
	 * Filter for {@link MediaBusiness#retrieveMedias(String, Integer, List, String)} 
	 * @author bebo
	 */
	public class MediaFilter implements FileFilter {

		private List<String> extensions;
		private Pattern pattern;
		
		public MediaFilter(List<String> extensions, String patternString) {
			super();
			this.extensions = extensions;
			if (!Utils.isEmpty(patternString)){
				pattern = Pattern.compile(patternString);
			}
		}

		@Override
		public boolean accept(File pathname) {
			if (pathname.isDirectory()) return true;
			if ((pattern == null || pattern.matcher(pathname.getAbsolutePath()).matches()) 
					&& (extensions == null || extensions.isEmpty() || extensions.contains(Utils.getFileExtension(pathname.getAbsolutePath()).toLowerCase()))){
				return true;
			}
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param path root path for the scan
	 * @param depth negative to scan everything
	 * @param extensions extensions allowed (null or empty to scan all files)
	 * @param pattern filter by this regex 
	 * @return
	 */
	public List<Media> retrieveMedias(String path, Integer depth, List<String> extensions, String pattern){
		log.debug("getMedia() path: " + path + ", depth: " + depth + ", extensions: " + extensions + ", pattern: " + pattern);
		List<Media> results = new ArrayList<Media>();
		depth = depth == null ? getSettingsBusiness().get().getScanDepth() : depth;
		if (depth != 0) { //-1 is a valid value, means go deep indefinitely, as there are files.
			File f = new File(path);
			if (f.exists() && f.isDirectory()){
				File[] children = f.listFiles(new MediaFilter(extensions, pattern));
				if (children != null){
					for(File child: children){
						String childPath = child.getAbsolutePath();
						Media mediaChild = new Media(childPath);
						if (!child.isDirectory()){
							mediaChild.setFile(true);	//let's leave it at null in case it's a directory: less noise as we avoid serialize nulls
						}
						results.add(mediaChild);							
					}
					if (depth != 1){ 
						for (Media mediaChild: results){
							mediaChild.setMediaChildren(retrieveMedias(mediaChild.getPath(), depth-1, extensions, pattern));
						}
					}
				}
			}
		}
		return results;
	}

}
