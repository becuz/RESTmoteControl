package org.zooper.becuz.restmote.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.utils.Constants;

/**
 * Application settings
 * @author bebo
 *
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class Settings implements Persistable{

	@JsonIgnore
	private Long id;
	
	/**
	 * 
	 */
	private Date lastUpdated;
	
	/**
	 * Name that appears on the header
	 */
	private String name;
	
	/**
	 * Name that appears on the home
	 */
	private String nameRoot;
	
//	/**
//	 * 
//	 */
//	private String iconTheme;
	
	/**
	 * Paths to scan
	 */
	@JsonIgnore
	private Set<String> paths;

	/**
	 * Depth in scanning paths. Negative to scan everything. Used internally in {@link PcControllerAbstract#getMedia(String, Set, Integer)}.
	 * Clients can override this specifying it as a parameter in the GET methods.
	 */
	private Integer scanDepth;
	
	//****************************************************************************************
	
	public Settings() {
	}
	
	@Override
	public String toString() {
		return "Settings: " + getId() + "_-_" + getName();
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Set<String> getPaths() {
		if (paths == null) {
			paths = new HashSet<String>();
		}
		return paths;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}
	
	public void addPath(String path) {
		getPaths().add(path);
	}

	public boolean removePath(String path) {
		return getPaths().remove(path);
	}

//	public Set<MediaCategory> getmediaCategories() {
//		if (mediaCategories == null) {
//			mediaCategories = new HashSet<MediaCategory>();
//		}
//		return mediaCategories;
//	}
//
//	public void setmediaCategories(Set<MediaCategory> mediaCategories) {
//		this.mediaCategories = mediaCategories;
//	}
	
//	public void addMediaCategory(MediaCategory mediaCategory) {
//		getmediaCategories().add(mediaCategory);
//	}
//
//	public boolean removeMediaCategory(MediaCategory mediaCategory) {
//		return getmediaCategories().remove(mediaCategory);
//	}
//	
//	public Set<App> getApps() {
//		if (apps == null) {
//			apps = new HashSet<App>();
//		}
//		return apps;
//	}
//
//	public void setApps(Set<App> apps) {
//		this.apps = apps;
//	}
//	
//	public void addApp(App app) {
//		getApps().add(app);
//	}
//
//	public boolean removeApp(App app) {
//		return getApps().remove(app);
//	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getIconTheme() {
//		return iconTheme;
//	}
//
//	public void setIconTheme(String iconTheme) {
//		this.iconTheme = iconTheme;
//	}

	public String getNameRoot() {
		return nameRoot;
	}

	public void setNameRoot(String nameRoot) {
		this.nameRoot = nameRoot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getScanDepth() {
		if (scanDepth == null){
			scanDepth = Constants.SCAN_DEPTH;
		}
		return scanDepth;
	}

	public void setScanDepth(Integer scanDepth) {
		this.scanDepth = scanDepth;
	}

	
	
}
