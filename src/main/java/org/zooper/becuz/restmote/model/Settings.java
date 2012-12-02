package org.zooper.becuz.restmote.model;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * Application settings
 * @author bebo
 *
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class Settings implements Persistable{
	
	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * 
	 */
	@JsonView(Views.All.class)
	private Date lastUpdated;
	
	/**
	 * Name that appears on the header
	 */
	private String name = "Home";
	
	/**
	 * Name that appears on the home
	 */
	private String nameRoot = "My medias";
	
//	/**
//	 * 
//	 */
//	private String iconTheme;
	
	/**
	 * Paths to scan
	 */
	@JsonView(Views.All.class)
	private Set<String> paths;

	/**
	 * Depth in scanning paths. Negative to scan everything. Used internally in {@link PcControllerAbstract#getMedia(String, Set, Integer)}.
	 * Clients can override this specifying it as a parameter in the GET methods.
	 */
	private Integer scanDepth = 1;
	
	
	/**
	 * Names of net interfaces the server should run on
	 */
	@JsonView(Views.All.class)
	private String serverInetName;
	
	/**
	 * Ip of {@link #serverInetName}
	 */
	@JsonView(Views.All.class)
	private String serverLastIp;
	
	/**
	 * Port the server should run on 
	 */
	@JsonView(Views.All.class)
	private Integer serverPort = 9898;
	
	/**
	 * icon theme name of controls 
	 */
	private String iconControlsTheme = "iconic";
	
	/**
	 * icon theme name of system 
	 */
	private String iconSystemTheme = "iconic";
	
	//****************************************************************************************
	
	public Settings() {
	}
	
	@Override
	public void validate() throws IllegalArgumentException {}
	
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
		return scanDepth;
	}

	public void setScanDepth(Integer scanDepth) {
		this.scanDepth = scanDepth;
	}

	public String getServerInetName() {
		return serverInetName;
	}

	public void setServerInetName(String serverInetName) {
		this.serverInetName = serverInetName;
	}

	public String getServerLastIp() {
		return serverLastIp;
	}

	public void setServerLastIp(String serverLastIp) {
		this.serverLastIp = serverLastIp;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public void setTheme(String theme){
		setIconControlsTheme(theme);
		if (new File(Utils.getThemeDir(theme) + "/" + Constants.NAME_DIR_ICON_SYSTEM).exists()){
			setIconSystemTheme(theme);
		} else {
			setIconSystemTheme(Constants.DEFAULT_THEME);
		}
	}
	
	public String getIconControlsTheme() {
		return iconControlsTheme;
	}

	public void setIconControlsTheme(String iconControlsTheme) {
		this.iconControlsTheme = iconControlsTheme;
	}

	public String getIconSystemTheme() {
		return iconSystemTheme;
	}

	public void setIconSystemTheme(String iconSystemTheme) {
		this.iconSystemTheme = iconSystemTheme;
	}

}
